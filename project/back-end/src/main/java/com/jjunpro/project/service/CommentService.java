package com.jjunpro.project.service;

import com.jjunpro.project.dto.CreateCommentDTO;
import com.jjunpro.project.projection.CommentPublic;

public interface CommentService {

    CommentPublic save(CreateCommentDTO dto);
}
