package ru.skillbox.team13.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.CityDto;
import ru.skillbox.team13.dto.CountryDto;
import ru.skillbox.team13.dto.LoginDto;
import ru.skillbox.team13.dto.UserDto;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.entity.Country;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.PersonMessagePermission;
import ru.skillbox.team13.entity.enums.UserType;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.repository.PersonRepo;
import ru.skillbox.team13.repository.UserRepository;
import ru.skillbox.team13.security.Jwt.JwtTokenProvider;
import ru.skillbox.team13.service.UserService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PersonRepo personRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PersonRepo personRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

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
    public UserDto.Response.AuthPerson login(LoginDto loginDto) {

        try {
            String username = loginDto.getEmail();
            User user = userRepository.findByEmail(username);
            Person person = personRepository.getById(user.getPerson().getId());
            City city = person.getCity();
            CityDto cityDto = null;
            if (city != null) {cityDto = new CityDto(city.getId(), city.getTitle());}
            Country country = person.getCountry();
            CountryDto countryDto = null;
            if (country != null) {countryDto = new CountryDto(country.getId(), country.getTitle());}
            UserDto.Response.AuthPerson authPerson = UserDto.Response.AuthPerson.builder()
                    .firstName(person.getFirstName())
                    .lastName(person.getLastName())
                    .birthDate(person.getBirthDate() == null ? null : person.getBirthDate().toEpochSecond(OffsetDateTime.now().getOffset()))
                    .email(user.getEmail())
                    .phone(person.getPhone())
                    .photo(person.getPhoto())
                    .about(person.getAbout())
                    .city(cityDto)
                    .country(countryDto)
                    .messagesPermissions(person.getMessagesPermission())
                    .lastOnlineTime(person.getLastOnlineTime().toEpochSecond(OffsetDateTime.now().getOffset()))
                    .isBlocked(person.isBlocked())
                    .token(jwtTokenProvider.createToken(username, user.getType()))
                    .build();
            log.info("IN login - user: {} successfully login", loginDto.getEmail());
            return authPerson;
        } catch (AuthenticationException e) {
            throw new BadRequestException("Invalid username or password");
        }
    }

    @Override
    public Boolean logout() {
        //метод включающий текущий токен в blacklist
        return false;
    }
}