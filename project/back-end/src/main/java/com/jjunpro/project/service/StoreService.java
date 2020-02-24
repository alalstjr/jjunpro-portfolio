package com.jjunpro.project.service;

import com.jjunpro.project.dto.SearchDTO;
import com.jjunpro.project.projection.StorePublic;
import com.jjunpro.project.projection.UniversityPublic;

import java.util.List;

public interface StoreService {

    StorePublic findByStoreOne(Long id);

    Long findByUniCount(String keyword);

    List<UniversityPublic> findByStoreUniAll(SearchDTO searchDTO);
}
