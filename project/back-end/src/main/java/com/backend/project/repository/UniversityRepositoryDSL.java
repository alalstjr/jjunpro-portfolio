package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.projection.CommentPublic;
import com.backend.project.projection.UniversityPublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UniversityRepositoryDSL {

    Page<UniversityPublic> findByPublicAll(Pageable pageable, Account account);
    List<UniversityPublic> findByUniversityListWhereAccountId(Account account, String userId, Long offsetCount);
    List<UniversityPublic> findByLikeListWhereAccountId(Account account, String userId, Long offsetCount);

    List<CommentPublic> findByCommentList(Long id);

    UniversityPublic findByPublicId(Long id, Account account);

    Boolean findByIdLike(Long id, Account account);

    void deleteData(Long id, Account accountData);
}
