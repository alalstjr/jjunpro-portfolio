package com.jjunpro.project.repository;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Alarm;

import java.util.List;

public interface AlarmRepositoryDSL {

    void deleteDataId(Long id);

    void deleteData(Long id);

    boolean findByDataIdAndAccount(Long dataId, Account account);

    List<Alarm> findByAlarmWhereUserId(Account account);
}
