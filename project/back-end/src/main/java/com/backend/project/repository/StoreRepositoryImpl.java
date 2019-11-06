package com.backend.project.repository;

import com.backend.project.domain.*;
import com.backend.project.projection.StorePublic;
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
public class StoreRepositoryImpl implements StoreRepositoryDSL {

    private final JPAQueryFactory queryFactory;

    private QStore qStore = QStore.store;

    private QUniversity qUniversity = QUniversity.university;

    @Override
    public Long findByUniCount(String stoId) {

        Long result = queryFactory
                .from(qStore)
                .leftJoin(qStore.stoUniList, qUniversity)
                .where(qStore.stoId.eq(stoId))
                .fetchCount();

        return result;
    }

    @Override
    public Page<StorePublic> findByStoreUniAll(Pageable pageable, String storeId, Account account) {

        Map<Store, List<University>> transform = queryFactory
                .from(qStore)
                .leftJoin(qStore.stoUniList, qUniversity)
                .where(qUniversity.publicStatus.eq(true).and(qUniversity.controlStatus.eq(false)).and(qStore.stoId.eq(storeId)))
                .transform(groupBy(qStore).as(list(qUniversity)));

        List<StorePublic> results = transform.entrySet().stream()
                .map(
                        s -> new StorePublic(
                                s.getKey().getId(),
                                s.getKey().getStoUniList().stream().map(
                                    u -> new UniversityPublic(
                                            u.getId(),
                                            u.getUniSubject(),
                                            u.getUniContent(),
                                            u.getUniName(),
                                            u.getUniTag(),
                                            u.getUniStar(),
                                            u.getUniIp(),
                                            u.getModifiedDate(),
                                            u.getAccount().getId(),
                                            u.getAccount().getNickname(),
                                            u.getUniLike().size(),
                                            u.getUniLike().contains(account),
                                            u.getFiles()
                                    )
                                ).collect(Collectors.toList())
                        )
                )
                .collect(Collectors.toList());

        return new PageImpl<>(results, pageable, results.size());
    }
}
