package com.backend.project.service;

import com.backend.project.dto.SearchDTO;
import com.backend.project.projection.StorePublic;
import com.backend.project.projection.UniversityPublic;

import java.util.List;

public interface StoreService {

    public Long findByUniCount(String keyword);

    public List<UniversityPublic> findByStoreUniAll(SearchDTO searchDTO);

    public StorePublic findByStoreOne(Long id);
}