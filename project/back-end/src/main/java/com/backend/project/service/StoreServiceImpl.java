package com.backend.project.service;

import com.backend.project.dto.SearchDTO;
import com.backend.project.projection.StorePublic;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository store;

    @Override
    public Long findByUniCount(String storeId) {
        return store.findByUniCount(storeId);
    }

    @Override
    public List<UniversityPublic> findByStoreUniAll(SearchDTO searchDTO) {
        return store.findByStoreUniAll(searchDTO);
    }

    @Override
    public StorePublic findByStoreOne(Long id) {
        return store.findByStoreOne(id);
    }
}
