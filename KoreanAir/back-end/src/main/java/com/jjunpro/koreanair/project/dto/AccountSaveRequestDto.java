package com.jjunpro.koreanair.project.dto;

import com.jjunpro.koreanair.project.domain.AccountEntity;
import com.jjunpro.koreanair.project.enums.UserRole;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountSaveRequestDto {
	private String userId;
	private String password;
	private String username;
	private UserRole userRole = UserRole.USER;
	
	public AccountEntity toEntity() {
		return AccountEntity.builder()
				.userId(userId)
				.password(password)
				.username(username)
				.userRole(userRole)
				.build();
	}
}
