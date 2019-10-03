package com.backend.koreanair.service;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.domain.University;
import com.backend.koreanair.dto.UniversitySaveDTO;
import com.backend.koreanair.repository.AccountRepository;
import com.backend.koreanair.repository.UniversityRepository;
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
