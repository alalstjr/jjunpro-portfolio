package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.dto.CommentDTO;
import com.backend.project.dto.CommentSaveDTO;
import com.backend.project.projection.CommentPublic;
import com.backend.project.service.AlarmService;
import com.backend.project.service.CommentService;
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
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final IpUtil         ipUtil;
    private final AccountUtill   accountUtill;
    private final CommentService commentService;
    private final AlarmService   alarmService;

    /**
     * INSERT Comment DATA
     */
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> insertComment(
            @Valid
            @RequestBody
                    CommentSaveDTO dto,
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
            // 유저의 정보와 IP 주소를 저장합니다.
            dto.setAccount(accountData.get());
            dto.setIp(ipUtil.getUserIp(request));
        }

        return new ResponseEntity<>(
                commentService.save(dto),
                HttpStatus.CREATED
        );
    }

    /**
     * GET Comment List DATA UniId
     */
    @GetMapping("/{uniId}")
    public List<CommentPublic> getCommentListUniId(
            @PathVariable
                    Long uniId,
            HttpServletRequest request
    ) throws IOException {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        // 알람 DATA 를 삭제합니다.
        if (accountData != null) {
            alarmService.deleteDataId(
                    uniId,
                    accountData
            );
        }

        return commentService.findByCommentList(
                uniId,
                accountData
        );
    }

    /**
     * DELETE Comment DATA id
     */
    @DeleteMapping("/{comment}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteCommentId(
            @Valid CommentDTO comment,
            BindingResult bindingResult
    ) throws BindException {
        // 유효성 검사 후 최종 반환합니다.
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        return new ResponseEntity<>(
                "처리가 완료되었습니다.",
                HttpStatus.OK
        );
    }
}
