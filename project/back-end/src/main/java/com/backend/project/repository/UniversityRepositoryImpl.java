package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.domain.QAccount;
import com.backend.project.domain.QUniversity;
import com.backend.project.domain.University;
import com.backend.project.projection.UniversityPublic;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class UniversityRepositoryImpl implements UniversityRepositoryDSL {

    private final JPAQueryFactory queryFactory;

    QUniversity qUniversity = QUniversity.university;

    QAccount qAccount = QAccount.account;

    @Override
    public Page<UniversityPublic> findByPublicAll(Pageable pageable) {

        Map<University, List<Account>> transform = queryFactory
                .from(qUniversity)
                .leftJoin(qUniversity.uniLike, qAccount)
                .transform(groupBy(qUniversity).as(list(qAccount)));

        List<UniversityPublic> results = transform.entrySet().stream()
                .map(
                        u -> new UniversityPublic(
                                u.getKey().getId(),
                                u.getKey().getUniSubject(),
                                u.getKey().getUniContent(),
                                u.getKey().getUniName(),
                                u.getKey().getUniTag(),
                                u.getKey().getUniLocal(),
                                u.getKey().getUniStar(),
                                u.getKey().getUniIp(),
                                u.getKey().getModifiedDate(),
                                u.getKey().getAccount().getId(),
                                u.getKey().getAccount().getNickname(),
                                u.getValue().size()
                        )
                )
                .collect(Collectors.toList());

        return new PageImpl<>(results, pageable, results.size());
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
