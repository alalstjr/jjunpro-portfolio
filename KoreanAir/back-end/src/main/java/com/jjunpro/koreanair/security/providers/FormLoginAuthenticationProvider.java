package com.jjunpro.koreanair.security.providers;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import com.jjunpro.koreanair.account.domin.Account;
import com.jjunpro.koreanair.account.repository.AccountRepository;
import com.jjunpro.koreanair.security.AccountContext;
import com.jjunpro.koreanair.security.AccountContextService;
import com.jjunpro.koreanair.security.tokens.PostAuthorizationToken;
import com.jjunpro.koreanair.security.tokens.PreAuthorizationToken;

public class FormLoginAuthenticationProvider implements AuthenticationProvider{
	
	@Autowired
	private AccountContextService accountContextService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		PreAuthorizationToken token = (PreAuthorizationToken)authentication;
		
		String username = token.getUsername();
		String password = token.getUserPassword();
		
		Account account = accountRepository
				.findByUserId(username)
				.orElseThrow(
						() -> new NoSuchElementException("정보에 맞는 계정이 없습니다.")
						);
		
		if(isCorrectPassword(password, account)) {
			return PostAuthorizationToken.getTokenFromAccountContext(AccountContext.fromAccountModel(account));
		}

		// 이곳까지 통과하지 못하면 잘못된 요청으로 접근하지 못한것 그러므로 throw 해줘야 한다.
		throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PreAuthenticatedAuthenticationToken.class.isAssignableFrom(authentication);
		
		/*
		 * PreAuthenticatedAuthenticationToken 으로 들어온 요청은 전부 이곳 필터에 걸치게 되도록 설정한 코드
		 */
	}

	private boolean isCorrectPassword(String password, Account account) {
		return passwordEncoder.matches(account.getPassword(), password);
		// 비교대상이 앞에와야 한다.
	}
}
