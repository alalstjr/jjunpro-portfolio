package com.backend.project.service;

import com.backend.project.dto.CommentSaveDTO;
import com.backend.project.projection.CommentPublic;

import java.util.List;

public interface CommentService {

    public CommentPublic save(CommentSaveDTO dto);

    public List<CommentPublic> findByCommentList(Long id);
}
