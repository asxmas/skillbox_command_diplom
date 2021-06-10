package ru.skillbox.team13.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.Friendship;
import ru.skillbox.team13.entity.enums.FriendshipStatusCode;

import java.util.List;
import java.util.Optional;

public interface RepoFriendship extends CrudRepository<Friendship, Integer> {

    @Query("select count(f) from Friendship f where f.sourcePerson.id = :srcId and f.status.code = :code")
    int countRequestedFriendships(Integer srcId, FriendshipStatusCode code);

    @Query("select count(f) from Friendship f where f.sourcePerson.id = :srcId and f.status.code = :code and " +
            "(f.destinationPerson.lastName like :name or f.destinationPerson.firstName like :name)")
    int countRequestedFriendships(Integer srcId, FriendshipStatusCode code, String name);

    @Query("select count(f) from Friendship f where f.destinationPerson.id = :dstId and f.status.code = :code")
    int countReceivedFriendships(Integer dstId, FriendshipStatusCode code);

    @Query("select count(f) from Friendship f where f.destinationPerson.id = :dstId and f.status.code = :code and " +
            "(f.destinationPerson.lastName like :name or f.destinationPerson.firstName like :name)")
    int countReceivedFriendships(Integer dstId, FriendshipStatusCode code, String name);

    @Query("select f from Friendship f where f.sourcePerson.id = :srcId and f.status.code = :code")
    List<Friendship> findRequestedFriendships(Pageable p, Integer srcId, FriendshipStatusCode code);

    @Query("select f from Friendship f where f.sourcePerson.id = :srcId and f.status.code = :code and " +
            "(f.destinationPerson.lastName like :name or f.destinationPerson.firstName like :name)")
    List<Friendship> findRequestedFriendships(Pageable p, Integer srcId, FriendshipStatusCode code, String name);

    @Query("select f from Friendship f where f.destinationPerson.id = :dstId and f.status.code = :code")
    List<Friendship> findReceivedFriendships(Pageable p, Integer dstId, FriendshipStatusCode code);

    @Query("select f from Friendship f where f.destinationPerson.id = :dstId and f.status.code = :code and " +
            "(f.destinationPerson.lastName like :name or f.destinationPerson.firstName like :name)")
    List<Friendship> findReceivedFriendships(Pageable p, Integer dstId, FriendshipStatusCode code, String name);

    @Query("select f from Friendship f where f.sourcePerson.id = :src and f.destinationPerson.id = :dst")
    Optional<Friendship> findRequestedFriendship(Integer src, Integer dst);

    @Query("select f from Friendship f where f.sourcePerson.id = :src and f.destinationPerson.id = :dstId " +
            "and f.status.code = :code")
    Optional<Friendship> findRequestedFriendship(Integer src, Integer dst, FriendshipStatusCode code);

    @Query("select f from Friendship f where f.destinationPerson = :dstId and f.sourcePerson in :srcIds")
    List<Friendship> findFriendshipsFromIdsToId(Integer dstId, int[] srcIds);

}
