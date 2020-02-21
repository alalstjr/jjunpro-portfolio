package com.jjunpro.project.security.provider;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.security.token.PostAuthorizationToken;
import com.jjunpro.project.security.token.PreAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

/* JWToken 발급을 위한 인증과정이 이루어지는 FormLogin Provider */
@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder   passwordEncoder;
    private final AccountRepository accountRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token
                .getPrincipal()
                .toString();

        String password = token
                .getCredentials()
                .toString();

        Account account = accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 계정입니다."));

        if (passwordComparison(
                password,
                account
        )) {
            return PostAuthorizationToken.getTokenFromAccountContext(new AccountContext(account));
        }

        throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    /* 비교대상이 앞에 와야한다. */
    private boolean passwordComparison(
            String password,
            Account account
    ) {
        return passwordEncoder.matches(
                password,
                account.getPassword()
        );
    }
}
