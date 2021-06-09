package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.FriendshipStatusDto;
import ru.skillbox.team13.dto.MessageDTO;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.Friendship;
import ru.skillbox.team13.entity.FriendshipStatus;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.PersonRepo;
import ru.skillbox.team13.repository.RepoFriendship;
import ru.skillbox.team13.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsServiceImpl {

    private final UserRepository userRepository;
    private final PersonRepo personRepo;
    private final RepoFriendship friendshipRepo;

    public DTOWrapper findFriendByName(String name, int offset, int itemPerPage) {
        if (name.isBlank()) {
            //todo implement custom error 400
            throw new RuntimeException("Name should not be blank");
        }
        int count = personRepo.countPersons("%" + name.toLowerCase() + "%");

        List<PersonDTO> results = findByName(name, offset, itemPerPage);
        return WrapperMapper.wrap(results, count, offset, itemPerPage);
    }

    public DTOWrapper deleteFriend(Integer id) {
        Person person = loadCurrentPerson();

        Friendship friendship = person.getFriendshipsRequested().stream()
                .filter(fship -> fship.getToPerson().getId() == id).findFirst()
                //todo add custom error
                .orElseThrow(() -> new RuntimeException("not in friendship with user id=" + id));

        person.getFriendshipsRequested().remove(friendship);  //todo set 'BLOCKED' or 'DECLINED'
        personRepo.save(person);

        return WrapperMapper.wrap(Collections.singletonList(new MessageDTO("ok")));
    }

    public DTOWrapper addOrAcceptFriend(Integer id) {
        Person person = loadCurrentPerson();

        //check incoming requests
        Friendship friendship = person.getFriendshipsReceived().stream()
                .filter(f -> f.getFromPerson().getId() == id)
                .findFirst()  //todo if found should set status code 'FRIEND' ???
                .orElse(createOutgoingFriendshipRequest(person, id));

        person.getFriendshipsRequested().add(friendship);

        personRepo.save(person);

        return WrapperMapper.wrap(Collections.singletonList(new MessageDTO("ok")));
    }

    public DTOWrapper getFriendshipRequests(String name, int offset, int itemPerPage) {
        User user = userRepository.findByName(name).orElseThrow(() ->
                new RuntimeException("No user for name " + name + " found"));

        Person person = user.getPerson();

        int page = offset / itemPerPage;
        Pageable p = PageRequest.of(page, itemPerPage);

        int count = friendshipRepo.countFriendshipsForPersonWithCode(person, FriendshipStatusCode.REQUEST);
        List<Friendship> requests = friendshipRepo.findFriendshipsForPersonWithCode(p, person, FriendshipStatusCode.REQUEST);

        List<Person> persons = requests.stream().map(Friendship::getFromPerson).collect(Collectors.toList());

        return WrapperMapper.wrap(persons, count, offset, itemPerPage);
    }

    public DTOWrapper getRecommendations(int offset, int itemPerPage) {
        //todo invent 'recommendation' logic
        return WrapperMapper.wrap(new ArrayList<>(), 0, offset, itemPerPage);
    }

    public DTOWrapper getStatusForIds(List<Integer> ids) {
        Person person = loadCurrentPerson();

        List<Friendship> fships = friendshipRepo.getFriendsOfPerson(person, ids);
        List<FriendshipStatusDto> list = fships.stream()
                .map(f -> new FriendshipStatusDto(f.getFromPerson().getId(), f.getStatus().getCode().name()))
                .collect(Collectors.toList());

        return WrapperMapper.onlyData(list);
    }

    private Friendship createOutgoingFriendshipRequest(Person fromPerson, Integer toPersonId) {
        if (fromPerson.getFriendshipsRequested().stream().anyMatch(f -> f.getId() == toPersonId)) {
            throw new RuntimeException("Friendship with user id=" + toPersonId + " is already requested");
        }

        FriendshipStatus status = new FriendshipStatus(LocalDateTime.now(), "", FriendshipStatusCode.REQUEST);
        return new Friendship(status, fromPerson, personRepo.getById(toPersonId));
    }

    private List<PersonDTO> findByName(String name, int offset, int itemPerPage) {
        int page = offset / itemPerPage;
        Pageable p = PageRequest.of(page, itemPerPage);
        List<Person> persons = personRepo.findFriendsByName(p, "%" + name.toLowerCase() + "%");
        return persons.stream().map(PersonMapper::convertPersonToPersonDTO).collect(Collectors.toList());
    }

    private Person loadCurrentPerson() { //todo temporary method
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByName(username).orElseThrow(() ->
                new RuntimeException("No user for name " + username + " found"));
        return user.getPerson();
    }
}
