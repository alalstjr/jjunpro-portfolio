package com.backend.project.repository;

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

//        Account account = Account.builder()
//                .userId("userId")
//                .password("password")
//                .nickname("nickname")
//                .myUniversity("myUniversity")
//                .build();
//
//        University uni = University.builder()
//                .uniSubject("uniSubject")
//                .uniName("uniName")
//                .account(account)
//                .build();


//        accountRepository.save(account);
//        universityRepository.save(uni);
//        accountRepository.findAll();
//        universityRepository.findAll();

//            universityRepository.findByIdLike(2L);

//        assertThat(entityManager.contains(account)).isTrue();

    }
}