package com.jjunpro.project.service;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Comment;
import com.jjunpro.project.dto.CreateCommentDTO;
import com.jjunpro.project.projection.CommentPublic;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    CommentPublic save(CreateCommentDTO dto);

    List<CommentPublic> findByCommentList(
            Long uniId,
            Account account
    );

    void deleteData(
            Long id,
            Account account
    );

    Optional<Comment> findById(Long id);
}
