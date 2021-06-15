package ru.skillbox.team13.service;

import ru.skillbox.team13.dto.DTOWrapper;

public interface FriendsService {
    DTOWrapper getFriends(String name, int offset, int itemPerPage);

    DTOWrapper deleteFriend(Integer friendPersonId);

    DTOWrapper acceptRequestAndAddFriend(Integer friendId);

    DTOWrapper getFriendshipRequests(String name, int offset, int itemPerPage);

    DTOWrapper getRecommendations(int offset, int itemPerPage);

    DTOWrapper getStatusForIds(int[] friendsIds);
}
