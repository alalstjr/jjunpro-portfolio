package com.jjunpro.project.repository;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.projection.CommentPublic;

import java.util.List;

public interface CommentRepositoryDSL {

    CommentPublic findByPublicId(Long id);

    List<CommentPublic> findByCommentList(Long id);

    void deleteData(Long id, Account accountData);

    void deleteUniComment(Long id, Account accountData);
}
