package com.jjunpro.project.service;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Alarm;

import java.util.List;
import java.util.Optional;

public interface AlarmService {

    void deleteDataId(
            Long dataId,
            Account account
    );

    void deleteData(Long dataId);

    List<Alarm> findByAlarmListWhereUserId(Account account);

    Optional<Alarm> findById(Long id);
}
