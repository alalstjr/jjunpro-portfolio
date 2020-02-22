package com.jjunpro.project.repository;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.projection.UniversityPublic;

public interface UniversityRepositoryDSL {

    UniversityPublic findByPublicId(
            Long id,
            Account account
    );
}
