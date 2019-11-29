package com.backend.project.repository;

import com.backend.project.domain.QAccount;
import com.backend.project.domain.QComment;
import com.backend.project.domain.QFile;
import com.backend.project.domain.QUniversity;
import com.backend.project.projection.CommentPublic;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryDSL {

    private final JPAQueryFactory queryFactory;
    private QComment qComment       = QComment.comment;
    private QUniversity qUniversity = QUniversity.university;
    private QAccount qAccount       = QAccount.account;
    private QFile qFile             = QFile.file;

    @Override
    public CommentPublic findByPublicId(Long id) {

        CommentPublic result = queryFactory
                .select(
                        Projections.constructor(
                                CommentPublic.class,
                                qComment.id,
                                qComment.content,
                                qComment.ip,
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
}
