package com.jjunpro.project.repository;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.QUniversity;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.service.StoreService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class UniversityRepositoryImpl implements UniversityRepositoryDSL {

    private QUniversity qUniversity = QUniversity.university;

    private final JPAQueryFactory queryFactory;
    private final StoreService    storeService;

    @Override
    public UniversityPublic findByPublicId(
            Long id,
            Account account
    ) {
        University uniData = queryFactory
                .select(qUniversity)
                .from(qUniversity)
                .where(qUniversity.id.eq(id))
                .fetchOne();

        assert uniData != null;
        return getUniversityPublic(
                uniData,
                account
        );
    }

    private UniversityPublic getUniversityPublic(
            University data,
            Account account
    ) {
        /* INSERT 직후 해당 {id} 값을 조회하면 Hash 값이 null 로 표시되는 오류를 조치하는 코드 */
        Set<Account> uniLike = data.getUniLike() == null ? new HashSet<>() : data.getUniLike();

        return new UniversityPublic(
                data.getId(),
                data.getUniSubject(),
                data.getUniAtmosphere(),
                data.getUniPrice(),
                data.getUniContent(),
                data.getUniName(),
                data.getUniTag(),
                data.getUniStar(),
                data.getModifiedDate(),
                data
                        .getAccount()
                        .getId(),
                data
                        .getAccount()
                        .getNickname(),
                data
                        .getAccount()
                        .getUrlList(),
                uniLike.size(),
                uniLike.contains(account),
                data.getFiles(),
                data
                        .getAccount()
                        .getPhoto(),
                storeService.findByStoreOne(data.getId()),
                data
                        .getComments()
                        .size()
        );
    }
}
