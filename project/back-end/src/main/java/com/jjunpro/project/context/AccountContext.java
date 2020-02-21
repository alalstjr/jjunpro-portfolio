package com.jjunpro.project.context;

import com.jjunpro.project.domain.Account;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class AccountContext extends User {

    private Account account;

    public AccountContext(
            Account account
    ) {
        super(
                account.getUsername(),
                account.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole()))
        );

        // Domain account 접근할 수 있도록 추가
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}