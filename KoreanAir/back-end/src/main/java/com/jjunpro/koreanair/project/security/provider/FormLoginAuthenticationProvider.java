package com.jjunpro.koreanair.project.security.provider;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jjunpro.koreanair.project.domain.AccountEntity;
import com.jjunpro.koreanair.project.enums.EnumMapper;
import com.jjunpro.koreanair.project.repositoriy.AccountRepository;
import com.jjunpro.koreanair.project.security.context.AccountContext;
import com.jjunpro.koreanair.project.security.token.PostAuthorizationToken;
import com.jjunpro.koreanair.project.security.token.PreAuthorizationToken;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

	private AccountRepository accountRepository;
	
	private BCryptPasswordEncoder passwordEncoder;
	
	private EnumMapper enumMapper; 
	
	@Override
	public Authentication authenticate(
			Authentication authentication
			) throws AuthenticationException {
		
		PreAuthorizationToken token = (PreAuthorizationToken)authentication;
		
		String userId = token.getPrincipal();
		String userPassword = token.getCredentials();
		
		AccountEntity account = accountRepository
				.findByUserId(userId)
				.orElseThrow(() -> new NoSuchElementException("정보에 맞는 계정이 없습니다."));
		
		if(isCorrectPassword(userPassword, account)) {
			
			// UserRole 형을 SimpleGrantedAuthority 바꿔주는 메서드 실행
			List<SimpleGrantedAuthority> userRole = enumMapper.userRoleList(account.getUserRole());
			
			return PostAuthorizationToken
					.getTokenFormAccountContext(AccountContext.fromAccountModel(account, userRole));
		}
		
		// 이곳까지 통과하지 못하면 잘못된 요청으로 접근하지 못한것 그러므로 throw 해줘야 한다.
		throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return PreAuthorizationToken.class.isAssignableFrom(authentication);
	}

	private boolean isCorrectPassword(String password, AccountEntity account) {
		// 비교대상이 앞에와야 한다.
		return passwordEncoder.matches(password, account.getPassword());
	}
}
