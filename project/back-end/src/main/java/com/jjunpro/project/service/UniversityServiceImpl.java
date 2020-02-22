package com.jjunpro.project.service;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Store;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.dto.StoreDTO;
import com.jjunpro.project.dto.UniversityDTO;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.repository.StoreRepository;
import com.jjunpro.project.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;
    private final StoreRepository      storeRepository;

    @Override
    public UniversityPublic createUniversity(UniversityDTO dto) {

        University universityData = universityRepository.save(dto.toEntity());

        /* Store 정보를 등록 */
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoId(dto.getStoId());

        /*
         * { DATA DB } 에 음식점의 정보를 조회
         * { DB } 에 음식점의 정보가 존재한다면 { Store DB } 에 저장한 University 값을 추가합니다.
         * { DB } 에 음식점의 정보가 없다면 음식점의 ID 정보를 추가
         */
        Optional<Store> storeData = storeRepository.findByStoId(storeDTO.getStoId());

        if (storeData.isPresent()) {
            storeData
                    .get()
                    .getStoUniList()
                    .add(universityData);

            storeRepository.save(storeData.get());
        }
        else {
            storeDTO.setStoName(dto.getStoName());
            storeDTO.setStoAddress(dto.getStoAddress());
            storeDTO.setStoUrl(dto.getStoUrl());
            storeDTO
                    .getStoUniList()
                    .add(universityData);

            storeRepository.save(storeDTO.toEntity());
        }

        return findByPublicId(
                universityData.getId(),
                dto.getAccount()
        );
    }

    @Override
    public UniversityPublic updateUniversity(UniversityDTO dto) {
        return null;
    }

    @Override
    public UniversityPublic findByPublicId(
            Long id,
            Account account
    ) {
        return universityRepository.findByPublicId(
                id,
                account
        );
    }
}
