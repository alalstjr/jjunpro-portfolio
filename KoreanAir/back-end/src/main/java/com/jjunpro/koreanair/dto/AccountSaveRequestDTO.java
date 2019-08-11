package com.jjunpro.koreanair.dto;


import com.jjunpro.koreanair.domain.Account;
import com.jjunpro.koreanair.enums.UserRole;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class AccountSaveRequestDTO {
	private String userId;
	private String username;
	private String password;
	private UserRole userRole = UserRole.USER;
	
	public Account toEntity() {
		return Account.builder()
				.userId(userId)
				.username(username)
				.password(password)
				.userRole(userRole)
				.build();
	}
}
