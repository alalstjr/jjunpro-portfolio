package com.backend.koreanair.controller;

import com.backend.koreanair.domain.Account;
import com.backend.koreanair.domain.University;
import com.backend.koreanair.dto.UniversitySaveDTO;
import com.backend.koreanair.repository.AccountRepository;
import com.backend.koreanair.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/university")
@CrossOrigin
public class UniversityController {

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("")
    public ResponseEntity<University> saveOrUpdate(
            @Valid
            @RequestBody UniversitySaveDTO dto
    ) {

        Account account = accountRepository.findById(2L).get();
        account.addUniversity(dto);

        System.out.println(dto.getAccount());

        accountRepository.save(account);

        University university = dto.toEntity();
        universityRepository.save(university);

        return null;
    }
}
