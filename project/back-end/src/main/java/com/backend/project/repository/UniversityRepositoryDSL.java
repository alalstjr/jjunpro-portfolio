package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.dto.SearchDTO;
import com.backend.project.projection.UniversityPublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UniversityRepositoryDSL {

    void deleteData(Long id, Account accountData);

    Page<UniversityPublic> findByPublicAll(Pageable pageable, Account account);
    List<UniversityPublic> findByUniversityListWhereAccountId(SearchDTO searchDTO);
    List<UniversityPublic> findByUniversityListWhereAccountNickname(SearchDTO searchDTO);
    List<UniversityPublic> findByLikeListWhereAccountId(SearchDTO searchDTO);
    List<UniversityPublic> findByUniversityListWhereKeyword(SearchDTO searchDTO);
    List<UniversityPublic> findByOrderByCreatedDateDesc(Account account);
    List<UniversityPublic> findByOrderByMostLike(Account account);
    Long findByIdUniCount(String uniName);

    UniversityPublic findByPublicId(Long id, Account account);

    Boolean findByIdLike(Long id, Account account);
}
