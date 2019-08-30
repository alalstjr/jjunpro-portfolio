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
	
	/**
	 * JWT 생성에 사용되는 Account 기본 생성자
	 */
	public AccountContext(
			AccountEntity account,
			String username, 
			String password, 
			Collection<? extends GrantedAuthority> authorities
			) {
		super(username, password, authorities);
		this.account = account;
	}
	
	/**
	 * JWT Decoder 사용되는 Account 기본 생성자
	 */
	public AccountContext(
			String username, 
			Collection<? extends GrantedAuthority> authorities
			) {
        super(username, null, authorities);
	}
	
	/**
	 * 로그인을 시도한 유저의 정보를 담은 AccountContext 생성
	 */
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
	
	/**
	 * 로그인을 시도한 유저의 정보를 담은 AccountContext 접근하는 메서드
	 */
	public final AccountEntity getAccount() {
		return account;
	}
	
}
