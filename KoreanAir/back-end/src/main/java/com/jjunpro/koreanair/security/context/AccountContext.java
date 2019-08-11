package com.jjunpro.koreanair.security.context;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jjunpro.koreanair.domain.Account;
import com.jjunpro.koreanair.enums.EnumMapper;
import com.jjunpro.koreanair.enums.UserRole;
import com.jjunpro.koreanair.security.tokens.JwtPostProcessingToken;

public class AccountContext extends User {

	private Account account;
	
	private static EnumMapper enumMapper;
	
	private AccountContext(
			Account account, 
			String username, 
			String password, 
			Collection<? extends GrantedAuthority> authorities
			) {
		super(username, password, authorities);
		this.account = account;
	}
	
	public AccountContext(String username, String role) {
	        super(username, enumMapper.userRoleList(role));
	}
	
	public static AccountContext fromAccountModel(Account account) {
		return new AccountContext(
				account, 
				account.getUserId(), 
				account.getPassword(),
				enumMapper.userRoleList(account.getUserRole())
				);
	}
	
	public static AccountContext fromJwtPostToken(JwtPostProcessingToken token) {
		return new AccountContext(
				null, 
				token.getUserid(), 
				token.getPassword(), 
				token.getAuthorities()
				);
	}

	public final Account getAccount() {
		return account;
	}
}
