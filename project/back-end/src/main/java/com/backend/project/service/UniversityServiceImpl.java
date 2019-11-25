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

import java.util.List;
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
    public List<UniversityPublic> findByUniversityListWhereAccountId(Account account, String userId, Long offsetCount) {
        return university.findByUniversityListWhereAccountId(account, userId, offsetCount);
    }

    @Override
    public Page<UniversityPublic> findByLikeListWhereAccountId(Pageable pageable, Account account, String userId) {
        return university.findByLikeListWhereAccountId(pageable, account, userId);
    }

    @Override
    public University saveOrUpdate(UniversitySaveDTO dto, StoreDTO storeDTO) {

        // update 경우 dto 기본값을 설정하여 UPDATE 해줍니다.
        if(dto.getId() != null) {
            University updateDto = university.findById(dto.getId()).get();
            dto.setId(updateDto.getId());
            dto.setUniLike(updateDto.getUniLike());
            dto.setUniName(updateDto.getUniName());
        }

        University universityData = university.save(dto.toEntity());

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
    public void deleteData(Long id, Account accountData) {
        university.deleteData(id, accountData);
    }
}
