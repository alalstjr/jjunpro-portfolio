package com.backend.project.repository;

import com.backend.project.dto.SearchDTO;
import com.backend.project.projection.StorePublic;
import com.backend.project.projection.UniversityPublic;

import java.util.List;

public interface StoreRepositoryDSL {

    public Long findByUniCount(String stoId);

    public List<UniversityPublic> findByStoreUniAll(SearchDTO searchDTO);
    public StorePublic findByStoreOne(Long id);
}
