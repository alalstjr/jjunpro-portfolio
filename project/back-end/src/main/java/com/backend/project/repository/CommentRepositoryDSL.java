package com.backend.project.repository;

import com.backend.project.projection.CommentPublic;

public interface CommentRepositoryDSL {
    CommentPublic findByPublicId(Long id);
}
