package com.backend.koreanair.controller;

import com.backend.koreanair.domain.University;
import com.backend.koreanair.dto.UniversitySaveDTO;
import com.backend.koreanair.service.UniversityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/university")
@CrossOrigin
public class UniversityController {

    @Autowired
    UniversityServiceImpl universityService;

    @PostMapping("")
    public ResponseEntity<University> saveOrUpdate(
            @Valid
            @RequestBody UniversitySaveDTO dto
    ) {
        University newUniversity = universityService.saveOrUpdate(dto);

        return new ResponseEntity<University>(newUniversity, HttpStatus.CREATED);
    }
}
