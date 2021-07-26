package ru.skillbox.team13.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.dto.MessageDTO;
import ru.skillbox.team13.dto.PersonDTO;
import ru.skillbox.team13.entity.Friendship;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;
import ru.skillbox.team13.exception.BadRequestException;
import ru.skillbox.team13.mapper.PersonMapper;
import ru.skillbox.team13.mapper.WrapperMapper;
import ru.skillbox.team13.repository.FriendshipRepository;
import ru.skillbox.team13.repository.PersonRepository;
import ru.skillbox.team13.repository.QueryDSL.PersonDAO;
import ru.skillbox.team13.service.FriendService;
import ru.skillbox.team13.service.UserService;
import ru.skillbox.team13.util.PageUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendSimpleServiceImpl implements FriendService {

    private final UserService userService;
    private final FriendshipRepository friendshipRepository;
    private final PersonRepository personRepository;
    private final PersonDAO personDAO;

    @Override
    public DTOWrapper getFriends(String name, int offset, int itemPerPage) {
        Person srcPerson = userService.getAuthorizedUser().getPerson();
        int srcid = srcPerson.getId();

        if (name.isBlank()) {
            Pageable p = PageUtil.getPageable(offset, itemPerPage);
            Page<Person> friends = friendshipRepository.findFriends(srcid, p);

            List<PersonDTO> list = friends.getContent().stream()
                    .map(PersonMapper::convertPersonToPersonDTO).collect(Collectors.toList());

            log.debug("Loading friends for id={}, total {}.", srcid, friends.getTotalElements());
            return WrapperMapper.wrap(list, (int) (friends.getTotalElements()), offset, itemPerPage, true);

        } else {
            return WrapperMapper.wrapMessage(new MessageDTO("not implemented in FriendSimpleServiceImpl ¯\\_(ツ)_/¯"));
        }
    }

    @Override
    public DTOWrapper deleteFriend(Integer dsId) {
        int srcId = userService.getAuthorizedUser().getPerson().getId();

        friendshipRepository.deleteBySourcePersonIdAndDestinationPersonId(srcId, dsId);
        log.debug("id={} no longer friends with id={}", srcId, dsId);

        return WrapperMapper.wrapMessage(new MessageDTO("ok"));
    }

    @Override
    public DTOWrapper acceptRequestAndAddFriend(Integer friendId) {  //just 'add friend' actually
        Person srcPerson = userService.getAuthorizedUser().getPerson();
        if (friendId.equals(srcPerson.getId())) throw new BadRequestException("Can't add self to friends");

        Person dstPerson = personRepository.findById(friendId)
                .orElseThrow(() -> new BadRequestException("No user for id=" + friendId));

        if (!friendshipRepository.existsBySourcePersonAndDestinationPerson(srcPerson, dstPerson)) {
            Friendship f = new Friendship(LocalDateTime.now(), "Friendship is magic!",
                    FriendshipStatusCode.FRIEND, srcPerson, dstPerson);

            friendshipRepository.save(f);
            log.debug("id={} now friends with id={}.", srcPerson.getId(), friendId);
            return WrapperMapper.wrapMessage(new MessageDTO("ok"));

        } else throw new BadRequestException("Friendship with id=" + friendId + " already exists");
    }

    @Override
    public DTOWrapper getRecommendations(int offset, int itemPerPage) {

        Person thisPerson = userService.getAuthorizedUser().getPerson();

        int id = thisPerson.getId();
        List<Integer> alreadyFriendedIds = friendshipRepository.findFriendsIds(id);
        alreadyFriendedIds.add(id);

        Pageable p = PageUtil.getPageable(offset, itemPerPage);

        Page<PersonDTO> page = personDAO.getPersonsMinusIds(alreadyFriendedIds, p);
        log.debug("Recommending for id={}: total recommendations {} (excluded {}).",
                id, page.getTotalElements(), alreadyFriendedIds.size());
        return WrapperMapper.wrap(page.getContent(), (int) page.getTotalElements(), offset, itemPerPage, true);
    }

    @Override
    public DTOWrapper getFriendshipRequests(String name, int offset, int itemPerPage) {
        return WrapperMapper.wrapMessage(new MessageDTO("not implemented in FriendSimpleServiceImpl ¯\\_(ツ)_/¯"));
    }

    @Override
    public DTOWrapper getStatusForIds(int[] friendsIds) {
        return WrapperMapper.wrapMessage(new MessageDTO("not implemented in FriendSimpleServiceImpl ¯\\_(ツ)_/¯"));
    }
}
