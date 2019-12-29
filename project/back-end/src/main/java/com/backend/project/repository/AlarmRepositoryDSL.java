package com.backend.project.repository;

import com.backend.project.domain.Account;

public interface AlarmRepositoryDSL
{
    public void deleteDataId(Long id);
    public boolean findByDataIdAndAccount(Long dataId, Account account);
}
