package ru.skillbox.team13.entity.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.skillbox.team13.security.Permission;

import java.util.Set;
import java.util.stream.Collectors;

public enum UserType {

    USER(Set.of(Permission.USER)),
    MODERATOR(Set.of(Permission.MODERATOR, Permission.USER)),
    ADMIN(Set.of(Permission.ADMIN, Permission.MODERATOR, Permission.USER));

    private final Set<Permission> permissions;

    UserType(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return permissions.stream().map(p -> new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
    }
}
