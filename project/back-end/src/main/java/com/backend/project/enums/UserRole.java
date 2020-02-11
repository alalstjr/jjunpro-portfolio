package com.backend.project.enums;

public enum UserRole implements EnumModel {
    
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String role_user;

    UserRole(String role_user) {
        this.role_user = role_user;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return role_user;
    }

    /**
     * 전달받은 ROLE 값이 Enum 값에 존재하는지 체크하는 메서드
     */
    public boolean isCorrectName(String name) {
        return name.equalsIgnoreCase(getKey());
    }
}
