package ru.skillbox.team13.service.impl;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
import ru.skillbox.team13.security.TokenType;
import ru.skillbox.team13.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public Boolean register(UserDto.Request.Register userDto) {

            //проверка наличия данного email в бд
            log.debug("Checking email '{}'...", userDto.getEmail());
            if (checkUserRegistration(userDto.getEmail()).isPresent())
                throw new BadRequestException("user is already registered");
            //при несовпадении двух переданных паролей бросаем исключение, которое отдает ответ с нужным статусом и ошибкой
            if (!userDto.getFirstPassword().equals(userDto.getSecondPassword()))
                throw new BadRequestException("passwords don't match");
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
            //заполняем Person имеющимися данными
            User user = new User();
            user.setEmail(userDto.getEmail());
            user.setName(userDto.getEmail());//НЕТ ДАННЫХ, NOT NULL
            user.setPassword(passwordEncoder.encode(userDto.getFirstPassword()));
            user.setType(UserType.USER);//НЕТ ДАННЫХ, NOT NULL
            user.setPerson(personRepository.save(person));
            user.setConfirmationCode(userDto.getCode());
            user.setApproved(true);////НЕТ ДАННЫХ, NOT NULL
            log.debug("Saving user and person data...");
            User registeredUser = userRepository.save(user);
            log.info("New user registered: '{}', type: {}, message permission: {}.",
                    registeredUser.getEmail(), registeredUser.getType().name(), person.getMessagesPermission().name());
            return true;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDTO login(LoginDto loginDto) {

        try {
            String username = loginDto.getEmail();
            User user = userRepository.findByEmail(username).get();
            Person person = personRepository.getById(user.getPerson().getId());
            log.debug("Successful login: {}", loginDto.getEmail());
            return PersonMapper.convertPersonToPersonDTOWithToken(person, jwtTokenProvider.createToken(username, TokenType.ORDINARY));
        } catch (AuthenticationException e) {
            throw new BadRequestException("Invalid username or password");
        }
    }

    @Override
    @Transactional
    public Boolean logout(HttpServletRequest request) {
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
        return true;
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

    private Optional<User> checkUserRegistration(String email) {
        return userRepository.findByEmail(email);
    }

    //метод генерации ссылки и отправки по email кода для смены пароля
    @Override
    @Transactional
    public Boolean codeGenerationAndEmail(String email, String origin){
        try {
            User user = checkUserRegistration(email).orElseThrow(() -> new BadRequestException("user not registered"));
            //генерируем токен для сброса пароля
            String link = jwtTokenProvider.createToken(user.getEmail(), TokenType.MAIL_LINK);
            //отправляем ссылку по email
            mailServiceImpl.sendMessage(email, "Password reset link to Team13",
                    "<p><a href=\"" + origin + "/api/v1/account/password/reset?link=" +
                            link + "\">Нажмите на ссылку для сброса пароля в Team13</a></p>");
        }
        catch (Exception e) {
            //при любой ошибке возвращаем false
            log.error("Failed to send code.");
            return false;
        }
        return true;
    }

    @Override
    public Person getInactivePerson() {
        return personRepository.getById(13); //todo ???
    }

    @Override
    @Transactional
    public Boolean setPassword(String token, String password) {

        User user;
        if (jwtTokenProvider.validateToken(token)) {
            user = userRepository.findByName(jwtTokenProvider.getUsername(token)).get();
        } else {
            log.warn("Change password failed (expired token).");
            return false;
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        putTokenToBlackList(token);
        log.info("Password successfully changed: {}", user.getEmail());
        return true;
    }

    @Override
    @Transactional
    public Boolean setEmail(String email) {
        if (checkUserRegistration(email).isPresent()) return false;
        User user = getAuthorizedUser();
        user.setEmail(email);
        Person person = user.getPerson();
        person.setEmail(email);
        userRepository.save(user);
        personRepository.save(person);
        log.info("Email successfully changed '{}' -> '{}'.", email, user.getEmail());
        return true;
    }

    @Override
    @Transactional
    public DTOWrapper  setNotification(SubscribeResponseDto subscribeType) {
        User user = getAuthorizedUser();
        Subscription subscription = new Subscription();
        if(subscribeType.isEnable() && !user.getPerson().getSubscriptions().contains(subscribeType.getType())) {
            subscription.setPerson(user.getPerson());
            subscription.setType(subscribeType.getType());
            subscriptionRepository.save(subscription);
            return WrapperMapper.wrap(new MessageDTO("ok"), false);
        }
        if(!subscribeType.isEnable() && user.getPerson().getSubscriptions().contains(subscribeType.getType())) {
            subscription.setPerson(user.getPerson());
            subscription.setType(subscribeType.getType());
            subscriptionRepository.delete(subscription);
            return WrapperMapper.wrap(new MessageDTO("ok"), false);
        }
        return null;
    }

    @Transactional
    public void deleteUserById(Integer personId) {
        userRepository.deleteUserByPersonId(personId);
    }

    @Override
    @Transactional
    public String resetPasswordAndGetToken(String token) {
        //получаем user'a из токена
        User user;
        if (jwtTokenProvider.validateToken(token)) { user = userRepository.findByName(jwtTokenProvider.getUsername(token)).get(); }
        else {
            log.warn("Change password failed (expired token).");
            return null;
        }
        //ссылка будет работать на один переход - отправляем токен в blacklist
        putTokenToBlackList(token);
        //сбрасываем пароль
        user.setPassword(UUID.randomUUID().toString());
        userRepository.save(user);
        log.info("Password successfully reset: {}.", user.getEmail());
        return jwtTokenProvider.createToken(user.getEmail(), TokenType.RECOVERY);
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