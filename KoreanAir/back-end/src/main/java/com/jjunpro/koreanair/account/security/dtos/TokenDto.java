package com.jjunpro.koreanair.account.security.dtos;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class TokenDto {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String token;

	public TokenDto(String token) {
		super();
		this.token = token;
	}
}
