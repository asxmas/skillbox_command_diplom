package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skillbox.team13.entity.FriendshipStatus;

public interface FriendshipStatusRepository extends JpaRepository<FriendshipStatus, Integer> {
}
