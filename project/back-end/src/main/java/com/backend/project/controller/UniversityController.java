package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.domain.File;
import com.backend.project.domain.University;
import com.backend.project.dto.SearchDTO;
import com.backend.project.dto.UniLikeDTO;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.respone.WebProcessRespone;
import com.backend.project.service.CommentService;
import com.backend.project.service.FileStorageService;
import com.backend.project.service.UniversityService;
import com.backend.project.util.AccountUtill;
import com.backend.project.util.IpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/university")
@RequiredArgsConstructor
public class UniversityController {
    private final WebProcessRespone  webProcessRespone;
    private final IpUtil             ipUtil;
    private final AccountUtill       accountUtill;
    private final UniversityService  universityService;
    private final FileStorageService fileStorageService;
    private final CommentService     commentService;

    /**
     * INSERT University DATA
     */
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UniversityPublic> insertUniversity(
            @Valid
            @ModelAttribute
                    UniversitySaveDTO dto, BindingResult bindingResult, Authentication authentication, HttpServletRequest request
    ) throws BindException {
        // 유효성 검사 후 최종 반환합니다.
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        UniversityPublic newUniversity = null;

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        if (accountData.isPresent()) {
            // 제거되는 file 존재하는 경우 삭제
            fileStorageService.deleteFileFilter(dto.getRemoveFiles());

            // 첨부파일이 존재하는 경우 파일 업로드 메소드
            if (dto.getFiles() != null) {
                int fileSizeDB = 0;

                // UPDATE
                if (dto.getId() != null) {
                    Optional<University> byId = universityService.findById(dto.getId());

                    // 업로드된 Domin의 File 갯수를 조회
                    if (byId.isPresent()) {
                        fileSizeDB = byId.get().getFiles().size();
                    }
                }

                List<File> fileData = fileStorageService.uploadMultipleFiles(
                        fileSizeDB,
                        dto.getFiles(),
                        "university"
                );

                // UPDATE 한 File의 정보를 DTO 에 추가
                dto.setFileData(fileData);
            }

            dto.setAccount(accountData.get());
            dto.setUniIp(ipUtil.getUserIp(request));

            newUniversity = universityService.saveOrUpdate(
                    dto,
                    accountData.get()
            );
        }

        return new ResponseEntity<>(
                newUniversity,
                HttpStatus.CREATED
        );
    }

    /**
     * UPDATE UniLike DATA uniId
     */
    @PostMapping("/like/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<UniLikeDTO> UpdateUniLikeUniId(
            @PathVariable
                    Long id, Authentication authentication
    ) {
        boolean    uniLikeState = false;
        UniLikeDTO uniLikeDTO   = new UniLikeDTO();

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        if (accountData.isPresent()) {
            // University
            Optional<University> universityData = universityService.findById(id);

            if (universityData.isPresent()) {
                // Like State Check
                if (universityData.get().getUniLike().contains(accountData.get())) {
                    // Like False
                    universityData.get().getUniLike().remove(accountData.get());
                }
                else {
                    // Like True
                    universityData.get().getUniLike().add(accountData.get());
                    uniLikeState = true;
                }

                // DTO 설정
                University earlyUniversity = universityService.saveOrUpdate(universityData.get());
                uniLikeDTO.setId(earlyUniversity.getId());
                uniLikeDTO.setUniLike(earlyUniversity.getUniLike().size());
                uniLikeDTO.setUniLikeState(uniLikeState);
            }
        }

        return new ResponseEntity<>(
                uniLikeDTO,
                HttpStatus.OK
        );
    }

    /**
     * GET University List DATA userId
     */
    @GetMapping("/pugjjigs/userId/{userId}")
    public ResponseEntity<?> getUniListUserId(
            @PathVariable
                    String userId,
            @RequestParam("offsetCount")
                    Long offsetCount,
            @RequestParam("ifCateA")
                    String ifCateA,
            @RequestParam("ifCateB")
                    String ifCateB, HttpServletRequest request
    ) throws IOException {
        // Search DTO 생성
        SearchDTO searchDTO = new SearchDTO();

        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        // offsetCount 없는경우 기본값 0 설정
        offsetCount = ( offsetCount == null ) ? 0L : offsetCount;

        // Param 값을 DTO 에 담아줍니다.
        // (관리하기 쉽게 DTO 한곳에 모아줍니다.)
        searchDTO.setKeyword(userId);
        searchDTO.setOffsetCount(offsetCount);
        searchDTO.setAccount(accountData);
        searchDTO.setIfCateA(ifCateA);
        searchDTO.setIfCateB(ifCateB);

        List<UniversityPublic> result = universityService.findByUniversityListWhereAccountId(searchDTO);

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    /**
     * GET University List DATA nickname
     */
    @GetMapping("/pugjjigs/nickname/{nickname}")
    public ResponseEntity<?> getUniListNickname(
            @PathVariable
                    String nickname,
            @RequestParam("offsetCount")
                    Long offsetCount,
            @RequestParam("ifCateA")
                    String ifCateA,
            @RequestParam("ifCateB")
                    String ifCateB, HttpServletRequest request
    ) throws IOException {
        // Search DTO 생성
        SearchDTO searchDTO = new SearchDTO();

        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        // offsetCount 없는경우 기본값 0 설정
        offsetCount = ( offsetCount == null ) ? 0L : offsetCount;

        // Param 값을 DTO 에 담아줍니다.
        // (관리하기 쉽게 DTO 한곳에 모아줍니다.)
        searchDTO.setKeyword(nickname);
        searchDTO.setOffsetCount(offsetCount);
        searchDTO.setAccount(accountData);
        searchDTO.setIfCateA(ifCateA);
        searchDTO.setIfCateB(ifCateB);

        List<UniversityPublic> result = universityService.findByUniversityListWhereAccountNickname(searchDTO);

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    /**
     * GET University List DATA CreatedDate DESC
     */
    @GetMapping("")
    public ResponseEntity<List<UniversityPublic>> getUniversityCreatedDate(HttpServletRequest request) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        List<UniversityPublic> result = universityService.findByOrderByCreatedDateDesc(accountData);

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    /**
     * GET University List DATA Most Like DESC`
     */
    @GetMapping("/best")
    public ResponseEntity<List<UniversityPublic>> getUniversityMostLike(HttpServletRequest request) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        List<UniversityPublic> result = universityService.findByOrderByMostLike(accountData);

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    /**
     * GET University DATA uniId
     */
    @GetMapping("/{id}")
    public ResponseEntity<UniversityPublic> getUniversityUniId(
            @PathVariable
                    Long id, HttpServletRequest request
    ) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        UniversityPublic result = universityService.findByPublicId(
                id,
                accountData
        );

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    /**
     * GET University List DATA uniLike
     */
    @GetMapping("/pugjjigLikes/{keyword}")
    public ResponseEntity<?> getUniListUniLike(
            @PathVariable
                    String keyword,
            @RequestParam("offsetCount")
                    Long offsetCount,
            @RequestParam("ifCateA")
                    String ifCateA,
            @RequestParam("ifCateB")
                    String ifCateB, HttpServletRequest request
    ) throws IOException {
        // Search DTO 생성
        SearchDTO searchDTO = new SearchDTO();

        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        // offsetCount 없는경우 기본값 0 설정
        offsetCount = ( offsetCount == null ) ? 0L : offsetCount;

        if (keyword != null || offsetCount != null) {
            // Param 값을 DTO 에 담아줍니다.
            // (관리하기 쉽게 DTO 한곳에 모아줍니다.)
            searchDTO.setKeyword(keyword);
            searchDTO.setOffsetCount(offsetCount);
            searchDTO.setAccount(accountData);
            searchDTO.setIfCateA(ifCateA);
            searchDTO.setIfCateB(ifCateB);
        }
        else {
            throw new IOException("검색에 필요한 정보를 전부 받지 못했습니다.");
        }

        List<UniversityPublic> result = universityService.findByLikeListWhereAccountId(searchDTO);
        return new ResponseEntity<List<UniversityPublic>>(
                result,
                HttpStatus.OK
        );
    }

    /**
     * GET University List DATA Search
     */
    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> getUniListSearch(
            @PathVariable
                    String keyword,
            @RequestParam("classification")
                    String classification,
            @RequestParam("offsetCount")
                    Long offsetCount,
            @RequestParam("ifCateA")
                    String ifCateA,
            @RequestParam("ifCateB")
                    String ifCateB, HttpServletRequest request
    ) throws IOException {
        // Search DTO 생성
        SearchDTO searchDTO = new SearchDTO();

        // offsetCount 없는경우 기본값 0 설정
        offsetCount = ( offsetCount == null ) ? 0L : offsetCount;

        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        if (keyword != null || classification != null || offsetCount != null) {
            // Param 값을 DTO 에 담아줍니다.
            // (관리하기 쉽게 DTO 한곳에 모아줍니다.)
            searchDTO.setKeyword(keyword);
            searchDTO.setClassification(classification);
            searchDTO.setOffsetCount(offsetCount);
            searchDTO.setAccount(accountData);
            searchDTO.setIfCateA(ifCateA);
            searchDTO.setIfCateB(ifCateB);
        }
        else {
            throw new IOException("검색에 필요한 정보를 전부 받지 못했습니다.");
        }

        List<UniversityPublic> result = universityService.findByUniversityListWhereKeyword(searchDTO);

        return new ResponseEntity<List<UniversityPublic>>(
                result,
                HttpStatus.OK
        );
    }

    /**
     * GET University Count DATA UniId
     */
    @GetMapping("/count/{uniName}")
    public ResponseEntity<Map<String, String>> getUniCountUniId(
            @PathVariable
                    String uniName
    ) {
        String result = universityService.findByIdUniCount(uniName).toString();

        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap.put(
                "count",
                result
        );
        resultMap.put(
                "uniName",
                uniName
        );

        return new ResponseEntity<Map<String, String>>(
                resultMap,
                HttpStatus.OK
        );
    }

    /**
     * DELETE University DATA uniId
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> deleteUniversityuniId(
            @PathVariable
                    Long id, Authentication authentication
    ) {
        String errorType = null;
        String errorText = null;

        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        if (accountData.isPresent()) {

            if (!accountUtill.userDataCheck(
                    id,
                    accountData,
                    "university"
            )) {
                errorType = "AuthenticationError";
                errorText = "잘못된 계정 접근입니다.";
                return webProcessRespone.webErrorRespone(
                        errorType,
                        errorText
                );
            }

            // University 등록된 File 를 삭제합니다.
            Optional<University> uniFiles;
            uniFiles = universityService.findById(id);
            uniFiles.ifPresent(university -> fileStorageService.filesDelete(university.getFiles()));

            // University 등록된 Comment 를 삭제합니다.
            commentService.deleteUniComment(
                    id,
                    accountData.get()
            );

            // University 를 삭제합니다.
            universityService.deleteData(
                    id,
                    accountData.get()
            );
        }
        else {
            errorType = "AuthenticationError";
            errorText = "올바른 접근이 아닙니다.";
            return webProcessRespone.webErrorRespone(
                    errorType,
                    errorText
            );
        }

        return new ResponseEntity<String>(
                "처리가 완료되었습니다.",
                HttpStatus.OK
        );
    }
}