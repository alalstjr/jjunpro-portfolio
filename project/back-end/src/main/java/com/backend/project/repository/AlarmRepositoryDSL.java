package com.backend.project.repository;

import com.backend.project.domain.Account;
import com.backend.project.domain.Alarm;

import java.util.List;

public interface AlarmRepositoryDSL {

    public void deleteDataId(Long id);

    public void deleteData(Long id);

    public boolean findByDataIdAndAccount(Long dataId, Account account);

    List<Alarm> findByAlarmWhereUserId(Account account);
}
