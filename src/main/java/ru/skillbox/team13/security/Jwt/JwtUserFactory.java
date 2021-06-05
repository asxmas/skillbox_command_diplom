package ru.skillbox.team13.security.Jwt;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.skillbox.team13.entity.User;
import ru.skillbox.team13.entity.enums.UserType;

import java.util.ArrayList;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
            user.getName(),
            user.getPassword(),
            true,
            mapToGrantedAuthorities(user.getType())
        );
    }

    private static ArrayList<SimpleGrantedAuthority> mapToGrantedAuthorities(UserType userRole) {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userRole.name()));
        return authorities;
    }
}
