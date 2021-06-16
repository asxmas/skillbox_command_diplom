package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.MessageDTO;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.dto.UserFriendshipStatusDTO;
import ru.skillbox.team13.entity.City;
import ru.skillbox.team13.entity.Friendship;
import ru.skillbox.team13.entity.FriendshipStatus;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.PersonRepo;
import ru.skillbox.team13.repository.RepoFriendship;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsServiceImpl implements ru.skillbox.team13.service.FriendsService {

    private final PersonRepo personRepo;
    private final RepoFriendship friendshipRepo;
    private final UserServiceImpl userService;  //todo UserService interface's not working

    //if no 'name' is present returns all persons with code 'FRIEND' for this person
    //else returns matching persons with code 'FRIEND' for this person
    //only for requested (outgoing) friendships!
    @Override
    public DTOWrapper getFriends(String name, int offset, int itemPerPage) {
        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        int count;
        List<Friendship> friendships;
        Pageable p = getPageable(offset, itemPerPage);

        if (name.isBlank()) {
            count = friendshipRepo.countRequestedFriendships(currentPersonId, FriendshipStatusCode.FRIEND);
            friendships = friendshipRepo.findRequestedFriendships(p, currentPersonId, FriendshipStatusCode.FRIEND);
        } else {
            count = friendshipRepo.countRequestedFriendships(currentPersonId, FriendshipStatusCode.FRIEND,
                    "%" + name.toLowerCase() + "%");
            friendships = friendshipRepo.findRequestedFriendships(p, currentPersonId, FriendshipStatusCode.FRIEND,
                    "%" + name.toLowerCase() + "%");
        }

        List<PersonDTO> results = friendships.stream().map(Friendship::getDestinationPerson)
                .map(PersonMapper::convertPersonToPersonDTO).collect(Collectors.toList());

        return WrapperMapper.wrap(results, count, offset, itemPerPage);
    }

    @Override
    @Modifying
    @Transactional
    //deletes friendship row for srcPerson (this) and dstPerson (id)
    //deletes friendship with any friendship status code
    public DTOWrapper deleteFriend(Integer friendPersonId) {
        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        Friendship friendship = getRequestedFriendship(currentPersonId, friendPersonId);

        friendshipRepo.delete(friendship); //todo set FSC to 'BLOCKED' or 'DECLINED' ???

        return WrapperMapper.wrapMessage(new MessageDTO("ok"));
    }

    @Override
    @Modifying
    @Transactional
    //if received friendship from personId (src) with 'REQUEST' exists, then change it to 'FRIEND'
    //create reverse friendship (dst = personId; src=this user) with 'REQUEST'
    public DTOWrapper acceptRequestAndAddFriend(Integer friendId) {
        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        //incoming friendship (friend -> current)
        //if friendship with any other code exist, nothing happens
        acceptRequestIfExists(friendId, currentPersonId, FriendshipStatusCode.REQUEST);

        //reverse friendship (current -> friend)
        //if friendship with any code exists, the exception will be thrown
        sendRequestIfNotExists(friendId, currentPersonId);

        return WrapperMapper.wrapMessage(new MessageDTO("ok"));
    }

    //if no 'name' present, returns all persons with 'REQUEST' status
    //else returns matching persons with code 'REQUEST'
    //only for incoming (received) friendships!
    @Override
    public DTOWrapper getFriendshipRequests(String name, int offset, int itemPerPage) {
        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();

        int count;
        List<Friendship> friendships;
        Pageable p = getPageable(offset, itemPerPage);

        if (name.isBlank()) {
            count = friendshipRepo.countReceivedFriendships(currentPersonId, FriendshipStatusCode.REQUEST);
            friendships = friendshipRepo.findReceivedFriendships(p, currentPersonId, FriendshipStatusCode.REQUEST);
        } else {
            count = friendshipRepo.countReceivedFriendships(currentPersonId, FriendshipStatusCode.REQUEST,
                    "%" + name.toLowerCase() + "%");
            friendships = friendshipRepo.findReceivedFriendships(p, currentPersonId, FriendshipStatusCode.REQUEST,
                    "%" + name.toLowerCase() + "%");
        }

        List<PersonDTO> results = friendships.stream().map(Friendship::getSourcePerson)
                .map(PersonMapper::convertPersonToPersonDTO).collect(Collectors.toList());

        return WrapperMapper.wrap(results, count, offset, itemPerPage);
    }

    //returns all users from same city (already friended and not)
    @Override
    public DTOWrapper getRecommendations(int offset, int itemPerPage) {
        Person thisPerson = userService.getAuthorizedUser().getPerson();
        City city = thisPerson.getCity();
        Pageable p = getPageable(offset, itemPerPage);

        Integer count = personRepo.countByCity(city);
        List<Person> personList = personRepo.findByCity(p, city);

        List<PersonDTO> results = personList.stream()
                .map(PersonMapper::convertPersonToPersonDTO).collect(Collectors.toList());

        return WrapperMapper.wrap(results, count, offset, itemPerPage);
    }

    //returns statuses only for incoming friendships
    //not actual USER IDs, but PERSON IDs
    @Override
    public DTOWrapper getStatusForIds(int[] friendsIds) {
        Integer currentPersonId = userService.getAuthorizedUser().getPerson().getId();
        List<Friendship> friendships = friendshipRepo.findFriendshipsFromIdsToId(currentPersonId, friendsIds);
        List<UserFriendshipStatusDTO> results = friendships.stream()
                .map(f -> new UserFriendshipStatusDTO(f.getSourcePerson().getId(), f.getStatus().getCode().name()))
                .collect(Collectors.toList());
        return WrapperMapper.wrapDataOnly(results);
    }

    private void acceptRequestIfExists(Integer srcFriendId, Integer dstCurrentPersonId, FriendshipStatusCode code) {
        Friendship friendship = null;
        try {
            friendship = getRequestedFriendship(srcFriendId, dstCurrentPersonId, code);
        } catch (BadRequestException ignored) {
        }
        if (friendship != null) {
            friendship.getStatus().setCode(FriendshipStatusCode.FRIEND);
            friendshipRepo.save(friendship);
        }
    }

    private void sendRequestIfNotExists(Integer dstFriendId, Integer srcCurrentPersonId) {
        Friendship friendship = null;
        try {
            friendship = getRequestedFriendship(srcCurrentPersonId, dstFriendId); //todo rewrite to 'exists..' db request
        } catch (BadRequestException ignored) {
        }

        if (friendship != null) {
            throw new BadRequestException(
                    "friendship from id=" + srcCurrentPersonId + " to id=" + dstFriendId + " already exists " +
                            "with code=" + friendship.getStatus().getCode());
        } else {
            friendship = createNewFriendship(srcCurrentPersonId, dstFriendId, FriendshipStatusCode.REQUEST);
            friendshipRepo.save(friendship);
        }
    }

    private Friendship createNewFriendship(Integer src, Integer dst, FriendshipStatusCode code) {
        Person srcPerson = personRepo.findById(src).get(); //todo exception handling
        Person dstPerson = personRepo.findById(dst).get();
        FriendshipStatus status = new FriendshipStatus(LocalDateTime.now(), "", FriendshipStatusCode.REQUEST);
        return new Friendship(status, srcPerson, dstPerson);
    }

    private Friendship getRequestedFriendship(Integer src, Integer dst) {
        return friendshipRepo.findRequestedFriendship(src, dst)
                .orElseThrow(() -> new BadRequestException("no friendship from id=" + src + " to id=" + dst));
    }

    private Friendship getRequestedFriendship(Integer src, Integer dst, FriendshipStatusCode code) {
        return friendshipRepo.findRequestedFriendship(src, dst, code).orElseThrow(() -> new BadRequestException(
                "no friendship from id=" + src + " to id=" + dst + " with code=" + code));
    }

    private Pageable getPageable(int offset, int itemPerPage) {
        int page = offset / itemPerPage;
        return PageRequest.of(page, itemPerPage);
    }
}
