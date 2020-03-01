package com.jjunpro.project.controller;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.dto.CommentDTO;
import com.jjunpro.project.dto.CreateCommentDTO;
import com.jjunpro.project.projection.CommentPublic;
import com.jjunpro.project.service.AlarmService;
import com.jjunpro.project.service.CommentService;
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
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final IpUtil         ipUtil;
    private final AccountUtil    accountUtil;
    private final CommentService commentService;
    private final AlarmService   alarmService;

    /**
     * INSERT Comment DATA
     */
    @PostMapping("")
    public ResponseEntity<CommentPublic> insertComment(
            @Valid @RequestBody CreateCommentDTO dto,
            BindingResult bindingResult,
            Authentication authentication,
            HttpServletRequest request
    ) throws BindException {
        /* 유효성 검사 후 최종 반환합니다. */
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        /* Account Info */
        Optional<Account> accountData = accountUtil.accountInfo(authentication);

        if (accountData.isPresent()) {
            dto.defaultSetting(
                    ipUtil.getUserIp(request),
                    accountUtil,
                    authentication
            );
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
            @PathVariable Long uniId,
            HttpServletRequest request
    ) throws IOException {
        /* Account Info */
        Account accountData = accountUtil.accountJWT(request);

        /* 알람 DATA 를 삭제합니다. */
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCommentId(
            @Valid CommentDTO id,
            BindingResult bindingResult,
            Authentication authentication
    ) throws BindException {
        /* 유효성 검사 후 최종 반환합니다. */
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        /* Account Info */
        Optional<Account> accountData = accountUtil.accountInfo(authentication);

        commentService.deleteData(id.getId(), accountData.get());

        return new ResponseEntity<>(
                "success",
                HttpStatus.OK
        );
    }
}
