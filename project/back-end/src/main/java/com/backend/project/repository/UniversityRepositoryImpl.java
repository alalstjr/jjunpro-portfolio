package com.backend.project.repository;

import com.backend.project.domain.*;
import com.backend.project.projection.UniversityPublic;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@RequiredArgsConstructor
public class UniversityRepositoryImpl implements UniversityRepositoryDSL {

    private final JPAQueryFactory queryFactory;
    private QUniversity qUniversity = QUniversity.university;
    private QAccount qAccount       = QAccount.account;
    private QStore qStore           = QStore.store;

    @Override
    public Page<UniversityPublic> findByPublicAll(Pageable pageable, Account account) {

        Map<University, List<Account>> transform = queryFactory
                .from(qUniversity)
                .leftJoin(qUniversity.uniLike, qAccount)
                .where(qUniversity.publicStatus.eq(true).and(qUniversity.controlStatus.eq(false)))
                .transform(groupBy(qUniversity).as(list(qAccount)));

        List<UniversityPublic> results = getUniversityPublicList(transform, account);

        return new PageImpl<>(results, pageable, results.size());
    }

    @Override
    public List<UniversityPublic> findByUniversityListWhereAccountId(Account account, String userId, Long offsetCount) {
        Map<University, List<Account>> transform = queryFactory
            .from(qUniversity)
            .leftJoin(qUniversity.uniLike, qAccount)
            .where(
                qUniversity.publicStatus.eq(true)
                .and(qUniversity.controlStatus.eq(false))
                .and(qUniversity.account.userId.eq(userId))
            )
            .offset(8 * offsetCount)
            .limit(8)
            .transform(
                groupBy(qUniversity).as(list(qAccount))
            );

        List<UniversityPublic> results = getUniversityPublicList(transform, account);

        return results;
    }

    @Override
    public Page<UniversityPublic> findByLikeListWhereAccountId(Pageable pageable, Account account, String userId) {
        Map<University, List<Account>> transform = queryFactory
                .from(qUniversity)
                .leftJoin(qUniversity.uniLike, qAccount)
                .where(qUniversity.publicStatus.eq(true).and(qUniversity.controlStatus.eq(false)).and(qUniversity.uniLike.any().userId.eq(userId)))
                .transform(groupBy(qUniversity).as(list(qAccount)));

        List<UniversityPublic> results = getUniversityPublicList(transform, account);

        return new PageImpl<>(results, pageable, results.size());
    }

    @Override
    public UniversityPublic findByPublicId(Long id, Account account) {

        University data = queryFactory
                .select(qUniversity)
                .from(qUniversity)
                .where(qUniversity.id.eq(id))
                .fetchOne();

        return getUniversityPublic(data, account);
    }

    @Override
    public Boolean findByIdLike(Long id, Account account) {
        University result = queryFactory
                .select(qUniversity)
                .from(qUniversity)
                .where(qUniversity.id.eq(id).and(qUniversity.uniLike.contains(account)))
                .fetchOne();

        return (result == null ? false : true);
    }

    @Override
    @Transactional
    public void deleteData(Long id, Account accountData) {

        queryFactory
            .delete(qStore)
            .where(qStore.stoUniList.any().id.eq(id))
            .execute();

        queryFactory
            .delete(qUniversity)
            .where(qUniversity.id.eq(id).and(qUniversity.account.eq(accountData)))
            .execute();
    }

    private UniversityPublic getUniversityPublic(University data, Account account) {
        return new UniversityPublic(
                data.getId(),
                data.getUniSubject(),
                data.getUniContent(),
                data.getUniName(),
                data.getUniTag(),
                data.getUniStar(),
                data.getUniIp(),
                data.getModifiedDate(),
                data.getAccount().getId(),
                data.getAccount().getNickname(),
                data.getUniLike().size(),
                data.getUniLike().contains(account),
                data.getFiles()
        );
    }

    private List<UniversityPublic> getUniversityPublicList(Map<University, List<Account>> transform, Account account) {
        return transform.entrySet().stream()
                .map(
                        u -> new UniversityPublic(
                                u.getKey().getId(),
                                u.getKey().getUniSubject(),
                                u.getKey().getUniContent(),
                                u.getKey().getUniName(),
                                u.getKey().getUniTag(),
                                u.getKey().getUniStar(),
                                u.getKey().getUniIp(),
                                u.getKey().getModifiedDate(),
                                u.getKey().getAccount().getId(),
                                u.getKey().getAccount().getNickname(),
                                u.getValue().size(),
                                u.getKey().getUniLike().contains(account),
                                u.getKey().getFiles()
                        )
                )
                .collect(Collectors.toList());
    }
}
