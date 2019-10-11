package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.University;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    UniversityRepository university;

    @Override
    public Optional<University> findById(Long id) {
        return university.findById(id);
    }

    @Override
    public Page<UniversityPublic> findByUniversityList(Pageable pageable) {
        return university.findByPublicAll(pageable);
    }

    @Override
    public University saveOrUpdate(UniversitySaveDTO dto) {
        University universityData = dto.toEntity();
        return university.save(universityData);
    }

    @Override
    public University saveOrUpdate(University universityData) {
        return university.save(universityData);
    }

    @Override
    public University findByIdLike(Long id, Account account) {
        return university.findByIdLike(id, account);
    }

    @Override
    public void delete(Long id, String username) {
        university.taskDelete(id, username);
    }
}
