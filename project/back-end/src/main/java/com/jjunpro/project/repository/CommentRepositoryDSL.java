package com.jjunpro.project.repository;

import com.jjunpro.project.projection.CommentPublic;

public interface CommentRepositoryDSL {

    CommentPublic findByPublicId(Long id);
}
