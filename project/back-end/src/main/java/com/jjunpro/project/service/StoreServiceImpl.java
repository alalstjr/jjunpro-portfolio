package com.jjunpro.project.service;

import com.jjunpro.project.dto.SearchDTO;
import com.jjunpro.project.projection.StorePublic;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;

    @Override
    public StorePublic findByStoreOne(Long id) {
        return storeRepository.findByStoreOne(id);
    }

    @Override
    public Long findByUniCount(String storeId) {
        return storeRepository.findByUniCount(storeId);
    }

    @Override
    public List<UniversityPublic> findByStoreUniAll(SearchDTO searchDTO) {
        return storeRepository.findByStoreUniAll(searchDTO);
    }
}
