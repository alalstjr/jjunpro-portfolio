package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.Store;
import com.backend.project.domain.University;
import com.backend.project.dto.StoreDTO;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.repository.StoreRepository;
import com.backend.project.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UniversityServiceImpl implements UniversityService {

    @Autowired
    UniversityRepository university;

    @Autowired
    StoreRepository store;

    @Override
    public Optional<University> findById(Long id) {
        return university.findById(id);
    }

    @Override
    public UniversityPublic findByPublicId(Long id, Account account) {
        return university.findByPublicId(id, account);
    }

    @Override
    public Page<UniversityPublic> findByUniversityList(Pageable pageable, Account account) {
        return university.findByPublicAll(pageable, account);
    }

    @Override
    public Page<UniversityPublic> findByUniversityListWhereAccountId(Pageable pageable, Account account, String userId) {
        return university.findByUniversityListWhereAccountId(pageable, account, userId);
    }

    @Override
    public University saveOrUpdate(UniversitySaveDTO dto, StoreDTO storeDTO) {
        University universityData =  university.save(dto.toEntity());

        storeDTO.setStoId(dto.getStoId());

        // DB 상에 음식점의 정보가 있는지 확인
        Optional<Store> storeData = store.findByStoId(storeDTO.getStoId());

        // DB 상에 음식점의 정보가 없다면 음식점의 ID 정보를 저장
        if(storeData.isPresent()) {
            storeData.get().getStoUniList().add(universityData);

            store.save(storeData.get());
        } else {
            storeDTO.setStoAddress(dto.getStoAddress());
            storeDTO.getStoUniList().add(universityData);

            store.save(storeDTO.toEntity());
        }

        return universityData;
    }

    @Override
    public University saveOrUpdate(University universityData) {
        return university.save(universityData);
    }

    @Override
    public Boolean findByIdLike(Long id, Account account) {
        return university.findByIdLike(id, account);
    }

    @Override
    public void deleteTask(Long id, Account accountData) {
        university.deleteTask(id, accountData);
    }
}
