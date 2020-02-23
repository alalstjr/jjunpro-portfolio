package com.jjunpro.project.repository;

import com.jjunpro.project.domain.QAccount;
import com.jjunpro.project.domain.QFile;
import com.jjunpro.project.dto.UpdateAccountPwdDTO;
import com.jjunpro.project.projection.AccountPublic;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryDSL {

    private QFile    qFile    = QFile.file;
    private QAccount qAccount = QAccount.account;

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public Long updatePassword(UpdateAccountPwdDTO dto) {
        return queryFactory
                .update(qAccount)
                .where(qAccount.id.eq(dto.getId()))
                .set(
                        qAccount.password,
                        dto.getPassword()
                )
                .execute();
    }

    @Override
    public AccountPublic findOnePublicAccount(String username) {
        return queryFactory
                .select(Projections.constructor(
                        AccountPublic.class,
                        qAccount.id,
                        qAccount.username,
                        qAccount.nickname,
                        qAccount.myUniversity,
                        qAccount.email,
                        qAccount.urlList,
                        qAccount.photo,
                        qAccount.createdDate,
                        qAccount.modifiedDate
                ))
                .from(qAccount)
                .leftJoin(
                        qAccount.photo,
                        qFile
                )
                .where(qAccount.username.eq(username))
                .fetchOne();
    }
}
