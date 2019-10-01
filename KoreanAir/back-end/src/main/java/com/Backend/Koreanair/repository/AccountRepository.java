package com.backend.koreanair.repository;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.projection.AccountPublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserId(String userId);

    @Query(value = "SELECT id, user_id, username, user_role, created_date, modified_date FROM Account", nativeQuery = true)
    Page<AccountPublic> findByPublicAccountList(Pageable pageable);

    @Query(value = "SELECT id, user_id, username, user_role, created_date, modified_date FROM Account AS a WHERE a.id = ?", nativeQuery = true)
    AccountPublic findOnePublicAccount(Long id);
}
