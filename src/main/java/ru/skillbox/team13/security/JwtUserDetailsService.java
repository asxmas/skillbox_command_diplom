package ru.skillbox.team13.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.repository.RepoUser;
import ru.skillbox.team13.security.Jwt.JwtUserFactory;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final RepoUser userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username).orElse(userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found")));
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return JwtUserFactory.create(user);
    }
}
