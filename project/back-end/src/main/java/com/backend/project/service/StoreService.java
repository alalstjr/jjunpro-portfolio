package com.backend.project.service;

import com.backend.project.domain.Store;
import com.backend.project.dto.StoreDTO;

public interface StoreService {

    public Store saveOrUpdate(StoreDTO dto);
}
