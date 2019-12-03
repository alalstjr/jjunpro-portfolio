package com.backend.project.repository;

import com.backend.project.domain.QFile;
import com.backend.project.domain.QStore;
import com.backend.project.domain.QUniversity;
import com.backend.project.domain.University;
import com.backend.project.dto.SearchDTO;
import com.backend.project.projection.StorePublic;
import com.backend.project.projection.UniversityPublic;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StoreRepositoryImpl implements StoreRepositoryDSL {

    private final JPAQueryFactory queryFactory;
    private QStore qStore = QStore.store;
    private QUniversity qUniversity = QUniversity.university;
    private QFile qFile = QFile.file;

    @Override
    public Long findByUniCount(String stoId) {

        Long result = queryFactory
                .select(qUniversity)
                .from(qStore)
                .leftJoin(qStore.stoUniList, qUniversity)
                .where(qStore.stoId.eq(stoId))
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
                )
                .orderBy(qUniversity.uniLike.size().desc())
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
                        u.getUniIp(),
                        u.getModifiedDate(),
                        u.getAccount().getId(),
                        u.getAccount().getNickname(),
                        u.getUniLike().size(),
                        u.getUniLike().contains(searchDTO.getAccount()),
                        u.getFiles(),
                        u.getAccount().getPhoto(),
                        findByStoreOne(u.getId()),
                        u.getComments().size()
                )
        ).collect(Collectors.toList());

//        Map<Store, List<University>> transform = queryFactory
//                .select(qUniversity)
//                .from(qStore)
//                .leftJoin(qStore.stoUniList, qUniversity)
//                .where(
//                        qUniversity.publicStatus.eq(true)
//                                .and(qUniversity.controlStatus.eq(false))
//                                .and(qStore.stoId.eq(storeId))
//                )
//                .transform(groupBy(qStore).as(list(qUniversity)));
//
//        List<StorePublic> results = transform.entrySet().stream()
//                .map(
//                        s -> new StorePublic(
//                                s.getKey().getId(),
//                                s.getKey().getStoUniList().stream().map(
//                                    u -> new UniversityPublic(
//                                            u.getId(),
//                                            u.getUniSubject(),
//                                            u.getUniContent(),
//                                            u.getUniName(),
//                                            u.getUniTag(),
//                                            u.getUniStar(),
//                                            u.getUniIp(),
//                                            u.getModifiedDate(),
//                                            u.getAccount().getId(),
//                                            u.getAccount().getNickname(),
//                                            u.getUniLike().size(),
//                                            u.getUniLike().contains(account),
//                                            u.getFiles()
//                                    )
//                                ).sorted(Comparator.reverseOrder()).collect(Collectors.toList())
//                        )
//                )
//                .collect(Collectors.toList());

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
