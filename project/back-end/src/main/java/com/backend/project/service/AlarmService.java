package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.Alarm;

import java.util.List;
import java.util.Optional;

public interface AlarmService
{
    public void deleteDataId(Long dataId, Account account);
    public void deleteData(Long dataId);
    public List<Alarm> findByAlarmListWhereUserId(Account account);

    public Optional<Alarm> findById(Long id);
}
