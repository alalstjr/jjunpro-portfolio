package com.backend.koreanair.repository;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.projection.AccountPublic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value = "SELECT user_id, username, user_role, created_date, modified_date FROM Account", nativeQuery = true)
    Iterable<AccountPublic> findByUserPublic();
}
