package ru.skillbox.team13.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.FriendshipStatus;

public interface RepoFriendshipStatus extends CrudRepository<FriendshipStatus, Integer> {
}
