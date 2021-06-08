package ru.skillbox.team13.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.skillbox.team13.entity.BlacklistedToken;

import java.util.List;
import java.util.Optional;

public interface RepoBlacklistedToken extends CrudRepository<BlacklistedToken, Integer> {

    Optional<BlacklistedToken> findByToken(String token);

    @Query(value = "SELECT token FROM BlacklistedToken token WHERE token.expiredDate < CURRENT_TIMESTAMP")
    List<BlacklistedToken> findExpiredTokens();
}
