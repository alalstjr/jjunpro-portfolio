package com.jjunpro.project.repository;

import com.jjunpro.project.domain.QComment;
import com.jjunpro.project.domain.QFile;
import com.jjunpro.project.domain.QUniversity;
import com.jjunpro.project.projection.CommentPublic;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryDSL {

    private QComment    qComment    = QComment.comment;
    private QUniversity qUniversity = QUniversity.university;
    private QFile       qFile       = QFile.file;

    private final JPAQueryFactory queryFactory;

    @Override
    public CommentPublic findByPublicId(Long id) {

        return queryFactory
                .select(Projections.constructor(
                        CommentPublic.class,
                        qComment.id,
                        qComment.content,
                        qComment.modifiedDate,
                        qComment.account.id,
                        qComment.account.nickname,
                        qComment.account.photo
                ))
                .from(qComment)
                .leftJoin(
                        qComment.account.photo,
                        qFile
                )
                .where(qComment.id.eq(id))
                .fetchOne();
    }
}
