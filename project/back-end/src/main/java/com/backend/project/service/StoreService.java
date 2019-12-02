package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.projection.StorePublic;
import com.backend.project.projection.UniversityPublic;

import java.util.List;

public interface StoreService {

    public Long findByUniCount(String storeId);
    public List<UniversityPublic> findByStoreUniAll(String storeId, Account account, Long offsetCount);
    public StorePublic findByStoreOne(Long id);
}
