package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.EditPersonDto;
import ru.skillbox.team13.dto.MessageDTO;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.entity.Country;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.CityRepository;
import ru.skillbox.team13.repository.CountryRepository;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.QueryDSL.PersonDAO;
import ru.skillbox.team13.service.CommentService;
import ru.skillbox.team13.service.PersonService;
import ru.skillbox.team13.service.PostService;
import ru.skillbox.team13.service.UserService;
import ru.skillbox.team13.util.PageUtil;
import ru.skillbox.team13.util.TimeUtil;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;


@Service
@Slf4j
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonDAO personDAO;
    private final UserService userService;
    private final CityRepository citiesRepository;
    private final CountryRepository countryRepository;
    private final PostService postsService; //todo refactor
    private final CommentService commentService;

    @Override
    @Transactional(readOnly = true)
    public DTOWrapper getMyProfile() { //todo tests
        Person myPerson = userService.getAuthorizedUser().getPerson();
        log.debug("Fetching data for person id={}.", myPerson.getId());
        return WrapperMapper.wrap(PersonMapper.convertPersonToPersonDTO(myPerson), true);
    }

    @Override
    @Transactional
    public DTOWrapper updateMyProfile(String fName, String lName, String about, String cityName, String countryName,
                                      String photo, String phone, Long bdate) {
        //todo tests!
        City city = citiesRepository.findByTitle(cityName).orElse(citiesRepository.save(new City(cityName)));
        Country country = countryRepository.findByTitle(countryName).orElse(countryRepository.save(new Country(countryName)));
        Person myPerson = userService.getAuthorizedUser().getPerson();

        myPerson.setCity(city);
        myPerson.setCountry(country);
        myPerson.setFirstName(fName);
        myPerson.setLastName(lName);
        myPerson.setAbout(about);
        myPerson.setPhoto(photo);
        myPerson.setPhone(phone);
        myPerson.setBirthDate(TimeUtil.getTime(bdate));

        log.debug("Updating person data for id={}.", myPerson.getId());
        personRepository.save(myPerson);

        PersonDTO newDto = PersonMapper.convertPersonToPersonDTO(myPerson);
        return WrapperMapper.wrap(newDto, true);
    }

    @Override
    @Transactional
    public DTOWrapper deleteMyProfile() {
        User user = userService.getAuthorizedUser();
        int personId = user.getPerson().getId();

        postsService.deletePostsForAuthor(personId);
        commentService.deleteCommentsForAuthor(personId);

        log.debug("Deleting person id={}.", personId);
        Person person = personRepository.getById(personId);
        person.setDeleted(true);
        personRepository.save(person);
        userService.deactivateUser(user);

        return WrapperMapper.wrap(new MessageDTO("ok"), true);
    }

    @Override
    public DTOWrapper getProfile(int id) {
        log.debug("Fetching data for person id={}.", id);
        Person person = personRepository.findById(id).orElseThrow(() -> new BadRequestException("No user for id=" + id + " found"));
        return WrapperMapper.wrap(PersonMapper.convertPersonToPersonDTO(person), true);
    }

    @Override
    public DTOWrapper find(String firstName, String lastName, Integer ageFrom, Integer ageTo,
                           String country, String city, int offset, int itemPerPage) {
        int currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        Pageable pageable = PageUtil.getPageable(offset, itemPerPage);

        LocalDateTime dbLatest = nonNull(ageFrom) ? TimeUtil.getBirthday(ageFrom) : null;
        LocalDateTime dbEarliest = nonNull(ageTo) ? TimeUtil.getBirthday(ageTo) : null;

        Page<PersonDTO> page = personDAO.find(currentPersonId, firstName, lastName, dbEarliest, dbLatest, country, city, pageable);
        return WrapperMapper.wrap(page.getContent(), (int) page.getTotalElements(), offset, itemPerPage, true);
    }
}
