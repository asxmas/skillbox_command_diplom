package ru.skillbox.team13.security_simple;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.repository.UserRepository;

import java.util.List;

//@Service   -- uncomment to enable
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user: " + username));

        List<GrantedAuthority> authorities;
        switch (user.getType()) {
            case MODERATOR -> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_MODERATOR"));

            case ADMIN -> authorities = List.of(
                    new SimpleGrantedAuthority("ROLE_USER"),
                    new SimpleGrantedAuthority("ROLE_MODERATOR"),
                    new SimpleGrantedAuthority("ROLE_ADMIN"));

            default -> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), authorities);
    }
}
