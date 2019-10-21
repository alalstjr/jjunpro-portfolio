package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.domain.University;
import com.backend.project.projection.UniversityPublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UniversityRepositoryDSL {

    Page<UniversityPublic> findByPublicAll(Pageable pageable);

    UniversityPublic findByPublicId(Long id);

    University findByIdLike(Long id, Account account);

    void deleteTask(Long id, Account accountData);
}
