package com.backend.koreanair.repository;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void showTest() {
        Account account = Account.builder()
                .userId("userId")
                .username("username")
                .password("password")
                .userRole(UserRole.USER)
                .build();

        accountRepository.save(account);

        accountRepository.findByUserPublic();

        assertThat(entityManager.contains(account)).isTrue();
    }
}