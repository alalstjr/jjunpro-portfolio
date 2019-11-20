package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.domain.QAccount;
import com.backend.project.projection.AccountPublic;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryDSL {

    private final JPAQueryFactory queryFactory;

    private QAccount qAccount = QAccount.account;

    @Override
    @Transactional
    public Long update(Account account) {

        Long result = queryFactory.update(qAccount)
                .where(qAccount.id.eq(account.getId()))
                .set(qAccount.nickname, account.getNickname())
                .set(qAccount.email, account.getEmail())
                .set(qAccount.urlList, account.getUrlList())
                .execute();

        return result;
    }

    @Override
    public List<AccountPublic> findByPublicAccountList() {

        List<Account> transform = queryFactory.selectFrom(qAccount)
                .fetch();

        List<AccountPublic> results = transform.stream().map(
                a -> new AccountPublic(
                        a.getId(),
                        a.getUserId(),
                        a.getNickname(),
                        a.getMyUniversity(),
                        a.getEmail(),
                        a.getUrlList(),
                        a.getCreatedDate(),
                        a.getModifiedDate()
                )
        ).collect(Collectors.toList());

        return results;
    }

    @Override
    public AccountPublic findOnePublicAccount(String userId) {
        AccountPublic result = queryFactory
                .select(
                        Projections.constructor(
                                AccountPublic.class,
                                qAccount.id,
                                qAccount.userId,
                                qAccount.nickname,
                                qAccount.myUniversity,
                                qAccount.email,
                                qAccount.urlList,
                                qAccount.createdDate,
                                qAccount.modifiedDate
                        )
                )
                .from(qAccount)
                .where(qAccount.userId.eq(userId))
                .fetchOne();

        return result;
    }
}
