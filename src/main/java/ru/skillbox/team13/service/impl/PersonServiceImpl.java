package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skillbox.team13.dto.*;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.entity.Country;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.CitiesRepository;
import ru.skillbox.team13.repository.CountryRepository;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.service.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final UserService userService;
    private final CitiesRepository citiesRepository;
    private final CountryRepository countryRepository;
    private final PostService postsService; //todo refactor
    private final CityServiceRepo cityServiceRepo; //todo refactor
    private final CountryServiceRepo countryServiceRepo; //todo refactor

    @Override
    @Transactional(readOnly = true)
    public DTOWrapper getMyProfile() { //todo tests
        Person myPerson = userService.getAuthorizedUser().getPerson();
        log.debug("Fetching data for person id={}.", myPerson.getId());
        return WrapperMapper.wrap(PersonMapper.convertPersonToPersonDTO(myPerson), true);
    }

    @Override
    @Transactional
    public DTOWrapper updateMyProfile(EditPersonDto editPersonDto) { //todo tests
        Person myPerson = userService.getAuthorizedUser().getPerson();
        log.debug("Updating person data for id={}.", myPerson.getId());
        fillPersonFields(myPerson, editPersonDto);

        personRepository.save(myPerson);

        PersonDTO newDto = PersonMapper.convertPersonToPersonDTO(myPerson);
        return WrapperMapper.wrap(newDto, true);
    }

    @Override
    @Transactional
    public DTOWrapper deleteMyProfile() { //todo tests
        int personId = userService.getAuthorizedUser().getPerson().getId();
        log.debug("Deleting person id={}.", personId);
        Person person = personRepository.getById(personId);
        postsService.setInactiveAuthor();
        person.setDeleted(true);
        personRepository.save(person);
        return WrapperMapper.wrap(new MessageDTO("ok"), true);
    }

    @Override
    public DTOWrapper getProfile(int id) {
        log.debug("Fetching data for person id={}.", id);
        Person person = personRepository.findById(id).orElseThrow(() -> new BadRequestException("No user for id=" + id + " found"));
        return WrapperMapper.wrap(PersonMapper.convertPersonToPersonDTO(person), true);
    }

    private void fillPersonDTOFields(PersonDTO personDTO) { //todo check
//        CityDto cityDTO = PersonMapper.convertCityToCityDTO(cityServiceRepo.getById(personDTO.getTownId()));
//        CountryDto countryDto = PersonMapper.convertCountryToCountryDTO(countryServiceRepo.getCountryById(personDTO.getCountryId()));
//        personDTO.setCityDto(cityDTO);
//        personDTO.setCountryDto(countryDto);
    }

    private void fillPersonFields(Person person, EditPersonDto dto) {

        if (dto.getFirstName() != null) {
            person.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            person.setLastName(dto.getLastName());
        }
        if (dto.getPhone() != null) {
            person.setPhone(dto.getPhone());
        }
        if (dto.getPhoto() != null) {
            person.setPhoto(dto.getPhoto());
        }
        if (dto.getPermission() != null) {
            person.setMessagesPermission(dto.getPermission());
        }
        if (dto.getCityId() != null) {
            City city = citiesRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new BadRequestException("No city for id " + dto.getCityId() + " found."));
            person.setCity(city);
        }

        if (dto.getCountryId() != null) {
            Country country = countryRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new BadRequestException("No country for id " + dto.getCityId() + " found."));
            person.setCountry(country);
        }
    }
}
