package com.backend.project.repository;

import com.backend.project.domain.*;
import com.backend.project.projection.CommentPublic;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryDSL
{
    private final JPAQueryFactory queryFactory;
    private QComment qComment       = QComment.comment;
    private QUniversity qUniversity = QUniversity.university;
    private QAccount qAccount       = QAccount.account;
    private QFile qFile             = QFile.file;

    @Override
    @Transactional
    public void deleteData(Long id, Account accountData) {

        queryFactory
                .delete(qComment)
                .where(
                        qComment.id.eq(id)
                                .and(qComment.account.eq(accountData))
                )
                .execute();
    }

    @Override
    @Transactional
    public void deleteUniComment(Long id, Account accountData) {

        // University 저장된 Comment 삭제
        queryFactory
                .delete(qComment)
                .where(
                        qComment.university.id.eq(id)
                                .and(qComment.account.eq(accountData))
                )
                .execute();
    }

    @Override
    public CommentPublic findByPublicId(Long id) {

        CommentPublic result = queryFactory
                .select(
                        Projections.constructor(
                                CommentPublic.class,
                                qComment.id,
                                qComment.content,
                                qComment.modifiedDate,
                                qComment.account.id,
                                qComment.account.nickname,
                                qComment.account.photo
                        )
                )
                .from(qComment)
                .leftJoin(qComment.account.photo, qFile)
                .where(
                        qComment.id.eq(id)
                )
                .fetchOne();

        return result;
    }

    @Override
    public List<CommentPublic> findByCommentList(Long id) {

        List<CommentPublic> result = queryFactory
                .select(
                        Projections.constructor(
                                CommentPublic.class,
                                qComment.id,
                                qComment.content,
                                qComment.modifiedDate,
                                qComment.account.id,
                                qComment.account.nickname,
                                qComment.account.photo
                        )
                )
                .from(qUniversity)
                .leftJoin(qUniversity.comments, qComment)
                .leftJoin(qComment.account.photo, qFile)
                .where(qUniversity.id.eq(id))
                .orderBy(qComment.modifiedDate.desc())
                .fetch();

        return result;
    }
}
