package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.projection.CommentPublic;

import java.util.List;

public interface CommentRepositoryDSL {
    void deleteData(Long id, Account accountData);
    void deleteUniComment(Long id, Account accountData);
    CommentPublic findByPublicId(Long id);
    List<CommentPublic> findByCommentList(Long id);
}
