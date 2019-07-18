package com.jjunpro.koreanair.account.exception;

public class InvalidJwtException extends RuntimeException {

	public InvalidJwtException(String msg) 
	{
		super(msg);
	}

}
