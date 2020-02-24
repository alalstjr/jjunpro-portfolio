package com.jjunpro.project.repository;

import com.jjunpro.project.domain.QStore;
import com.jjunpro.project.domain.QUniversity;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.dto.SearchDTO;
import com.jjunpro.project.projection.StorePublic;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.util.QueryDslUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryDSL {

    private QStore      qStore      = QStore.store;
    private QUniversity qUniversity = QUniversity.university;

    private final JPAQueryFactory queryFactory;
    private final QueryDslUtil    queryDslUtil;

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

    @Override
    public Long findByUniCount(String keyword) {

        Long result = queryFactory
                .select(qUniversity)
                .from(qStore)
                .leftJoin(
                        qStore.stoUniList,
                        qUniversity
                )
                .where(qStore.stoId.eq(keyword))
                .fetchCount();

        return result;
    }

    @Override
    public List<UniversityPublic> findByStoreUniAll(SearchDTO searchDTO) {

        List<University> uniData = queryFactory
                .select(qUniversity)
                .from(qStore)
                .leftJoin(
                        qStore.stoUniList,
                        qUniversity
                )
                .where(qUniversity.publicStatus
                        .eq(true)
                        .and(qUniversity.controlStatus.eq(false))
                        .and(queryDslUtil.getSearchKeyword(searchDTO))
                        .and(queryDslUtil.getSearchCate(searchDTO)))
                .orderBy(queryDslUtil.getSearchOrderBy(searchDTO))
                .offset(8 * searchDTO.getOffsetCount())
                .limit(8)
                .fetch();

        List<UniversityPublic> results = uniData
                .stream()
                .map(u -> new UniversityPublic(
                        u.getId(),
                        u.getUniSubject(),
                        u.getUniAtmosphere(),
                        u.getUniPrice(),
                        u.getUniContent(),
                        u.getUniName(),
                        u.getUniTag(),
                        u.getUniStar(),
                        u.getModifiedDate(),
                        u
                                .getAccount()
                                .getId(),
                        u
                                .getAccount()
                                .getNickname(),
                        u
                                .getAccount()
                                .getUrlList(),
                        u
                                .getUniLike()
                                .size(),
                        u
                                .getUniLike()
                                .contains(searchDTO.getAccount()),
                        u.getFiles(),
                        u
                                .getAccount()
                                .getPhoto(),
                        findByStoreOne(u.getId()),
                        u
                                .getComments()
                                .size()
                ))
                .collect(Collectors.toList());

        return results;
    }
}
