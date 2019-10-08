package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.domain.QUniversity;
import com.backend.project.domain.University;
import com.backend.project.projection.UniversityPublic;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UniversityRepositoryImpl implements UniversityRepositoryDSL {

    private final JPAQueryFactory queryFactory;

    QUniversity qUniversity = QUniversity.university;

    @Override
    public Page<UniversityPublic> findByPublicAll(Pageable pageable) {

        QueryResults<UniversityPublic> results = queryFactory
                .select(Projections.fields(
                        UniversityPublic.class,
                        qUniversity
//                        qUniversity.id,
//                        qUniversity.uniSubject,
//                        qUniversity.uniContent,
//                        qUniversity.uniName,
//                        qUniversity.uniLocal,
//                        qUniversity.uniTag,
//                        qUniversity.uniStar,
//                        qUniversity.uniIp,
//                        qUniversity.modifiedDate,
//                        qUniversity.account.id.as("account_id"),
//                        qUniversity.account.nickname.as("account_nickname"),
//                        qUniversity.uniLike
                ))
                .from(qUniversity)
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    @Override
    public University findByIdLike(Long id, Account account) {

        University result = queryFactory
                .select(qUniversity)
                .from(qUniversity)
                .where(qUniversity.id.eq(id))
                .fetchOne();

        return result;
    }
}
