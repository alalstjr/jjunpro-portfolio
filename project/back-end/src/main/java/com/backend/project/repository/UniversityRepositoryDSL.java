package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.projection.UniversityPublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UniversityRepositoryDSL {

    Page<UniversityPublic> findByPublicAll(Pageable pageable, Account account);
    Page<UniversityPublic> findByUniversityListWhereAccountId(Pageable pageable, Account account, String userId);
    Page<UniversityPublic> findByLikeListWhereAccountId(Pageable pageable, Account account, String userId);

    UniversityPublic findByPublicId(Long id, Account account);

    Boolean findByIdLike(Long id, Account account);

    void deleteTask(Long id, Account accountData);
}
