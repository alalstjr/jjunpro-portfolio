package com.backend.project.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class EnumMapper {

    /**
     * <UserRole> Role 값을 List 형태로 변환하는 메서드
     */
    public List<SimpleGrantedAuthority> userRoleList(UserRole userRole) {
        return Arrays
                .asList(userRole)
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * <String> Role 값을 List 형태로 변환하는 메서드
     */
    public List<SimpleGrantedAuthority> userRoleListString(String userRole) {
        return Arrays
                .asList(userRole)
                .stream()
                .map(role -> new SimpleGrantedAuthority(userRole))
                .collect(Collectors.toList());
    }

    /**
     * <String> Client 에서 받은 Role 값이 메칭되는지 확인 후 List 형태로 변환하는 메서드
     */
    public UserRole getRoleByName(String userRole) {
        return Arrays
                .stream(UserRole.values())
                .filter(role -> role.isCorrectName(userRole))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("검색된 권한이 없습니다."));
    }
}