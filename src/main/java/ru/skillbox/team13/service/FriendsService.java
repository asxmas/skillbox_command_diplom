package ru.skillbox.team13.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.repository.PersonRepo;
import ru.skillbox.team13.dto.PersonDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsService {

    private final PersonRepo personRepo;

    public List<PersonDTO> findByName(String name, int offset, int itemPerPage) {
        int page = offset / itemPerPage;
        Pageable p = PageRequest.of(page, itemPerPage);
        List<Person> persons = personRepo.findFriendsByName(p, "%" + name.toLowerCase() + "%");
        return persons.stream().map(PersonMapper::convertPersonToPersonDTO).collect(Collectors.toList());
    }

    public int countByName(String name) {
        return personRepo.countPersons("%" + name.toLowerCase() + "%");
    }
}
