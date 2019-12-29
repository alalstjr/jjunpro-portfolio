package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.repository.AlarmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
