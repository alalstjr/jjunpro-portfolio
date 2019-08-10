package com.jjunpro.koreanair.account.enums;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class EnumMapper {

	private Map<String, List<EnumValue>> factory = new HashMap<String, List<EnumValue>>();

	// Role List 
	private List<EnumValue> toEnumValues(Class<? extends EnumModel> e) {
		return Arrays
				.stream(e.getEnumConstants())
				.map(EnumValue::new)
				.collect(Collectors.toList());
	}
	
	// 특정 Role 값을 List 형태로 변환
	private List<SimpleGrantedAuthority> toEnumValuesList(UserRole userRole) {
		return Arrays
				.asList(userRole)
				.stream()
				.map(r -> new SimpleGrantedAuthority(r.getValue()))
				.collect(Collectors.toList());
	}
}
