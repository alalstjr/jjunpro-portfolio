package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.domain.University;
import com.backend.project.dto.StoreDTO;
import com.backend.project.dto.UniLikeDTO;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.respone.WebProcessRespone;
import com.backend.project.service.AccountServiceImpl;
import com.backend.project.service.UniversityServiceImpl;
import com.backend.project.util.AccountUtill;
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
import java.io.IOException;
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

    @Autowired
    private AccountUtill accountUtill;

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

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

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

        StoreDTO storeDTO = new StoreDTO();

        dto.setAccount(accountData.get());
        dto.setUniIp(ipUtil.getUserIp(request));

        University newUniversity = universityService.saveOrUpdate(dto, storeDTO);


        return new ResponseEntity<University>(newUniversity, HttpStatus.CREATED);
    }

    // LKIE TRUE or FALSE
    @PostMapping("/{id}/like")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UniLikeDTO> updateLike(
            @PathVariable Long id,
            Authentication authentication,
            HttpServletRequest request
    ) {
        String errorType = null;
        String errorText = null;
        Boolean uniLikeState = false;

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        // University
        University universityData = universityService.findById(id).get();

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
            uniLikeState = true;
        }

        University earlyUniversity = universityService.saveOrUpdate(universityData);

        UniLikeDTO uniLikeDTO = new UniLikeDTO();
        uniLikeDTO.setId(earlyUniversity.getId());
        uniLikeDTO.setUniLike(earlyUniversity.getUniLike().size());
        uniLikeDTO.setUniLikeState(uniLikeState);

        return new ResponseEntity<UniLikeDTO>(uniLikeDTO, HttpStatus.OK);
    }

    // GET 공개된 유저정보
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public Page<UniversityPublic> getPugjjig(
            Pageable pageable,
            HttpServletRequest request
    ) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        return universityService.findByUniversityList(pageable, accountData);
    }

    // GET {userId} 의 푹찍 목록
    @GetMapping("/pugjjig/{userId}")
    public Page<UniversityPublic> getUserPugjjig(
            Pageable pageable,
            @PathVariable String userId,
            HttpServletRequest request
    ) throws IOException {
        if(userId == null) {
            throw new IOException("잘못된 접근입니다.");
        }
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        return universityService.findByUniversityListWhereAccountId(pageable, accountData, userId);
    }

    // GET 푹찍 {ID} 리뷰
    @GetMapping("/{id}")
    public ResponseEntity<UniversityPublic> getPublicAccountList(
            @PathVariable Long id,
            HttpServletRequest request
    ) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        UniversityPublic result =  universityService.findByPublicId(id, accountData);

        return new ResponseEntity<UniversityPublic>(result, HttpStatus.OK);
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