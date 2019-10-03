package com.backend.koreanair.service;

import com.backend.koreanair.domain.University;
import com.backend.koreanair.dto.UniversitySaveDTO;

public interface UniversityService {

    public University saveOrUpdate(UniversitySaveDTO dto);
}
