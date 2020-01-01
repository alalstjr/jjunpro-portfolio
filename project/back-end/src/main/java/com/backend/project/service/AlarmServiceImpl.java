package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.Alarm;
import com.backend.project.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlarmServiceImpl implements AlarmService
{
    @Autowired
    private AlarmRepository alarmRepository;

    @Override
    public void deleteDataId(Long dataId, Account account)
    {
        if(alarmRepository.findByDataIdAndAccount(dataId, account))
            alarmRepository.deleteDataId(dataId);
    }

    @Override
    public void deleteData(Long dataId) {
        alarmRepository.deleteData(dataId);
    }

    @Override
    public List<Alarm> findByAlarmListWhereUserId(Account account)
    {
        List<Alarm> result =  alarmRepository.findByAlarmWhereUserId(account);
        return result;
    }

    @Override
    public Optional<Alarm> findById(Long id) {
        return alarmRepository.findById(id);
    }
}
