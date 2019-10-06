package com.backend.project.service;

import com.backend.project.domain.University;
import com.backend.project.dto.UniversitySaveDTO;

public interface UniversityService {

    public University saveOrUpdate(UniversitySaveDTO dto);
}
