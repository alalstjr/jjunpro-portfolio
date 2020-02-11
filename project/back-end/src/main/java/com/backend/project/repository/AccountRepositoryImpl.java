package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.domain.QAccount;
import com.backend.project.domain.QFile;
import com.backend.project.dto.AccountPwdUpdateDTO;
import com.backend.project.projection.AccountPublic;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryDSL {

    private QAccount qAccount = QAccount.account;
    private QFile    qFile    = QFile.file;

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public Long update(Account account) {

        // File 을 전송받는경우 와 안받는 경우
        if (account.getPhoto() != null) {
            return queryFactory.update(qAccount).where(qAccount.id.eq(account.getId())).set(
                    qAccount.nickname,
                    account.getNickname()
            ).set(
                    qAccount.email,
                    account.getEmail()
            ).set(
                    qAccount.urlList,
                    account.getUrlList()
            ).set(
                    qAccount.photo,
                    account.getPhoto()
            ).execute();
        }
        else {
            return queryFactory.update(qAccount).where(qAccount.id.eq(account.getId())).set(
                    qAccount.nickname,
                    account.getNickname()
            ).set(
                    qAccount.email,
                    account.getEmail()
            ).set(
                    qAccount.urlList,
                    account.getUrlList()
            ).execute();
        }
    }

    @Override
    @Transactional
    public Long pwdUpdate(AccountPwdUpdateDTO dto) {

        return queryFactory.update(qAccount).where(qAccount.id.eq(dto.getId())).set(
                qAccount.password,
                dto.getPassword()
        ).execute();
    }

    @Override
    public List<AccountPublic> findByPublicAccountList() {

        List<Account> transform = queryFactory.selectFrom(qAccount).fetch();

        List<AccountPublic> results = transform.stream().map(a -> new AccountPublic(
                a.getId(),
                a.getUserId(),
                a.getNickname(),
                a.getMyUniversity(),
                a.getEmail(),
                a.getUrlList(),
                a.getPhoto(),
                a.getCreatedDate(),
                a.getModifiedDate()
        )).collect(Collectors.toList());

        return results;
    }

    @Override
    public AccountPublic findOnePublicAccount(String userId) {
        AccountPublic result = queryFactory.select(Projections.constructor(
                AccountPublic.class,
                qAccount.id,
                qAccount.userId,
                qAccount.nickname,
                qAccount.myUniversity,
                qAccount.email,
                qAccount.urlList,
                qAccount.photo,
                qAccount.createdDate,
                qAccount.modifiedDate
        )).from(qAccount).leftJoin(
                qAccount.photo,
                qFile
        ).where(qAccount.userId.eq(userId)).fetchOne();

        return result;
    }
}
