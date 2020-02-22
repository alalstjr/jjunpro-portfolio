package com.jjunpro.project.controller;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.dto.UniversityDTO;
import com.jjunpro.project.projection.UniversityPublic;
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
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/university")
@RequiredArgsConstructor
public class UniversityController {

    private final IpUtil            ipUtil;
    private final AccountUtil       accountUtill;
    private final UniversityService universityService;

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
        // 유효성 검사 후 최종 반환합니다.
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        //UniversityPublic newUniversity = null;

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        accountData.ifPresent(account -> defaultSetting(
                dto,
                request,
                account
        ));

        return new ResponseEntity<>(
                universityService.createUniversity(dto),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<University> updateUniversity(
            @Valid
            @ModelAttribute
                    UniversityDTO dto,
            BindingResult bindingResult,
            Authentication authentication,
            HttpServletRequest request
    ) throws BindException {
        // 유효성 검사 후 최종 반환합니다.
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        if (accountData.isPresent()) {
            defaultSetting(
                    dto,
                    request,
                    accountData.get()
            );

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

            universityService.updateUniversity(dto);
        }

        return null;
    }

    // 유저의 정보와 IP 주소를 저장합니다.
    private void defaultSetting(
            UniversityDTO dto,
            HttpServletRequest request,
            Account accountData
    ) {
        dto.setAccount(accountData);
        dto.setUniIp(ipUtil.getUserIp(request));
    }
}
