package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.University;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.projection.UniversityPublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UniversityService {

    public Optional<University> findById(Long id);
    public Page<UniversityPublic> findByUniversityList(Pageable pageable);
    public University findByIdLike(Long id, Account account);

    public University saveOrUpdate(UniversitySaveDTO dto);
    public University saveOrUpdate(University universityData);

    public void delete(Long id, String username);
}
