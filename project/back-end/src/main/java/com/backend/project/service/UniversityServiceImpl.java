package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.University;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.repository.AccountRepository;
import com.backend.project.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    UniversityRepository university;

    @Autowired
    AccountRepository account;

    @Override
    public University saveOrUpdate(UniversitySaveDTO dto) {
        Account accountData = account.findById(1L).get();
        dto.setAccount(accountData);
        University universityData = dto.toEntity();

        return university.save(universityData);
    }
}
