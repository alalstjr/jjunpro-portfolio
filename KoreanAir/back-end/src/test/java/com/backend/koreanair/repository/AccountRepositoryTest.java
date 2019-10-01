package com.backend.koreanair.repository;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

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

//        accountRepository.save(account);

//        accountRepository.findByUserPublic();

//        accountRepository.findByUserId("userId");

        accountRepository.findOneById(1L);

//        assertThat(entityManager.contains(account)).isTrue();
    }
}