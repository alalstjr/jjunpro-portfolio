package com.jjunpro.project.repository;

import com.jjunpro.project.domain.*;
import com.jjunpro.project.dto.SearchDTO;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.service.StoreService;
import com.jjunpro.project.util.QueryDslUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class UniversityRepositoryImpl implements UniversityRepositoryDSL {

    private QUniversity qUniversity = QUniversity.university;
    private QAccount    qAccount    = QAccount.account;
    private QStore      qStore      = QStore.store;

    private final JPAQueryFactory queryFactory;
    private final StoreService    storeService;
    private final QueryDslUtil    queryDslUtil;

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
        return
                getUniversityPublic(
                uniData,
                account
        );
    }

    @Override
    public List<UniversityPublic> findByUniversityListWhereSearchDto(SearchDTO searchDTO) {
        Map<University, List<Account>> transform = queryFactory
                .from(qUniversity)
                .leftJoin(
                        qUniversity.uniLike,
                        qAccount
                )
                .where(qUniversity.publicStatus
                        .eq(true)
                        .and(qUniversity.controlStatus.eq(false))
                        .and(queryDslUtil.getSearchKeyword(searchDTO))
                        .and(queryDslUtil.getSearchCate(searchDTO)))
                .orderBy(queryDslUtil.getSearchOrderBy(searchDTO))
                .offset(8 * searchDTO.getOffsetCount())
                .limit(8)
                .transform(groupBy(qUniversity).as(list(qAccount)));

        return getUniversityPublicList(
                transform,
                searchDTO.getAccount()
        );
    }

    @Override
    public List<UniversityPublic> findByOrderByCreatedDateDesc(Account account) {
        Map<University, List<Account>> transform = queryFactory
                .from(qUniversity)
                .leftJoin(
                        qUniversity.uniLike,
                        qAccount
                )
                .where(qUniversity.publicStatus
                        .eq(true)
                        .and(qUniversity.controlStatus.eq(false)))
                .orderBy(qUniversity.createdDate.desc())
                .limit(9)
                .transform(groupBy(qUniversity).as(list(qAccount)));

        return getUniversityPublicList(
                transform,
                account
        );
    }

    @Override
    public Long findByIdUniCount(String uniName) {
        return queryFactory
                .selectFrom(qUniversity)
                .where(qUniversity.uniName.eq(uniName))
                .fetchCount();
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

    @Override
    @Transactional
    public void deleteData(
            Long id,
            Account accountData
    ) {
        /* qStore 삭제 */
        queryFactory
                .delete(qStore)
                .where(qStore.stoUniList.any().id.eq(id))
                .execute();

        /* University 삭제 */
        queryFactory
                .delete(qUniversity)
                .where(qUniversity.id
                        .eq(id)
                        .and(qUniversity.account.eq(accountData)))
                .execute();
    }

    private List<UniversityPublic> getUniversityPublicList(
            Map<University, List<Account>> transform,
            Account account
    ) {
        return transform
                .entrySet()
                .stream()
                .map(u -> new UniversityPublic(
                        u
                                .getKey()
                                .getId(),
                        u
                                .getKey()
                                .getUniSubject(),
                        u
                                .getKey()
                                .getUniAtmosphere(),
                        u
                                .getKey()
                                .getUniPrice(),
                        u
                                .getKey()
                                .getUniContent(),
                        u
                                .getKey()
                                .getUniName(),
                        u
                                .getKey()
                                .getUniTag(),
                        u
                                .getKey()
                                .getUniStar(),
                        u
                                .getKey()
                                .getModifiedDate(),
                        u
                                .getKey()
                                .getAccount()
                                .getId(),
                        u
                                .getKey()
                                .getAccount()
                                .getNickname(),
                        u
                                .getKey()
                                .getAccount()
                                .getUrlList(),
                        u
                                .getValue()
                                .size(),
                        u
                                .getKey()
                                .getUniLike()
                                .contains(account),
                        u
                                .getKey()
                                .getFiles(),
                        u
                                .getKey()
                                .getAccount()
                                .getPhoto(),
                        storeService.findByStoreOne(u
                                .getKey()
                                .getId()),
                        u
                                .getKey()
                                .getComments()
                                .size()
                ))
                .collect(Collectors.toList());
    }
}
