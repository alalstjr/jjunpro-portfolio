package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.domain.University;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.respone.WebProcessRespone;
import com.backend.project.service.AccountServiceImpl;
import com.backend.project.service.UniversityServiceImpl;
import com.backend.project.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
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

        Optional<Account> account = accountService.findByUserId(userDetails.getUsername());

        if(!account.isPresent()) {
            errorType = "AuthenticationError";
            errorText = "올바른 접근이 아닙니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        if(ipUtil.getUserIp(request).equals("0.0.0.0")) {
            errorType = "AuthenticationError";
            errorText = "올바른 접근이 아닙니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        dto.setAccount(account.get());
        dto.setUniIp(ipUtil.getUserIp(request));

        University newUniversity = universityService.saveOrUpdate(dto);
        return new ResponseEntity<University>(newUniversity, HttpStatus.CREATED);
    }
}
