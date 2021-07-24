package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.skillbox.team13.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.email = :email and u.isApproved = true")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmailNoApproval(String email);

    @Query("select u from User u where u.name = :name and u.isApproved = true")
    Optional<User> findByName(String name);

    Optional<User> findByConfirmationCode(String token);

    @Modifying
    @Query("DELETE FROM User WHERE person.id = :id")
    void deleteUserByPersonId(Integer id);
}