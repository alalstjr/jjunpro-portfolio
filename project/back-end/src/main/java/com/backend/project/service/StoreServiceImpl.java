package com.backend.project.service;

import com.backend.project.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository store;

    @Override
    public Long findByUniCount(String storeId) {
        return store.findByUniCount(storeId);
    }
}
