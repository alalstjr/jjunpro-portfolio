package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.dto.CommentSaveDTO;
import com.backend.project.projection.CommentPublic;
import com.backend.project.respone.WebProcessRespone;
import com.backend.project.service.CommentService;
import com.backend.project.util.AccountUtill;
import com.backend.project.util.IpUtil;
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
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController
{

    @Autowired
    private CommentService commentService;

    @Autowired
    private WebProcessRespone webProcessRespone;

    @Autowired
    private IpUtil ipUtil;

    @Autowired
    private AccountUtill accountUtill;

    /**
     * INSERT Comment DATA
     */
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CommentPublic> insertComment(
            @Valid @RequestBody CommentSaveDTO dto,
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

        // 유효성 검사 최종 반환
        if(errorMap.size() > 0)
        {
            return webProcessRespone.webErrorRespone(errorMap);
        }

        dto.setAccount(accountData.get());
        dto.setIp(ipUtil.getUserIp(request));

        CommentPublic newComment = commentService.save(dto);

        return new ResponseEntity<CommentPublic>(newComment, HttpStatus.CREATED);
    }

    /**
     * GET Comment List DATA UniId
     */
    @GetMapping("/{uniId}")
    public List<CommentPublic> getCommentListUniId(
            @PathVariable Long uniId,
            HttpServletRequest request
    ) throws IOException
    {
        // Account Info
        Account accountData = accountUtill.accountJWT(request);

        return commentService.findByCommentList(uniId, accountData);
    }

    /**
     * DELETE Comment DATA id
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> deleteCommentId(
            @PathVariable Long id,
            Authentication authentication
    )
    {
        String errorType = null;
        String errorText = null;

        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        if(accountData.isPresent())
        {

            if(!accountUtill.userDataCheck(id, accountData, "comment"))
            {
                errorType = "AuthenticationError";
                errorText = "잘못된 계정 접근입니다.";
                return webProcessRespone.webErrorRespone(errorType, errorText);
            }

            commentService.deleteData(id, accountData.get());
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
