package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.projection.UniversityPublic;

import java.util.List;

public interface StoreRepositoryDSL {

    Long findByUniCount(String stoId);

    List<UniversityPublic> findByStoreUniAll(String storeId, Account account, Long offsetCount);
}
