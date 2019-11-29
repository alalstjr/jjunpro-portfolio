package com.backend.project.repository;

import com.backend.project.projection.CommentPublic;

import java.util.List;

public interface CommentRepositoryDSL {
    CommentPublic findByPublicId(Long id);

    List<CommentPublic> findByCommentList(Long id);
}
