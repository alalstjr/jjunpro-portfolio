package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.domain.University;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.respone.WebProcessRespone;
import com.backend.project.service.AccountServiceImpl;
import com.backend.project.service.UniversityServiceImpl;
import com.backend.project.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/university")
@CrossOrigin
public class UniversityController {

    @Autowired
    UniversityServiceImpl universityService;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    private WebProcessRespone webProcessRespone;

    @Autowired
    private IpUtil ipUtil;

    // CREATE or UADATE 생성
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<University> saveOrUpdate(
            @Valid
            @RequestBody UniversitySaveDTO dto,
            BindingResult bindingResult,
            Authentication authentication,
            HttpServletRequest request
    ) {
        String errorType = null;
        String errorText = null;

        // Field Check
        if(bindingResult.hasErrors()) {
            return webProcessRespone.webErrorRespone(bindingResult);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<Account> accountData = accountService.findByUserId(userDetails.getUsername());

        if(!accountData.isPresent()) {
            errorType = "AuthenticationError";
            errorText = "올바른 접근이 아닙니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        if(ipUtil.getUserIp(request).equals("0.0.0.0")) {
            errorType = "AuthenticationError";
            errorText = "올바른 접근이 아닙니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        if(dto.getUniStar() < 0 || dto.getUniStar() >= 6) {
            errorType = "AuthenticationError";
            errorText = "잘못된 점수입니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        dto.setAccount(accountData.get());
        dto.setUniIp(ipUtil.getUserIp(request));

        University newUniversity = universityService.saveOrUpdate(dto);
        return new ResponseEntity<University>(newUniversity, HttpStatus.CREATED);
    }

    // LKIE TRUE or FALSE
    @PostMapping("/{id}/like")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<University> updateLike(
            @PathVariable Long id,
            Authentication authentication,
            HttpServletRequest request
    ) {
        String errorType = null;
        String errorText = null;

        // Account
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<Account> accountData = accountService.findByUserId(userDetails.getUsername());

        // University
        University universityData = universityService.findByIdLike(id, accountData.get());

        if(!accountData.isPresent()) {
            errorType = "AuthenticationError";
            errorText = "올바른 접근이 아닙니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        // Like State Check
        if(universityData.getUniLike().contains(accountData.get())) {
            // Like False
            universityData.getUniLike().remove(accountData.get());
        } else {
            // Like True
            universityData.getUniLike().add(accountData.get());
        }

        University earlyUniversity = universityService.saveOrUpdate(universityData);

        return new ResponseEntity<University>(earlyUniversity, HttpStatus.OK);
    }

    // GET 공개된 유저정보
    @GetMapping("")
    public Page<UniversityPublic> getPublicAccountList(
            Pageable pageable
    ) {
        return universityService.findByUniversityList(pageable);
    }

    // DELETE 특정 TASK 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> deleteTask(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String errorType = null;
        String errorText = null;

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<Account> accountData =  accountService.findByUserId(userDetails.getUsername());

        if(!accountData.isPresent()) {
            universityService.deleteTask(id, accountData.get());
        } else {
            errorType = "AuthenticationError";
            errorText = "올바른 접근이 아닙니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        return new ResponseEntity<String>("처리가 완료되었습니다.", HttpStatus.OK);
    }
}
