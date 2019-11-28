package com.backend.project.service;

import com.backend.project.domain.Comment;
import com.backend.project.dto.CommentSaveDTO;

public interface CommentService {

    public Comment save(CommentSaveDTO dto);
}
