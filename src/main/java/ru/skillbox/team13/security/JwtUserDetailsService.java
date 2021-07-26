package ru.skillbox.team13.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.repository.UserRepository;
import ru.skillbox.team13.security.Jwt.JwtUserFactory;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user;
        Optional<User> optionalUser = userRepository.findByEmail(username);
        if (optionalUser.isEmpty()) { throw new UsernameNotFoundException("User with username: " + username + " not found"); }
        else { user = optionalUser.get(); }
//        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return JwtUserFactory.create(user);
    }
}
