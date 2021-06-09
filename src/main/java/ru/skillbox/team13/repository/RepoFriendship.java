package ru.skillbox.team13.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Friendship;
import ru.skillbox.team13.entity.Person;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;

import java.util.List;

public interface RepoFriendship extends CrudRepository<Friendship, Integer> {

    @Query("select count(f) from Friendship f where f.toPerson = :person and f.status.code = :code")
    Integer countFriendshipsForPersonWithCode(Person person, FriendshipStatusCode code);

    @Query("select f from Friendship f where f.toPerson = :person and f.status.code = :code")
    List<Friendship> findFriendshipsForPersonWithCode(Pageable p, Person person, FriendshipStatusCode code);

    @Query("select f from Friendship f where f.toPerson = :person and f.fromPerson.id in :ids")
    List<Friendship> getFriendsOfPerson(Person person, List<Integer> ids);
}
