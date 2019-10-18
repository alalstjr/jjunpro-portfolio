package com.backend.project.repository;

import com.backend.project.domain.QStore;
import com.backend.project.domain.QUniversity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
