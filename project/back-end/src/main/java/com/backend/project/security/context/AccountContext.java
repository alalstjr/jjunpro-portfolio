package com.backend.project.security.context;

import com.backend.project.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class AccountContext extends User {

    private Account account;

    public AccountContext(
            Account account, String username, String password, Collection<? extends GrantedAuthority> authorities
    ) {
        super(
                username,
                password,
                authorities
        );
        this.account = account;
    }

    public AccountContext(
            String username, List<SimpleGrantedAuthority> userRole
    ) {
        super(
                username,
                "password",
                userRole
        );
    }

    public static AccountContext fromAccountModel(
            Account account, List<SimpleGrantedAuthority> userRole
    ) {
        return new AccountContext(
                account,
                account.getUserId(),
                account.getPassword(),
                userRole
        );
    }

    public final Account getAccount() {
        return account;
    }
}
