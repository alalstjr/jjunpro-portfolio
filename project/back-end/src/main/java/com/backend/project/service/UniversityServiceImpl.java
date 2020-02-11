package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.File;
import com.backend.project.domain.Store;
import com.backend.project.domain.University;
import com.backend.project.dto.SearchDTO;
import com.backend.project.dto.StoreDTO;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.repository.StoreRepository;
import com.backend.project.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {
    private final UniversityRepository university;
    private final StoreRepository      store;

    @Override
    public Optional<University> findById(Long id) {
        return university.findById(id);
    }

    @Override
    public UniversityPublic findByPublicId(Long id, Account account) {
        return university.findByPublicId(
                id,
                account
        );
    }

    @Override
    public Page<UniversityPublic> findByUniversityList(Pageable pageable, Account account) {
        return university.findByPublicAll(
                pageable,
                account
        );
    }

    @Override
    public List<UniversityPublic> findByUniversityListWhereAccountId(SearchDTO searchDTO) {
        return university.findByUniversityListWhereAccountId(searchDTO);
    }

    @Override
    public List<UniversityPublic> findByUniversityListWhereAccountNickname(SearchDTO searchDTO) {
        return university.findByUniversityListWhereAccountNickname(searchDTO);
    }

    @Override
    public List<UniversityPublic> findByLikeListWhereAccountId(SearchDTO searchDTO) {
        return university.findByLikeListWhereAccountId(searchDTO);
    }

    @Override
    public UniversityPublic saveOrUpdate(UniversitySaveDTO dto, Account accountData) {
        // UPDATE
        if (dto.getId() != null) {
            // { DATA DB } 값이 조회
            Optional<University> updateDto = university.findById(dto.getId());

            // { DATA DB } 조회한 값을 DTO 값에 UPDATE 수정
            if (updateDto.isPresent()) {
                dto.setId(updateDto.get().getId());
                dto.setUniLike(updateDto.get().getUniLike());
                dto.setUniName(updateDto.get().getUniName());
                dto.setComments(updateDto.get().getComments());

                List<File> updateFile = updateDto.get().getFiles();

                // UPDATE 기존 file의 제거되는게 있는경우
                if (dto.getRemoveFiles().length > 0) {
                    for (int i = 0; i < dto.getRemoveFiles().length; i++) {
                        updateFile = updateFileFilter(
                                updateFile,
                                dto,
                                i
                        );
                    }
                }

                // UPDATE file 존재하는 경우와 아닌경우
                if (dto.getFileData() == null) {
                    dto.setFileData(updateFile);
                }
                else {
                    dto.getFileData().addAll(updateFile);
                }
            }
        }

        University universityData = university.save(dto.toEntity());

        // Store 정보를 등록
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setStoId(dto.getStoId());

        // { DATA DB } 에 음식점의 정보를 조회
        Optional<Store> storeData = store.findByStoId(storeDTO.getStoId());

        // { DB } 에 음식점의 정보가 존재한다면 { Store DB } 에 저장한 University 값을 추가합니다.
        // { DB } 에 음식점의 정보가 없다면 음식점의 ID 정보를 추가
        if (storeData.isPresent()) {
            storeData.get().getStoUniList().add(universityData);

            store.save(storeData.get());
        }
        else {
            storeDTO.setStoName(dto.getStoName());
            storeDTO.setStoAddress(dto.getStoAddress());
            storeDTO.setStoUrl(dto.getStoUrl());
            storeDTO.getStoUniList().add(universityData);

            store.save(storeDTO.toEntity());
        }

        return findByPublicId(
                universityData.getId(),
                accountData
        );
    }

    @Override
    public University saveOrUpdate(University universityData) {
        return university.save(universityData);
    }

    @Override
    public Boolean findByIdLike(Long id, Account account) {
        return university.findByIdLike(
                id,
                account
        );
    }

    @Override
    public List<UniversityPublic> findByUniversityListWhereKeyword(SearchDTO searchDTO) {
        return university.findByUniversityListWhereKeyword(searchDTO);
    }

    @Override
    public List<UniversityPublic> findByOrderByCreatedDateDesc(Account account) {
        return university.findByOrderByCreatedDateDesc(account);
    }

    @Override
    public List<UniversityPublic> findByOrderByMostLike(Account account) {
        return university.findByOrderByMostLike(account);
    }

    @Override
    public Long findByIdUniCount(String uniName) {
        return university.findByIdUniCount(uniName);
    }

    @Override
    public void deleteData(Long id, Account accountData) {
        university.deleteData(
                id,
                accountData
        );
    }

    /**
     * Client 에서 기존 업로드된 파일의 제거된 {id}값을 기존 file와 비교하여 제거하는 메소드
     */
    private List<File> updateFileFilter(List<File> result, UniversitySaveDTO dto, Integer i) {
        return result.stream().filter(f -> f.getId() != dto.getRemoveFiles()[i]).collect(Collectors.toList());
    }
}
