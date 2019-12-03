package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.domain.File;
import com.backend.project.domain.QAccount;
import com.backend.project.domain.QFile;
import com.backend.project.dto.AccountPwdUpdateDTO;
import com.backend.project.projection.AccountPublic;
import com.backend.project.service.AccountService;
import com.backend.project.service.FileStorageService;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepositoryDSL {

    private final JPAQueryFactory queryFactory;
    private QAccount qAccount = QAccount.account;
    private QFile qFile = QFile.file;

    @Autowired
    AccountService accountService;

    @Autowired
    FileStorageService fileStorageService;

    @Override
    @Transactional
    public Long update(Account account) {

        // 파일을 전송받는경우와 안받는 경우
        if(account.getPhoto() != null) {

            // Account File Data
            File accountFile = accountService.findById(account.getId()).get().getPhoto();

            // 기존에 업로드된 파일이 존재할 경우
            if(accountFile != null) {
                // Account 저장된 File 삭제
                fileStorageService.fileDelete(accountFile, "account");
            }

            return queryFactory.update(qAccount)
                    .where(qAccount.id.eq(account.getId()))
                    .set(qAccount.nickname, account.getNickname())
                    .set(qAccount.email, account.getEmail())
                    .set(qAccount.urlList, account.getUrlList())
                    .set(qAccount.photo, account.getPhoto())
                    .execute();
        } else {
            return queryFactory.update(qAccount)
                    .where(qAccount.id.eq(account.getId()))
                    .set(qAccount.nickname, account.getNickname())
                    .set(qAccount.email, account.getEmail())
                    .set(qAccount.urlList, account.getUrlList())
                    .execute();
        }
    }

    @Override
    @Transactional
    public Long pwdUpdate(AccountPwdUpdateDTO dto) {

        return queryFactory.update(qAccount)
                .where(qAccount.id.eq(dto.getId()))
                .set(qAccount.password, dto.getPassword())
                .execute();
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
                        a.getPhoto(),
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
                                qAccount.photo,
                                qAccount.createdDate,
                                qAccount.modifiedDate
                        )
                )
                .from(qAccount)
                .leftJoin(qAccount.photo, qFile)
                .where(qAccount.userId.eq(userId))
                .fetchOne();

        return result;
    }
}
