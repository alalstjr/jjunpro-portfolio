package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.projection.StorePublic;
import com.backend.project.projection.UniversityPublic;

import java.util.List;

public interface StoreRepositoryDSL {

    public Long findByUniCount(String stoId);

    public List<UniversityPublic> findByStoreUniAll(String storeId, Account account, Long offsetCount);
    public StorePublic findByStoreOne(Long id);
}
