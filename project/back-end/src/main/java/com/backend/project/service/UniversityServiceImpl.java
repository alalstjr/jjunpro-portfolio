package com.backend.project.service;

import com.backend.project.domain.University;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    UniversityRepository university;

    @Override
    public Page<University> findByUniversityList(Pageable pageable) {
        return university.findAll(pageable);
//        return account.findByPublicAccountList(pageable);
    }

    @Override
    public University saveOrUpdate(UniversitySaveDTO dto) {

        University universityData = dto.toEntity();

        return university.save(universityData);
    }
}
