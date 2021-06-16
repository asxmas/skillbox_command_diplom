package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.User;

import java.util.Optional;

public interface RepoUser extends CrudRepository<User, Integer> {

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);

    Optional<User> findByConfirmationCode(String token);
}