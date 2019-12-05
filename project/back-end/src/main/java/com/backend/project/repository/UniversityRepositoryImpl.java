package com.backend.project.repository;

import com.backend.project.domain.*;
import com.backend.project.dto.SearchDTO;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.service.CommentServiceImpl;
import com.backend.project.service.FileStorageServiceImpl;
import com.backend.project.service.StoreService;
import com.backend.project.service.UniversityServiceImpl;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@RequiredArgsConstructor
public class UniversityRepositoryImpl implements UniversityRepositoryDSL
{
    private final JPAQueryFactory queryFactory;
    private QUniversity qUniversity = QUniversity.university;
    private QAccount qAccount       = QAccount.account;
    private QStore qStore           = QStore.store;

    @Autowired
    private UniversityServiceImpl universityService;

    @Autowired
    private FileStorageServiceImpl fileStorageService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private StoreService storeService;

    @Override
    @Transactional
    public void deleteData(Long id, Account accountData)
    {
        // University File Data
        List<File> uniFiles = universityService.findById(id).get().getFiles();
        if(!uniFiles.isEmpty())
        {
            System.out.println(uniFiles.get(0).getId());
        }

        // qStore 삭제
        queryFactory
                .delete(qStore)
                .where(qStore.stoUniList.any().id.eq(id))
                .execute();

        // University 삭제
        queryFactory
                .delete(qUniversity)
                .where(qUniversity.id.eq(id).and(qUniversity.account.eq(accountData)))
                .execute();

        // University 저장된 Comment 삭제
        commentService.deleteUniComment(id, accountData);

        if(!uniFiles.isEmpty())
        {
            // University 저장된 File 삭제
            fileStorageService.filesDelete(uniFiles, "university");
        }
    }

    @Override
    public Page<UniversityPublic> findByPublicAll(Pageable pageable, Account account)
    {
        Map<University, List<Account>> transform = queryFactory
                .from(qUniversity)
                .leftJoin(qUniversity.uniLike, qAccount)
                .where(qUniversity.publicStatus.eq(true).and(qUniversity.controlStatus.eq(false)))
                .transform(groupBy(qUniversity).as(list(qAccount)));

        List<UniversityPublic> results = getUniversityPublicList(transform, account);

        return new PageImpl<>(results, pageable, results.size());
    }

    @Override
    public List<UniversityPublic> findByUniversityListWhereAccountId(SearchDTO searchDTO)
    {
        Map<University, List<Account>> transform = queryFactory
                .from(qUniversity)
                .leftJoin(qUniversity.uniLike, qAccount)
                .where(
                        qUniversity.publicStatus.eq(true)
                        .and(qUniversity.controlStatus.eq(false))
                        .and(qUniversity.account.userId.eq(searchDTO.getKeyword()))
                )
                .offset(8 * searchDTO.getOffsetCount())
                .limit(8)
                .transform(
                    groupBy(qUniversity).as(list(qAccount))
                );

        List<UniversityPublic> results = getUniversityPublicList(transform, searchDTO.getAccount());

        return results;
    }

    @Override
    public List<UniversityPublic> findByLikeListWhereAccountId(SearchDTO searchDTO)
    {
        Map<University, List<Account>> transform = queryFactory
                .from(qUniversity)
                .leftJoin(qUniversity.uniLike, qAccount)
                .where(
                        qUniversity.publicStatus.eq(true)
                        .and(qUniversity.controlStatus.eq(false))
                        .and(qUniversity.uniLike.any().userId.eq(searchDTO.getKeyword()))
                )
                .offset(8 * searchDTO.getOffsetCount())
                .limit(8)
                .transform(
                        groupBy(qUniversity).as(list(qAccount))
                );

        List<UniversityPublic> results = getUniversityPublicList(transform, searchDTO.getAccount());

        return results;
    }

    @Override
    public List<UniversityPublic> findByUniversityListWhereKeyword(SearchDTO searchDTO)
    {
        // 검색 대상 분류 동적 조건
        BooleanBuilder builder = new BooleanBuilder();
        switch (searchDTO.getClassification()) {
            case "uniName" :
                builder.and(qUniversity.uniName.contains(searchDTO.getKeyword()));
                break;

            case "stoId" :
                builder.and(qStore.stoId.contains(searchDTO.getKeyword()));
                break;

            case "uniTag" :
                builder.and(qUniversity.uniTag.contains(searchDTO.getKeyword()));
                break;

            case "userId" :
                builder.and(qUniversity.account.userId.contains(searchDTO.getKeyword()));
                break;

            default:
                break;
        }

        Map<University, List<Account>> transform = queryFactory
                .from(qUniversity)
                .leftJoin(qUniversity.uniLike, qAccount)
                .where(
                        qUniversity.publicStatus.eq(true)
                        .and(qUniversity.controlStatus.eq(false))
                        .and(builder)
                )
                .offset(8 * searchDTO.getOffsetCount())
                .limit(8)
                .transform(
                        groupBy(qUniversity).as(list(qAccount))
                );

        List<UniversityPublic> results = getUniversityPublicList(transform, searchDTO.getAccount());

        return results;
    }

    @Override
    public UniversityPublic findByPublicId(Long id, Account account)
    {
        University uniData = queryFactory
                .select(qUniversity)
                .from(qUniversity)
                .where(qUniversity.id.eq(id))
                .fetchOne();

        return getUniversityPublic(uniData, account);
    }

    @Override
    public Boolean findByIdLike(Long id, Account account)
    {
        University result = queryFactory
                .select(qUniversity)
                .from(qUniversity)
                .where(qUniversity.id.eq(id).and(qUniversity.uniLike.contains(account)))
                .fetchOne();

        return (result == null ? false : true);
    }

    private UniversityPublic getUniversityPublic(University data, Account account)
    {
        // INSERT 직후 해당 {id} 값을 조회하면 Hash 값이 null 로 표시되는 오류를 조치하는 코드
        Set<Account> uniLike = data.getUniLike() == null ? new HashSet<>() : data.getUniLike();

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
                data.getAccount().getUrlList(),
                uniLike.size(),
                uniLike.contains(account),
                data.getFiles(),
                data.getAccount().getPhoto(),
                storeService.findByStoreOne(data.getId()),
                data.getComments().size()
        );
    }

    private List<UniversityPublic> getUniversityPublicList(Map<University, List<Account>> transform, Account account)
    {
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
                                u.getKey().getAccount().getUrlList(),
                                u.getValue().size(),
                                u.getKey().getUniLike().contains(account),
                                u.getKey().getFiles(),
                                u.getKey().getAccount().getPhoto(),
                                storeService.findByStoreOne(u.getKey().getId()),
                                u.getKey().getComments().size()
                        )
                )
                .collect(Collectors.toList());
    }
}
