package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.domain.File;
import com.backend.project.domain.University;
import com.backend.project.dto.SearchDTO;
import com.backend.project.dto.StoreDTO;
import com.backend.project.dto.UniLikeDTO;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.respone.WebProcessRespone;
import com.backend.project.service.AccountService;
import com.backend.project.service.FileStorageService;
import com.backend.project.service.UniversityService;
import com.backend.project.util.AccountUtill;
import com.backend.project.util.IpUtil;
import com.backend.project.util.ValidityCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/university")
@CrossOrigin
public class UniversityController
{
    @Autowired
    private UniversityService universityService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private WebProcessRespone webProcessRespone;

    @Autowired
    private IpUtil ipUtil;

    @Autowired
    private AccountUtill accountUtill;

    @Autowired
    private ValidityCheckUtil validityCheck;

    /**
     * INSERT University DATA
     */
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UniversityPublic> insertUniversity(
            @Valid @ModelAttribute UniversitySaveDTO dto,
            BindingResult bindingResult,
            Authentication authentication,
            HttpServletRequest request
    )
    {
        Map<String, String> errorMap = new HashMap<String, String>();
        String errorType = null;
        String errorText = null;

        // Field Check
        if(bindingResult.hasErrors())
        {
            return webProcessRespone.webErrorRespone(bindingResult);
        }

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        // UPDATE 경우 작성한 유저 유효성 검사
        if(dto.getId() != null)
        {
            // 해당 데이터의 작성자가 맞는지 검사합니다.
            if(!accountUtill.userDataCheck(dto.getId(), accountData, "university"))
            {
                errorType = "AuthenticationError";
                errorText = "잘못된 계정 접근입니다.";
                return webProcessRespone.webErrorRespone(errorType, errorText);
            }
        }

        if(!accountData.isPresent())
        {
            errorType = "AuthenticationError";
            errorText = "잘못된 계정 접근입니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        if(ipUtil.getUserIp(request).equals("0.0.0.0"))
        {
            errorType = "AuthenticationError";
            errorText = "잘못된 IP 정보 접근입니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        if(dto.getUniStar() < 0 || dto.getUniStar() >= 6)
        {
            errorType = "AuthenticationError";
            errorText = "잘못된 점수입니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        // 유효성 검사 최종 반환
        if(errorMap.size() > 0)
        {
            return webProcessRespone.webErrorRespone(errorMap);
        }

        // 첨부파일이 존재하는 경우 파일 업로드 메소드
        if(dto.getFiles() != null)
        {
            List<File> fileData = fileStorageService.uploadMultipleFiles(dto.getFiles(), "university");
            dto.setFileData(fileData);
        }

        StoreDTO storeDTO = new StoreDTO();

        dto.setAccount(accountData.get());
        dto.setUniIp(ipUtil.getUserIp(request));

        UniversityPublic newUniversity = universityService.saveOrUpdate(dto, storeDTO, accountData.get());

        return new ResponseEntity<UniversityPublic>(newUniversity, HttpStatus.CREATED);
    }

    /**
     * UPDATE UniLike DATA uniId
     */
    @PostMapping("/like/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UniLikeDTO> UpdateUniLikeUniId(
            @PathVariable Long id,
            Authentication authentication,
            HttpServletRequest request
    )
    {
        Map<String, String> errorMap = new HashMap<String, String>();
        String errorType = null;
        String errorText = null;
        Boolean uniLikeState = false;

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        // University
        University universityData = universityService.findById(id).get();

        if(!accountData.isPresent())
        {
            errorType = "AuthenticationError";
            errorText = "올바른 접근이 아닙니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        // 유효성 검사 최종 반환
        if(errorMap.size() > 0)
        {
            return webProcessRespone.webErrorRespone(errorMap);
        }

        // Like State Check
        if(universityData.getUniLike().contains(accountData.get()))
        {
            // Like False
            universityData.getUniLike().remove(accountData.get());
        }
        else
        {
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

    /**
     * GET University List DATA userId
     */
    @GetMapping("/pugjjigs/{userId}")
    public ResponseEntity<?> getUniListUserId(
            @PathVariable String userId,
            @RequestParam("offsetCount") Long offsetCount,
            HttpServletRequest request
    ) throws IOException
    {
        // Search DTO 생성
        SearchDTO searchDTO = new SearchDTO();

        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        // offsetCount 없는경우 기본값 0 설정
        offsetCount = (offsetCount == null) ? 0L : offsetCount;

        if(userId != null || offsetCount != null)
        {
            // Param 값을 DTO 에 담아줍니다.
            // (관리하기 쉽게 DTO 한곳에 모아줍니다.)
            searchDTO.setKeyword(userId);
            searchDTO.setOffsetCount(offsetCount);
            searchDTO.setAccount(accountData);
        }
        else
        {
            throw new IOException("검색에 필요한 정보를 전부 받지 못했습니다.");
        }

        List<UniversityPublic> result = universityService.findByUniversityListWhereAccountId(searchDTO);
        return new ResponseEntity<List<UniversityPublic>>(result, HttpStatus.OK);
    }

    /**
     * GET University DATA uniId
     */
    @GetMapping("/{id}")
    public ResponseEntity<UniversityPublic> getUniversityUniId(
            @PathVariable Long id,
            HttpServletRequest request
    ) throws IOException
    {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        UniversityPublic result =  universityService.findByPublicId(id, accountData);

        return new ResponseEntity<UniversityPublic>(result, HttpStatus.OK);
    }

    /**
     * GET University List DATA uniLike
     */
    @GetMapping("/pugjjigLikes/{keyword}")
    public ResponseEntity<?> getUniListUniLike(
            @PathVariable String keyword,
            @RequestParam("offsetCount") Long offsetCount,
            HttpServletRequest request
    ) throws IOException
    {
        // Search DTO 생성
        SearchDTO searchDTO = new SearchDTO();

        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        // offsetCount 없는경우 기본값 0 설정
        offsetCount = (offsetCount == null) ? 0L : offsetCount;

        if(keyword != null || offsetCount != null)
        {
            // Param 값을 DTO 에 담아줍니다.
            // (관리하기 쉽게 DTO 한곳에 모아줍니다.)
            searchDTO.setKeyword(keyword);
            searchDTO.setOffsetCount(offsetCount);
            searchDTO.setAccount(accountData);
        }
        else
        {
            throw new IOException("검색에 필요한 정보를 전부 받지 못했습니다.");
        }

        List<UniversityPublic> result = universityService.findByLikeListWhereAccountId(searchDTO);
        return new ResponseEntity<List<UniversityPublic>>(result, HttpStatus.OK);
    }

    /**
     * GET University List DATA Search
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> getUniListSearch(
            @PathVariable String keyword,
            @RequestParam("classification") String classification,
            @RequestParam("offsetCount") Long offsetCount,
            HttpServletRequest request
    ) throws IOException
    {
        // Search DTO 생성
        SearchDTO searchDTO = new SearchDTO();

        // offsetCount 없는경우 기본값 0 설정
        offsetCount = (offsetCount == null) ? 0L : offsetCount;

        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        if(keyword != null || classification != null || offsetCount != null)
        {
            // Param 값을 DTO 에 담아줍니다.
            // (관리하기 쉽게 DTO 한곳에 모아줍니다.)
            searchDTO.setKeyword(keyword);
            searchDTO.setClassification(classification);
            searchDTO.setOffsetCount(offsetCount);
            searchDTO.setAccount(accountData);
        }
        else
        {
            throw new IOException("검색에 필요한 정보를 전부 받지 못했습니다.");
        }

        List<UniversityPublic> result = universityService.findByUniversityListWhereKeyword(searchDTO);

        return new ResponseEntity<List<UniversityPublic>>(result, HttpStatus.OK);
    }

    /**
     * DELETE University DATA uniId
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> deleteUniversityuniId(
            @PathVariable Long id,
            Authentication authentication
    )
    {
        String errorType = null;
        String errorText = null;

        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        if(accountData.isPresent())
        {

            if(!accountUtill.userDataCheck(id, accountData, "university"))
            {
                errorType = "AuthenticationError";
                errorText = "잘못된 계정 접근입니다.";
                return webProcessRespone.webErrorRespone(errorType, errorText);
            }

            universityService.deleteData(id, accountData.get());
        }
        else
        {
            errorType = "AuthenticationError";
            errorText = "올바른 접근이 아닙니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        return new ResponseEntity<String>("처리가 완료되었습니다.", HttpStatus.OK);
    }
}