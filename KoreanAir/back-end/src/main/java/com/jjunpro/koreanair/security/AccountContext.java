package com.jjunpro.koreanair.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.jjunpro.koreanair.account.domin.Account;
import com.jjunpro.koreanair.account.domin.UserRole;

public class AccountContext extends User {

	
	private AccountContext(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public static AccountContext fromAccountModel(Account account) {
		return new AccountContext(account.getUserId(), account.getPassword(), parseAuthorities(account.getUserRole()));
	}

	/*
	 * UserRole 은 List로 들어가야 하기때문에 List로 변환후 값을 넣어주도록 밑에 변환 코드 맵핑해주는 코드를 만듭니다.
	 * */
	private static List<SimpleGrantedAuthority> parseAuthorities(UserRole role) {
		return Arrays.asList(role)
				.stream()
				.map(r -> new SimpleGrantedAuthority(r.getRoleName()))
				.collect(Collectors.toList()
				);
	}
	
}
