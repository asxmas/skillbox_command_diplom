package ru.skillbox.team13.service.impl;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
import ru.skillbox.team13.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final RepoUser userRepository;
    private final PersonRepo personRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RepoBlacklistedToken blacklistedTokenRepo;

    @Override
    public Boolean register(UserDto.Request.Register userDto) {

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
        String username = getCurrentUser().getEmail();
        try {
            String token = jwtTokenProvider.resolveToken(request);
            Date tokenExpirationDate = jwtTokenProvider.resolveTokenDate(token);
            BlacklistedToken expiredToken = new BlacklistedToken();
            expiredToken.setToken(token);
            expiredToken.setExpiredDate(LocalDateTime.ofInstant(tokenExpirationDate.toInstant(), ZoneId.systemDefault()));
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
    public User getAuthorizedUser() {
        try {
            String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            User user = userRepository.findByEmail(email).get();
            return user;
        }
        catch (Exception ex) {
            throw new BadRequestException("Non authorized user");
        }
    }

    //очистка blacklistedToken по расписанию
    @Scheduled(fixedRate = 3600000)
    private void blackListExpiredTokensClear(){
        List<BlacklistedToken> expiredTokens = blacklistedTokenRepo.findExpiredTokens();
        blacklistedTokenRepo.deleteAll(expiredTokens);
    }

    @Override
    public PersonDTO getCurrentUser(){
        return PersonMapper.convertPersonToPersonDTO(getAuthorizedUser().getPerson());
    }
}