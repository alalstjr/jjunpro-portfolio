package com.jjunpro.project.repository;

import com.jjunpro.project.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryDSL {

    Optional<Account> findByUsername(String username);

    Optional<Account> findByNickname(String value);

    Optional<Account> findByEmail(String value);
}
