package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.domain.QAccount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

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
}
