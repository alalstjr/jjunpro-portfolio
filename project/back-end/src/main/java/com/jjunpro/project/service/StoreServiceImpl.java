package com.jjunpro.project.service;

import com.jjunpro.project.projection.StorePublic;
import com.jjunpro.project.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public StorePublic findByStoreOne(Long id) {
        return storeRepository.findByStoreOne(id);
    }
}
