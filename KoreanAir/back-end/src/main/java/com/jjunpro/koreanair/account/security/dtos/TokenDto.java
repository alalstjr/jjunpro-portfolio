package com.jjunpro.koreanair.account.security.dtos;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class TokenDto {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String token;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String userId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String username;

	public TokenDto(String token, String userId, String username) {
		super();
		this.token = token;
		this.userId = userId;
		this.username = username;
	}
}
