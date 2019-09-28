package com.backend.koreanair.enums;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String role_user;

    UserRole(String role_user) {
        this.role_user = role_user;
    }
}
