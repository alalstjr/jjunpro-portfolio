package com.jjunpro.project.repository;

import com.jjunpro.project.domain.QStore;
import com.jjunpro.project.projection.StorePublic;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryDSL {

    private QStore qStore = QStore.store;

    private final JPAQueryFactory queryFactory;

    /**
     * Long id
     * University {id} 값을 필요로 합니다.
     * Store 상점의 정보를 얻어오는 메소드
     */
    @Override
    public StorePublic findByStoreOne(Long id) {
        return queryFactory
                .select(Projections.constructor(
                        StorePublic.class,
                        qStore.stoId,
                        qStore.stoName,
                        qStore.stoAddress,
                        qStore.stoUrl
                ))
                .from(qStore)
                .where(qStore.stoUniList.any().id.eq(id))
                .fetchOne();
    }
}
