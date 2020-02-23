package com.jjunpro.project.repository;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.dto.SearchDTO;
import com.jjunpro.project.projection.UniversityPublic;

import java.util.List;

/**
 * findByUniversityListWhereSearchDto : SearchDto 정보를 참조하여 UniversityList 탐색합니다.
 */
public interface UniversityRepositoryDSL {

    UniversityPublic findByPublicId(
            Long id,
            Account account
    );

    List<UniversityPublic> findByUniversityListWhereSearchDto(SearchDTO searchDTO);

    List<UniversityPublic> findByOrderByCreatedDateDesc(Account account);

    Long findByIdUniCount(String uniName);

    void deleteData(
            Long id,
            Account accountData
    );
}
