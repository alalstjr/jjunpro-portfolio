package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.projection.StorePublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreService {

    public Long findByUniCount(String storeId);
    public Page<StorePublic> findByStoreUniAll(Pageable pageable, String storeId, Account account);
}
