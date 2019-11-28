package com.backend.project.controller;

import com.backend.project.domain.Comment;
import com.backend.project.dto.CommentSaveDTO;
import com.backend.project.service.CommentServiceImpl;
import com.backend.project.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin
public class CommentController {

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    private IpUtil ipUtil;

    // CREATE or UADATE 생성
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Comment> saveOrUpdate(
            @Valid @RequestBody CommentSaveDTO dto,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {

        dto.setIp(ipUtil.getUserIp(request));

        commentService.save(dto);

        return null;
    }
}
