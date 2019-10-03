package com.backend.koreanair.repository;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.domain.University;
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

    @Autowired
    UniversityRepository universityRepository;

    @Test
    public void showTest() {

        Account account = Account.builder()
                .userId("userId")
                .username("username")
                .password("password")
                .nickname("nickname")
                .myUniversity("myUniversity")
                .userRole(UserRole.USER)
                .build();

        University uni = University.builder()
                .uniSubject("uniSubject")
                .uniName("uniName")
                .uniState(false)
                .account(account)
                .build();

        account.addUniversity(uni);

        accountRepository.save(account);
        universityRepository.save(uni);
        accountRepository.findAll();
        universityRepository.findAll();

//
//        universityRepository.findAll();



//        assertThat(entityManager.contains(account)).isTrue();

    }
}