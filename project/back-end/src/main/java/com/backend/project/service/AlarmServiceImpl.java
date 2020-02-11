package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.Alarm;
import com.backend.project.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final AlarmRepository alarmRepository;

    @Override
    public void deleteDataId(
            Long dataId,
            Account account
    ) {
        if (alarmRepository.findByDataIdAndAccount(
                dataId,
                account
        ))
            alarmRepository.deleteDataId(dataId);
    }

    @Override
    public void deleteData(Long dataId) {
        alarmRepository.deleteData(dataId);
    }

    @Override
    public List<Alarm> findByAlarmListWhereUserId(Account account) {
        return alarmRepository.findByAlarmWhereUserId(account);
    }

    @Override
    public Optional<Alarm> findById(Long id) {
        return alarmRepository.findById(id);
    }
}
