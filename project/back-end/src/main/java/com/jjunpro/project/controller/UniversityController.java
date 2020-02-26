package com.jjunpro.project.controller;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.File;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.dto.SearchDTO;
import com.jjunpro.project.dto.UniversityDTO;
import com.jjunpro.project.dto.UniversityValidDTO;
import com.jjunpro.project.dto.UpdateUniLikeDTO;
import com.jjunpro.project.exception.SimpleException;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.service.CommentService;
import com.jjunpro.project.service.FileStorageService;
import com.jjunpro.project.service.UniversityService;
import com.jjunpro.project.util.AccountUtil;
import com.jjunpro.project.util.IpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {

    private final IpUtil             ipUtil;
    private final AccountUtil        accountUtil;
    private final UniversityService  universityService;
    private final FileStorageService fileStorageService;
    private final CommentService     commentService;

    /**
     * INSERT University DATA
     */
    @PostMapping("")
    public ResponseEntity<UniversityPublic> createUniversity(
            @Valid
            @ModelAttribute
                    UniversityDTO dto,
            BindingResult bindingResult,
            Authentication authentication,
            HttpServletRequest request
    ) throws BindException {
        /* 유효성 검사 후 최종 반환합니다. */
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        dto.defaultSetting(
                ipUtil.getUserIp(request),
                accountUtil,
                authentication
        );

        this.fileUpload(dto);

        return new ResponseEntity<>(
                universityService.createUniversity(dto),
                HttpStatus.CREATED
        );
    }

    /**
     * UPDATE University DATA
     */
    @PostMapping("/{updateId}")
    public ResponseEntity<UniversityPublic> updateUniversity(
            @Valid
            @ModelAttribute
                    UniversityDTO dto,
            BindingResult bindingResult,
            Authentication authentication,
            HttpServletRequest request
    ) throws BindException {
        /* 유효성 검사 후 최종 반환합니다. */
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        dto.defaultSetting(
                ipUtil.getUserIp(request),
                accountUtil,
                authentication
        );

        /* 제거되는 file 존재하는 경우 삭제 */
        if (dto.getRemoveFiles() != null && dto.getRemoveFiles().length > 0) {
            fileStorageService.deleteFileFilter(dto.getRemoveFiles());
        }

        this.fileUpload(dto);

        return new ResponseEntity<>(
                universityService.updateUniversity(dto),
                HttpStatus.CREATED
        );
    }

    /**
     * UPDATE UniLike DATA uniId
     */
    @PostMapping("/like/{id}")
    public ResponseEntity<UpdateUniLikeDTO> UpdateUniLikeUniId(
            @PathVariable
                    Long id,
            Authentication authentication
    ) {
        /* Account Info */
        Optional<Account> accountData = accountUtil.accountInfo(authentication);

        if (accountData.isPresent()) {
            return new ResponseEntity<>(
                    universityService.updateUniversityLike(
                            id,
                            accountData.get()
                    ),
                    HttpStatus.OK
            );
        }

        throw new SimpleException("회원의 정보가 존재하지 않습니다.");
    }

    /**
     * GET University List DATA Search
     */
    @GetMapping("/search")
    public ResponseEntity<?> getUniList(
            @RequestParam("category")
                    String category,
            @RequestParam("keyword")
                    String keyword,
            @RequestParam("offsetCount")
                    int offsetCount,
            @RequestParam("ifCateA")
                    String ifCateA,
            @RequestParam("ifCateB")
                    String ifCateB,
            HttpServletRequest request
    ) throws IOException {
        /* Account Info */
        Account accountData = accountUtil.accountJWT(request);

        SearchDTO searchDTO = SearchDTO
                .builder()
                .category(category)
                .keyword(keyword)
                .offsetCount(offsetCount)
                .ifCateA(ifCateA)
                .ifCateB(ifCateB)
                .accountData(accountData)
                .build();

        return new ResponseEntity<>(
                universityService.findByUniversityListWhereSearchDto(searchDTO),
                HttpStatus.OK
        );
    }

    /**
     * GET University List DATA CreatedDate DESC
     */
    @GetMapping("")
    public ResponseEntity<List<UniversityPublic>> getUniversityCreatedDate(HttpServletRequest request) throws IOException {
        /* Account Info */
        Account accountData = accountUtil.accountJWT(request);

        List<UniversityPublic> result = universityService.findByOrderByCreatedDateDesc(accountData);

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
                    Long id,
            HttpServletRequest request
    ) throws IOException {
        /* Account Info */
        Account accountData = accountUtil.accountJWT(request);

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
    public ResponseEntity<String> deleteUniversityuniId(
            @Valid UniversityValidDTO id,
            Authentication authentication,
            BindingResult bindingResult
    ) throws BindException {
        /* 유효성 검사 후 최종 반환합니다. */
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Optional<Account> accountData = accountUtil.accountInfo(authentication);

        if (accountData.isPresent()) {
            /* University 등록된 File 를 삭제합니다. */
            Optional<University> uniFiles;
            uniFiles = universityService.findById(id.getId());
            uniFiles.ifPresent(university -> fileStorageService.filesDelete(university.getFiles()));

            /* University 등록된 Comment 를 삭제합니다. */
            commentService.deleteData(id.getId(), accountData.get());

            /* University 를 삭제합니다. */
            universityService.deleteData(
                    id.getId(),
                    accountData.get()
            );
        }

        return new ResponseEntity<>(
                "success",
                HttpStatus.OK
        );
    }

    /* 첨부파일이 존재하는 경우 파일 업로드 메소드 */
    private void fileUpload(
            UniversityDTO dto
    ) {
        /*
         * fileSizeDB 변수는 최대 업로드 갯수를 파악하는데 사용됩니다.
         * 최종적으로 fileSizeDB + dto.getFiles() 합한 값이 MAX Upload Size 값을 비교합니다.
         * */
        if (dto.getFiles() != null) {
            int fileSizeDB = 0;

            /* UPDATE */
            if (dto.getId() != null) {
                Optional<University> byId = universityService.findById(dto.getId());

                /* { DB DATA } 업로드된 File 갯수를 조회 */
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

            /* CREATE or UPDATE 한 File의 정보를 DTO 에 추가 */
            dto.setFileData(fileData);
        }
    }
}
