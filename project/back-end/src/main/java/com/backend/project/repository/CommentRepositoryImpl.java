package com.backend.project.repository;

import com.backend.project.domain.QAccount;
import com.backend.project.domain.QUniversity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryDSL {

    private final JPAQueryFactory queryFactory;
    private QUniversity qUniversity = QUniversity.university;
    private QAccount qAccount       = QAccount.account;

}
