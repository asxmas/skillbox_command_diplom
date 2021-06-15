package ru.skillbox.team13.security;

public enum Permission {
    USER("user"),
    MODERATOR("moderator"),
    ADMIN("admin");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

}
