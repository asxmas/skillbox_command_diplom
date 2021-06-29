package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;

import java.util.List;

public interface FriendsService {
    DTOWrapper getFriends(String name, int offset, int itemPerPage);

    DTOWrapper deleteFriend(Integer friendPersonId);

    DTOWrapper acceptRequestAndAddFriend(Integer friendId);

    DTOWrapper getFriendshipRequests(String name, int offset, int itemPerPage);

    DTOWrapper getRecommendations(int offset, int itemPerPage);

    DTOWrapper getStatusForIds(int[] friendsIds);

    List<Person> getFriends(Integer srcId, FriendshipStatusCode code);
}
