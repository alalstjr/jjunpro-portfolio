package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.University;
import com.backend.project.dto.StoreDTO;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.projection.UniversityPublic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UniversityService {

    public Optional<University> findById(Long id);
    public UniversityPublic findByPublicId(Long id, Account account);
    public Page<UniversityPublic> findByUniversityList(Pageable pageable, Account account);
    public Page<UniversityPublic> findByUniversityListWhereAccountId(Pageable pageable, Account account, String userId);
    public Page<UniversityPublic> findByLikeListWhereAccountId(Pageable pageable, Account account, String userId);
    public Boolean findByIdLike(Long id, Account account);

    public University saveOrUpdate(UniversitySaveDTO dto, StoreDTO storeDTO);
    public University saveOrUpdate(University universityData);

    public void deleteTask(Long id, Account accountData);
}
