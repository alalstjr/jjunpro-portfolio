package com.jjunpro.koreanair.account.security.dtos;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
public class FormLoginDto {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String userId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String password;
}
