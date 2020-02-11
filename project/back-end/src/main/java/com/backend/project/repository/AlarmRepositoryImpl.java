package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.domain.Alarm;
import com.backend.project.domain.QAlarm;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
public class AlarmRepositoryImpl implements AlarmRepositoryDSL {

    private QAlarm qAlarm = QAlarm.alarm;

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public void deleteDataId(Long id) {
        // University Id 값을 조회하는 삭제 메소드
        queryFactory
                .delete(qAlarm)
                .where(qAlarm.dataId.eq(id))
                .execute();
    }

    @Override
    @Transactional
    public void deleteData(Long id) {
        // Alarm Id 값을 조회하는 삭제 메소드
        queryFactory
                .delete(qAlarm)
                .where(qAlarm.id.eq(id))
                .execute();
    }

    @Override
    public boolean findByDataIdAndAccount(Long dataId, Account account) {
        // DB 조회한 결과 사용자의 정보가 Alarm DB 포함되어 있다면 dataId 탐색후 모두 제거
        BooleanExpression result = queryFactory
                .selectFrom(qAlarm)
                .where(qAlarm.dataId
                        .eq(dataId)
                        .and(qAlarm.userId.eq(account.getId())))
                .exists();

        return result != null;
    }

    @Override
    public List<Alarm> findByAlarmWhereUserId(Account account) {
        return queryFactory
                .selectFrom(qAlarm)
                .where(qAlarm.userId.eq(account.getId()))
                .fetch();
    }
}
