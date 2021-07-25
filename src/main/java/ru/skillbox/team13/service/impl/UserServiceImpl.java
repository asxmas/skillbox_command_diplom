package ru.skillbox.team13.service.impl;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.*;
import ru.skillbox.team13.entity.BlacklistedToken;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.Subscription;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.NotificationCode;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.entity.enums.UserType;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.exception.UnauthorizedException;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.BlacklistedTokenRepository;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.SubscriptionRepository;
import ru.skillbox.team13.repository.UserRepository;
import ru.skillbox.team13.security.Jwt.JwtTokenProvider;
import ru.skillbox.team13.security.Jwt.JwtUser;
import ru.skillbox.team13.security.TokenType;
import ru.skillbox.team13.service.UserService;
import ru.skillbox.team13.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final BlacklistedTokenRepository blacklistedTokenRepo;
    private final MailServiceImpl mailServiceImpl;
    private final SessionRegistry sessionRegistry;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    @Modifying
    public DTOWrapper register(UserDto.Request.Register userDto, HttpServletRequest request) {

            //проверка наличия данного email в бд
            log.debug("Checking email '{}'...", userDto.getEmail());
            if (checkUserRegistration(userDto.getEmail()).isPresent())
                throw new BadRequestException("Пользователь уже зарегистрирован");
            //заполняем нового User имеющимися данными при регистрации
            //все поля по которым нет данных и могут быть null не заполняем
            Person person = new Person();
            person.setFirstName(userDto.getFirstName());
            person.setLastName(userDto.getLastName());
            person.setEmail(userDto.getEmail());
            person.setRegDate(LocalDateTime.now());
            person.setBlocked(false);//НЕТ ДАННЫХ, NOT NULL
            person.setLastOnlineTime(LocalDateTime.now());
            person.setMessagesPermission(PersonMessagePermission.ALL);//NOT NULL, ставим значение по-умолчанию
            person.setPhoto("duser.png");
            //заполняем Person имеющимися данными
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setName(userDto.getEmail());//НЕТ ДАННЫХ, NOT NULL
            user.setPassword(passwordEncoder.encode(userDto.getFirstPassword()));
            user.setType(UserType.USER);//НЕТ ДАННЫХ, NOT NULL
            user.setPerson(personRepository.save(person));
            user.setConfirmationCode(userDto.getCode());
            user.setApproved(false);//требует подтверждения переходом по ссылке из почты
            log.debug("Saving user and person data...");
            User registeredUser = userRepository.save(user);
            log.info("New user registered: '{}', type: {}, message permission: {}, not approval.",
                    registeredUser.getEmail(), registeredUser.getType().name(), person.getMessagesPermission().name());
            return universalAccountMailLink(userDto.getEmail(), "register/confirm", request);
    }

    @Override
    @Transactional
    public DTOWrapper login(LoginDto loginDto) {

        try {
            String username = loginDto.getEmail();
            User user = userRepository.findByEmail(username).get();
            Person person = personRepository.getById(user.getPerson().getId());
            user.setName(loginDto.getEmail());
            userRepository.save(user);
            log.debug("Successful login: {}", loginDto.getEmail());
            return WrapperMapper.wrap(PersonMapper.convertPersonToPersonDTOWithToken(person, jwtTokenProvider.createToken(username, TokenType.ORDINARY)), true);
        } catch (AuthenticationException e) {
            throw new BadRequestException("Неверный логин или пароль");
        }
    }

    @Override
    @Transactional
    public DTOWrapper logout(HttpServletRequest request) {
        String username = "anonymous";
        try {
            username = getAuthorizedUser().getEmail();
            String token = jwtTokenProvider.resolveToken(request);
            if (token != null) {
                    putTokenToBlackList(token);
                    log.debug("Successful logout: {}", username);
            }
        }
        catch (JwtException | UnauthorizedException e) {
            log.debug("Successful logout: {}", username);
        }
        finally {
            SecurityContextHolder.clearContext();
            SecurityContextHolder.createEmptyContext();
        }
        return WrapperMapper.wrapMessage(new MessageDTO("ok"));
    }

    //метод получения текущего авторизованного пользователя
    @Override
    @Transactional(readOnly = true)
    public User getAuthorizedUser() {

        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmail(email).get();
            return user;
        }
        catch (Exception ex) {
            throw new UnauthorizedException("Unauthorized user");
        }
    }

    //очистка blacklistedToken по расписанию
    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void blackListExpiredTokensClear(){
        blacklistedTokenRepo.deleteExpiredTokens();
        log.debug("Scheduled task complete: expired tokens deleted");
    }

    //очистка пользователей не подтвердивших регистрацию
    @Scheduled(fixedRate = 60000)
    @Transactional
    @Modifying
    public void clearNotApprovedUsers(){
        List<User> usersList = userRepository.findAllByNotApproved();
        for (User user : usersList) {
            if (user.getPerson().getRegDate().isBefore(LocalDateTime.now().minusMinutes(10))) {
                userRepository.delete(user);
                log.debug("Scheduled task complete: not approved user {} deleted", user.getEmail());
            }
        }
     }

    private Optional<User> checkUserRegistration(String email) {
        return userRepository.findByEmailNoApproval(email);
    }

    //метод генерации и отправки по email ссылки для сброса пароля (смены email, )
    @Override
    @Transactional
    public DTOWrapper universalAccountMailLink(String email, String route, HttpServletRequest request){

        try {
            Optional<User> optionalUser = checkUserRegistration(email);
            String description = null;
            String link = jwtTokenProvider.createToken(email, TokenType.MAIL_LINK);
            switch (route){
                case "password/reset" -> {
                    if (optionalUser.isEmpty()) {throw new BadRequestException("Такой пользователь не зарегистрирован");}
                    description = "Нажмите на ссылку для сброса пароля в Team13";
                }
                case "register/confirm" -> {
                    description = "Нажмите на ссылку для подтверждения регистрации Team13";
                }
                case "email/shift" -> {
                    if (optionalUser.isPresent()) {throw new BadRequestException("Такой пользователь уже зарегистрирован");}
                    description = "Нажмите на ссылку для подтверждения email в Team13";
                    User user = getAuthorizedUser();
                    user.setConfirmationCode(link);
                    userRepository.save(user);
                }
            }

            //отправляем ссылку по email
            mailServiceImpl.sendMessage(email, "Link to changes in your Team13 account",
                    "<p><a href=\"" + request.getScheme() + "://" + request.getHeader("host") + "/api/v1/account/" + route + "?link=" +
                            link + "\">" + description + "</a></p>");
        }
        catch (Exception e) {
            //при любой ошибке возвращаем отрицательный ответ
            log.error("Failed to send code.");
            throw new BadRequestException("Не удалось выслать ссылку");
        }
        return WrapperMapper.wrapMessage(new MessageDTO("ok"));
    }

    @Override
    public Person getInactivePerson() {
        return personRepository.getById(13); //todo ???
    }

    @Override
    @Transactional
    public DTOWrapper setPassword(String token, String password) {

        User user;
        if (jwtTokenProvider.validateToken(token)) {
            user = userRepository.findByName(jwtTokenProvider.getUsername(token)).get();
        } else {
            log.warn("Change password failed (expired token).");
            throw new BadRequestException("Не удалось установить пароль");
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        putTokenToBlackList(token);
        log.info("Password successfully changed: {}", user.getEmail());
        return WrapperMapper.wrapMessage(new MessageDTO("ok"));
    }

    @Override
    @Transactional
    public Boolean setEmail(String token) {

        User user;
        String newEmail = jwtTokenProvider.getUsername(token);
        if (jwtTokenProvider.validateToken(token) &&
                checkUserRegistration(newEmail).isEmpty()) {
            user = userRepository.findByConfirmationCode(token).get();
        } else {
            log.warn("Email change failed (expired token).");
            return false;
        }

        putTokenToBlackList(token);
        String oldEmail = user.getEmail();
        user.setEmail(newEmail);
        Person person = user.getPerson();
        person.setEmail(newEmail);
        user.setConfirmationCode(null);
        userRepository.save(user);
        personRepository.save(person);

        log.info("Email successfully changed '{}' -> '{}'.", oldEmail, user.getEmail());
        return true;
    }

    @Override
    @Transactional
    public DTOWrapper  setNotification(SubscribeResponseDto subscribeType) {

        Person currentPerson = getAuthorizedUser().getPerson();
        Optional<Subscription> optionalSubscription = subscriptionRepository.findByTypeEqualsAndPerson(subscribeType.getType(), currentPerson);
        if (!subscribeType.isEnable() && optionalSubscription.isPresent()) { subscriptionRepository.delete(optionalSubscription.get()); }
        if (subscribeType.isEnable() && optionalSubscription.isEmpty()) {
            Subscription subscription = new Subscription()
                    .setPerson(currentPerson)
                    .setType(subscribeType.getType());
            subscriptionRepository.save(subscription);
        }
        return WrapperMapper.wrap(new MessageDTO("ok"), true);
    }

    @Transactional
    public void deleteUserById(Integer personId) {
        userRepository.deleteUserByPersonId(personId);
    }

    @Override
    @Transactional
    public String resetAndGetToken(String token) {

        //получаем user'a из токена
        User user;
        if (jwtTokenProvider.validateToken(token)) { user = userRepository.findByName(jwtTokenProvider.getUsername(token)).get(); }
        else {
            log.warn("Password reset failed (expired token).");
            return null;
        }
        //сбрасываем пароль
        user.setPassword(UUID.randomUUID().toString());
        //ссылка будет работать на один переход - отправляем токен в blacklist
        putTokenToBlackList(token);

        userRepository.save(user);
        log.info("Password reset successfully: {}.", user.getEmail());
        return jwtTokenProvider.createToken(user.getEmail(), TokenType.RECOVERY);
    }

    @Override
    public Boolean registerConfirm(String token) {

        User user;
        if (jwtTokenProvider.validateToken(token)) {
            user = userRepository.findByEmailNoApproval(jwtTokenProvider.getUsername(token)).get();
        } else {
            log.warn("Registration confirm failed (expired token).");
            return false;
        }
        putTokenToBlackList(token);
        user.setApproved(true);
        userRepository.save(user);
        log.info("Registration confirm success {}", user.getEmail());
        return true;
    }

    @Override
    public DTOWrapper getNotifications() {

        int currentPersonId = getAuthorizedUser().getPerson().getId();
        List<NotificationCode> subscriptionList = subscriptionRepository.findAllTypesByPersonId(currentPersonId);
        List<SubscribeResponseDto> results = Arrays.stream(NotificationCode.values())
                .map(type -> { if (subscriptionList.contains(type))
                                    {return new SubscribeResponseDto(type, true);}
                               else {return new SubscribeResponseDto(type, false);}})
                .collect(Collectors.toList());
        return WrapperMapper.wrap(results, true);
    }

    @Override
    public DTOWrapper getUserActivity(int userId) {
        User user = getUser(userId);

        boolean isOnline = isOnline(user);
        LocalDateTime lastOnlineTime = user.getPerson().getLastOnlineTime();
        Long timestamp = TimeUtil.getTimestamp(lastOnlineTime);
        log.debug("Checking online status for user id {} ({}, last online: {}).", userId, isOnline, lastOnlineTime);
        return WrapperMapper.wrap(Map.of("online", isOnline, "last_activity", timestamp), true);
    }

    @Override
    public DTOWrapper setUserDialogStatus(int userId, String status) {
        User user = getUser(userId);
        JwtUser ud = getUserDetails(user.getEmail());
        ud.setOnlineStatus(status);
        log.debug("Setting status '{}' to user id={} .", status, userId);
        return WrapperMapper.wrap(Map.of("message", "ok"), true);
    }

    private JwtUser getUserDetails(String username) {
        return (JwtUser) sessionRegistry.getAllPrincipals().stream()
                .filter(o -> ((JwtUser) o).getUsername().equals(username))
                .findAny().orElseThrow(() -> new BadRequestException("Internal user ID mismatch"));
    }

    private boolean isOnline(User user) {
        return sessionRegistry.getAllPrincipals().stream()
                .anyMatch(principal -> ((UserDetails) principal).getUsername().equals(user.getEmail()));
    }

    private User getUser(int userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new BadRequestException("User for id=" + userId + " is not found"));
    }

    private void putTokenToBlackList(String token) {

        Date tokenExpirationDate = jwtTokenProvider.resolveTokenDate(token);
        if (new Date().before(tokenExpirationDate)) {
            BlacklistedToken expiredToken = new BlacklistedToken();
            expiredToken.setToken(token);
            expiredToken.setExpiredDate(LocalDateTime.ofInstant(tokenExpirationDate.toInstant().plusSeconds(10), ZoneId.systemDefault()));
            blacklistedTokenRepo.save(expiredToken);
        }
    }
}