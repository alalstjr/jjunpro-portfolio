package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.Comment;
import com.backend.project.dto.CommentSaveDTO;
import com.backend.project.projection.CommentPublic;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    public CommentPublic save(CommentSaveDTO dto);
    public void deleteData(Long id, Account account);
    public void deleteUniComment(Long id, Account account);
    public Optional<Comment> findById(Long id);
    public List<CommentPublic> findByCommentList(Long uniId);
}
