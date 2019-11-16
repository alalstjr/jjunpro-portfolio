package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository store;

    @Override
    public Long findByUniCount(String storeId) {
        return store.findByUniCount(storeId);
    }

    @Override
    public List<UniversityPublic> findByStoreUniAll(String storeId, Account account) {
        return store.findByStoreUniAll(storeId, account);
    }
}
