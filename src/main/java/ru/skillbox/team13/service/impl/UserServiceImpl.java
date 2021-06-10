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
import ru.skillbox.team13.entity.*;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.entity.enums.UserType;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.repository.PersonRepo;
import ru.skillbox.team13.repository.RepoBlacklistedToken;
import ru.skillbox.team13.repository.RepoUser;
import ru.skillbox.team13.security.Jwt.JwtTokenProvider;
import ru.skillbox.team13.service.MailService;
import ru.skillbox.team13.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RepoUser userRepository;
    private final PersonRepo personRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RepoBlacklistedToken blacklistedTokenRepo;
    private final MailService mailService;

    @Override
    public Boolean register(UserDto.Request.Register userDto) {

            //проверка наличия данного email в бд
            if (checkUserRegistration(userDto.getEmail()))
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
            User registeredUser = userRepository.save(user);
            log.info("IN register - user: {} successfully registered", registeredUser.getEmail());
            return true;
    }

    @Override
    public PersonDTO login(LoginDto loginDto) {

        try {
            String username = loginDto.getEmail();
            User user = userRepository.findByEmail(username).get();
            Person person = personRepository.getById(user.getPerson().getId());
            log.info("IN login - user: {} successfully login", loginDto.getEmail());
            return PersonMapper.convertPersonToPersonDTOWithToken(person, jwtTokenProvider.createToken(username, user.getType()));
        } catch (AuthenticationException e) {
            throw new BadRequestException("Invalid username or password");
        }
    }

    @Override
    public Boolean logout(HttpServletRequest request) {

        String username = getCurrentUserDto().getEmail();
        try {
            String token = jwtTokenProvider.resolveToken(request);
            Date tokenExpirationDate = jwtTokenProvider.resolveTokenDate(token);
            BlacklistedToken expiredToken = new BlacklistedToken();
            expiredToken.setToken(token);
            expiredToken.setExpiredDate(LocalDateTime.ofInstant(tokenExpirationDate.toInstant().plusSeconds(10), ZoneId.systemDefault()));
            blacklistedTokenRepo.save(expiredToken);
            SecurityContextHolder.clearContext();
            SecurityContextHolder.createEmptyContext();
        }
        catch (JwtException | IllegalArgumentException e) {
            return false;
        }
        log.info("IN logout - user: {} successfully logout", username);
        return true;
    }

    //метод получения текущего авторизованного пользователя
    @Override
    public User getAuthorizedUser() {

        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findByEmail(email).get();
            return user;
        }
        catch (Exception ex) {
            throw new BadRequestException("Non authorized user");
        }
    }

    //очистка blacklistedToken по расписанию
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void blackListExpiredTokensClear(){

        blacklistedTokenRepo.deleteExpiredTokens();
        log.info("Scheduled task complete - IN blackListExpiredTokensClear: expired tokens deleted");
    }

    @Override
    public PersonDTO getCurrentUserDto(){

        return PersonMapper.convertPersonToPersonDTO(getAuthorizedUser().getPerson());
    }

    private Boolean checkUserRegistration(String email) {

        return userRepository.findByEmail(email).isPresent();
    }

    //метод генерации ссылки и отправки по email кода для смены пароля
    @Override
    public Boolean codeGenerationAndEmail(String email, String origin){

        try {
            if (!checkUserRegistration(email)) throw new BadRequestException("user not registered");
            //генерируем ссылку
            String code = UUID.randomUUID().toString().replaceAll("-", "");
            //записываем code в БД
            User user = getAuthorizedUser();
            user.setConfirmationCode(code);
            userRepository.save(user);
            //отправляем ссылку по email
            mailService.sendMessage(getAuthorizedUser().getEmail(), "Password recovery code",
                    "<p>Your password recovery code is: " + code + "</p>");
        }
        catch (Exception e) {
            //при любой ошибке возвращаем false
            return false;
        }
        return true;
    }
}