package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.projection.StorePublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreRepositoryDSL {

    Long findByUniCount(String stoId);

    Page<StorePublic> findByStoreUniAll(Pageable pageable, String storeId, Account account);
}
