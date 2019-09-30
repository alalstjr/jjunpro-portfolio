package com.backend.koreanair.security.provider;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.enums.EnumMapper;
import com.backend.koreanair.repository.AccountRepository;
import com.backend.koreanair.security.context.AccountContext;
import com.backend.koreanair.security.token.PostAuthorizationToken;
import com.backend.koreanair.security.token.PreAuthorizationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnumMapper enumMapper;

    @Override
    public Authentication authenticate(
            Authentication authentication
    ) throws AuthenticationException {

        PreAuthorizationToken token = (PreAuthorizationToken)authentication;

        String principal = token.getPrincipal().toString();
        String credential = token.getCredentials().toString();

        Account account = accountRepository
                .findByUserId(principal)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 계정입니다."));

        if(passwordComparison(credential, account)) {

            List<SimpleGrantedAuthority> userRole =
                    enumMapper.userRoleList(account.getUserRole());

            return PostAuthorizationToken
                    .getTokenFromAccountContext(
                            AccountContext.fromAccountModel(account, userRole)
                    );
        }

        throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean passwordComparison(String password, Account account) {
        // 비교대상이 앞에 와야한다.
        return passwordEncoder.matches(password, account.getPassword());
    }
}
