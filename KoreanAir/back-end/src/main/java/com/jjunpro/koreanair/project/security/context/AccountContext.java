package com.jjunpro.koreanair.project.security.context;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jjunpro.koreanair.project.domain.AccountEntity;

public class AccountContext extends User {
	
	private static final long serialVersionUID = 6919319380734012832L;
	
	private AccountEntity account;

	public AccountContext(
			AccountEntity account,
			String username, 
			String password, 
			Collection<? extends GrantedAuthority> authorities
			) {
		super(username, password, authorities);
		this.account = account;
	}
	
	public static AccountContext fromAccountModel(
			AccountEntity account, 
			List<SimpleGrantedAuthority> userRole
			) {
		
		return new AccountContext(
				account, 
				account.getUserId(), 
				account.getPassword(),
				userRole
				);
	}
	
	public final AccountEntity getAccount() {
		return account;
	}
	
}
