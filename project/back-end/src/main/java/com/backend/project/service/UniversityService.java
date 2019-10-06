package com.backend.project.service;

import com.backend.project.domain.University;
import com.backend.project.dto.UniversitySaveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UniversityService {

    public Page<University> findByUniversityList(Pageable pageable);

    public University saveOrUpdate(UniversitySaveDTO dto);
}
