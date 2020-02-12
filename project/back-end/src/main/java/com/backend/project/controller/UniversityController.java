package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.domain.File;
import com.backend.project.domain.University;
import com.backend.project.dto.SearchDTO;
import com.backend.project.dto.UniLikeDTO;
import com.backend.project.dto.UniversityDTO;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.service.CommentService;
import com.backend.project.service.FileStorageService;
import com.backend.project.service.UniversityService;
import com.backend.project.util.AccountUtill;
import com.backend.project.util.IpUtil;
import com.backend.project.util.SearchUtill;
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

    private final IpUtil             ipUtil;
    private final AccountUtill       accountUtill;
    private final SearchUtill        searchUtill;
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
                        fileSizeDB = byId
                                .get()
                                .getFiles()
                                .size();
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

            // 유저의 정보와 IP 주소를 저장합니다.
            dto.setAccount(accountData.get());
            dto.setUniIp(ipUtil.getUserIp(request));

            newUniversity = universityService.saveOrUpdate(dto);
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
                if (universityData
                        .get()
                        .getUniLike()
                        .contains(accountData.get())) {
                    // Like False
                    universityData
                            .get()
                            .getUniLike()
                            .remove(accountData.get());
                }
                else {
                    // Like True
                    universityData
                            .get()
                            .getUniLike()
                            .add(accountData.get());
                    uniLikeState = true;
                }

                // DTO 설정
                University earlyUniversity = universityService.saveOrUpdate(universityData.get());
                uniLikeDTO.setId(earlyUniversity.getId());
                uniLikeDTO.setUniLike(earlyUniversity
                        .getUniLike()
                        .size());
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
    @GetMapping("/pugjjigs/userId/{keyword}")
    public ResponseEntity<?> getUniListUserId(
            @PathVariable
                    String keyword,
            @RequestParam(value = "offsetCount")
                    int offsetCount,
            @RequestParam("ifCateA")
                    String ifCateA,
            @RequestParam("ifCateB")
                    String ifCateB, HttpServletRequest request
    ) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        SearchDTO searchDTO = searchUtill.setSearchDTO(
                keyword,
                null,
                offsetCount,
                ifCateA,
                ifCateB,
                accountData
        );

        List<UniversityPublic> result = universityService.findByUniversityListWhereAccountId(searchDTO);

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    /**
     * GET University List DATA nickname
     */
    @GetMapping("/pugjjigs/nickname/{keyword}")
    public ResponseEntity<?> getUniListNickname(
            @PathVariable
                    String keyword,
            @RequestParam("offsetCount")
                    int offsetCount,
            @RequestParam("ifCateA")
                    String ifCateA,
            @RequestParam("ifCateB")
                    String ifCateB, HttpServletRequest request
    ) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        SearchDTO searchDTO = searchUtill.setSearchDTO(
                keyword,
                null,
                offsetCount,
                ifCateA,
                ifCateB,
                accountData
        );

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
                    int offsetCount,
            @RequestParam("ifCateA")
                    String ifCateA,
            @RequestParam("ifCateB")
                    String ifCateB, HttpServletRequest request
    ) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        SearchDTO searchDTO = searchUtill.setSearchDTO(
                keyword,
                null,
                offsetCount,
                ifCateA,
                ifCateB,
                accountData
        );

        List<UniversityPublic> result = universityService.findByLikeListWhereAccountId(searchDTO);
        return new ResponseEntity<>(
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
                    int offsetCount,
            @RequestParam("ifCateA")
                    String ifCateA,
            @RequestParam("ifCateB")
                    String ifCateB, HttpServletRequest request
    ) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        SearchDTO searchDTO = searchUtill.setSearchDTO(
                keyword,
                classification,
                offsetCount,
                ifCateA,
                ifCateB,
                accountData
        );

        List<UniversityPublic> result = universityService.findByUniversityListWhereKeyword(searchDTO);

        return new ResponseEntity<>(
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
        String result = universityService
                .findByIdUniCount(uniName)
                .toString();

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put(
                "count",
                result
        );
        resultMap.put(
                "uniName",
                uniName
        );

        return new ResponseEntity<>(
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
            @Valid
                    UniversityDTO id, Authentication authentication, BindingResult bindingResult
    ) throws BindException {
        // 유효성 검사 후 최종 반환합니다.
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        if (accountData.isPresent()) {
            // University 등록된 File 를 삭제합니다.
            Optional<University> uniFiles;
            uniFiles = universityService.findById(id.getId());
            uniFiles.ifPresent(university -> fileStorageService.filesDelete(university.getFiles()));

            // University 등록된 Comment 를 삭제합니다.
            commentService.deleteUniComment(
                    id.getId(),
                    accountData.get()
            );

            // University 를 삭제합니다.
            universityService.deleteData(
                    id.getId(),
                    accountData.get()
            );
        }

        return new ResponseEntity<>(
                "삭제가 완료되었습니다.",
                HttpStatus.OK
        );
    }
}