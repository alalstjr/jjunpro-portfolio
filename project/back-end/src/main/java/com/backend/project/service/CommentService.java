package com.backend.project.service;

import com.backend.project.dto.CommentSaveDTO;
import com.backend.project.projection.CommentPublic;

public interface CommentService {

    public CommentPublic save(CommentSaveDTO dto);
}
