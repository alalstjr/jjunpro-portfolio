package com.backend.project.repository;

import com.backend.project.domain.QStore;
import com.backend.project.domain.QUniversity;
import com.backend.project.domain.University;
import com.backend.project.dto.SearchDTO;
import com.backend.project.projection.StorePublic;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.util.RepositoryUtill;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryDSL {

    private final JPAQueryFactory queryFactory;
    private QStore qStore = QStore.store;
    private QUniversity qUniversity = QUniversity.university;

    @Autowired
    private RepositoryUtill repositoryUtill;

    @Override
    public Long findByUniCount(String keyword) {

        Long result = queryFactory
                .select(qUniversity)
                .from(qStore)
                .leftJoin(qStore.stoUniList, qUniversity)
                .where(qStore.stoId.eq(keyword))
                .fetchCount();

        return result;
    }

    @Override
    public List<UniversityPublic> findByStoreUniAll(SearchDTO searchDTO) {

        List<University> uniData = queryFactory
                .select(qUniversity)
                .from(qStore)
                .leftJoin(qStore.stoUniList, qUniversity)
                .where(
                        qUniversity.publicStatus.eq(true)
                        .and(qUniversity.controlStatus.eq(false))
                        .and(qStore.stoId.eq(searchDTO.getKeyword()))
                        .and(repositoryUtill.getSearchCate(searchDTO))
                )
                .orderBy(
                        repositoryUtill.getSearchOrderBy(searchDTO)
                )
                .offset(8 * searchDTO.getOffsetCount())
                .limit(8)
                .fetch();

        List<UniversityPublic> results = uniData.stream().map(
                u -> new UniversityPublic(
                        u.getId(),
                        u.getUniSubject(),
                        u.getUniContent(),
                        u.getUniName(),
                        u.getUniTag(),
                        u.getUniStar(),
                        u.getModifiedDate(),
                        u.getAccount().getId(),
                        u.getAccount().getNickname(),
                        u.getAccount().getUrlList(),
                        u.getUniLike().size(),
                        u.getUniLike().contains(searchDTO.getAccount()),
                        u.getFiles(),
                        u.getAccount().getPhoto(),
                        findByStoreOne(u.getId()),
                        u.getComments().size()
                )
        ).collect(Collectors.toList());

        return results;
    }

    /**
     * Long id
     * University {id} 값을 필요로 합니다.
     * Store 상점의 정보를 얻어오는 메소드
     * */
    @Override
    public StorePublic findByStoreOne(Long id) {
        return queryFactory
                .select(
                        Projections.constructor(
                                StorePublic.class,
                                qStore.stoId,
                                qStore.stoName,
                                qStore.stoAddress,
                                qStore.stoUrl
                        )
                )
                .from(qStore)
                .where(qStore.stoUniList.any().id.eq(id))
                .fetchOne();
    }
}
