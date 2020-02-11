package com.backend.project.security.provider;

import com.backend.project.domain.Account;
import com.backend.project.enums.EnumMapper;
import com.backend.project.repository.AccountRepository;
import com.backend.project.security.context.AccountContext;
import com.backend.project.security.token.PostAuthorizationToken;
import com.backend.project.security.token.PreAuthorizationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final AccountRepository accountRepository;
    private final PasswordEncoder   passwordEncoder;
    private final EnumMapper        enumMapper;

    @Override
    public Authentication authenticate(
            Authentication authentication
    ) throws AuthenticationException {

        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String principal = token
                .getPrincipal()
                .toString();
        String credential = token
                .getCredentials()
                .toString();

        Account account = accountRepository
                .findByUserId(principal)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 계정입니다."));

        if (passwordComparison(
                credential,
                account
        )) {

            List<SimpleGrantedAuthority> userRole = enumMapper.userRoleList(account.getUserRole());

            return PostAuthorizationToken.getTokenFromAccountContext(AccountContext.fromAccountModel(
                    account,
                    userRole
            ));
        }

        throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean passwordComparison(
            String password,
            Account account
    ) {
        // 비교대상이 앞에 와야한다.
        return passwordEncoder.matches(
                password,
                account.getPassword()
        );
    }
}
