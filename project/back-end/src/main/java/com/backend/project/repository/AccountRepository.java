package com.backend.project.repository;

import com.backend.project.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryDSL {

    Optional<Account> findByUserId(String userId);

    Optional<Account> findByNickname(String nickname);

    Optional<Account> findByEmail(String email);

    //    @Query(value = "SELECT id, user_id, nickname, user_role, email, url_list, created_date, modified_date FROM Account AS a WHERE a.user_id = ?", nativeQuery = true)
    //    AccountPublicTest findOnePublicAccount(String userId);
}
