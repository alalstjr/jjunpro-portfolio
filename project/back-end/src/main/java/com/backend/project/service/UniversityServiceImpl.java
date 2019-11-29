package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.domain.File;
import com.backend.project.domain.Store;
import com.backend.project.domain.University;
import com.backend.project.dto.StoreDTO;
import com.backend.project.dto.UniversitySaveDTO;
import com.backend.project.projection.CommentPublic;
import com.backend.project.projection.UniversityPublic;
import com.backend.project.repository.StoreRepository;
import com.backend.project.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<UniversityPublic> findByLikeListWhereAccountId(Account account, String userId, Long offsetCount) {
        return university.findByLikeListWhereAccountId(account, userId, offsetCount);
    }

    @Override
    public UniversityPublic saveOrUpdate(UniversitySaveDTO dto, StoreDTO storeDTO, Account accountData) {

        // UPDATE 경우 dto 기본값을 설정하여 UPDATE 해줍니다.
        if(dto.getId() != null) {
            University updateDto = university.findById(dto.getId()).get();
            dto.setId(updateDto.getId());
            dto.setUniLike(updateDto.getUniLike());
            dto.setUniName(updateDto.getUniName());

            List<File> updateFile = updateDto.getFiles();

            // UPDATE 기존 file의 제거되는게 있는경우
            if(dto.getRemoveFiles().length > 0) {
                for(int i = 0; i < dto.getRemoveFiles().length; i++) {
                    updateFile = updateFileFilter(updateFile, dto, i);
                }
            }

            // UPDATE file 존재하는 경우와 아닌경우
            if(dto.getFileData() == null) {
                dto.setFileData(updateFile);
            } else {
                dto.getFileData().addAll(updateFile);
            }
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

        return findByPublicId(universityData.getId(), accountData);
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
    public List<CommentPublic> findByCommentList(Long id) {
        return university.findByCommentList(id);
    }

    @Override
    public void deleteData(Long id, Account accountData) {
        university.deleteData(id, accountData);
    }

    /**
    * Client 에서 기존 업로드된 파일의 제거된 {id}값을 기존 file와 비교하여 제거하는 메소드
    **/
    private List<File> updateFileFilter(List<File> result, UniversitySaveDTO dto, Integer i) {
        return result
                .stream()
                .filter(
                        f -> f.getId() != dto.getRemoveFiles()[i]).collect(Collectors.toList()
                );
    }
}
