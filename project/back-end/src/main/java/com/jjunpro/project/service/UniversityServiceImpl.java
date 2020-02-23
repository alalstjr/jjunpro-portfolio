package com.jjunpro.project.service;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.File;
import com.jjunpro.project.domain.Store;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.dto.SearchDTO;
import com.jjunpro.project.dto.StoreDTO;
import com.jjunpro.project.dto.UniversityDTO;
import com.jjunpro.project.dto.UpdateUniLikeDTO;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.repository.StoreRepository;
import com.jjunpro.project.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        if (dto.getId() != null) {
            /* { DATA DB } 값이 조회 */
            Optional<University> updateDto = universityRepository.findById(dto.getId());

            /* { DATA DB } 조회한 값을 DTO 값에 UPDATE 수정 */
            if (updateDto.isPresent()) {
                dto.setId(updateDto
                        .get()
                        .getId());
                dto.setUniLike(updateDto
                        .get()
                        .getUniLike());
                dto.setUniName(updateDto
                        .get()
                        .getUniName());
                dto.setComments(updateDto
                        .get()
                        .getComments());

                List<File> updateFile = updateDto
                        .get()
                        .getFiles();

                /* UPDATE 기존 file 에서 제거되는 file 이 있는경우 */
                if (dto.getRemoveFiles() != null && dto.getRemoveFiles().length > 0) {
                    for (int i = 0; i < dto.getRemoveFiles().length; i++) {
                        updateFile = updateFileFilter(
                                updateFile,
                                dto,
                                i
                        );
                    }
                }

                /* UPDATE file 존재하는 경우와 아닌경우 */
                if (dto.getFileData() == null) {
                    dto.setFileData(updateFile);
                }
                else {
                    dto
                            .getFileData()
                            .addAll(updateFile);
                }
            }
        }

        University universityData = universityRepository.save(dto.toEntity());

        return findByPublicId(
                universityData.getId(),
                dto.getAccount()
        );
    }

    @Override
    public UpdateUniLikeDTO updateUniversityLike(
            Long id,
            Account account
    ) {
        UpdateUniLikeDTO dto          = new UpdateUniLikeDTO();
        boolean          uniLikeState = false;

        /* University DB Data */
        Optional<University> universityData = universityRepository.findById(id);

        if (universityData.isPresent()) {
            /* 좋아요를 누른 유저가 universityData 내부에 존재하는지 확인합니다. */
            if (universityData
                    .get()
                    .getUniLike()
                    .contains(account)) {
                /* universityData 내부에 유저의 정보를 삭제합니다. false */
                universityData
                        .get()
                        .getUniLike()
                        .remove(account);
            }
            else {
                /* universityData 내부에 유정의 정보를 추가합니다. true */
                universityData
                        .get()
                        .getUniLike()
                        .add(account);
                uniLikeState = true;
            }

            /* Response DTO 설정 */
            University earlyUniversity = universityRepository.save(universityData.get());
            dto.setId(earlyUniversity.getId());
            dto.setUniLike(earlyUniversity
                    .getUniLike()
                    .size());
            dto.setUniLikeState(uniLikeState);
        }

        return dto;
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

    @Override
    public Optional<University> findById(Long id) {
        return universityRepository.findById(id);
    }

    /* Client 에서 기존 업로드된 파일의 제거된 {id} 값을 기존 file와 비교하여 제거하는 메소드 */
    private List<File> updateFileFilter(
            List<File> result,
            UniversityDTO dto,
            Integer i
    ) {
        return result
                .stream()
                .filter(f -> !f
                        .getId()
                        .equals(dto.getRemoveFiles()[i]))
                .collect(Collectors.toList());
    }

    @Override
    public List<UniversityPublic> findByUniversityListWhereSearchDto(SearchDTO searchDTO) {
        return universityRepository.findByUniversityListWhereSearchDto(searchDTO);
    }

    @Override
    public List<UniversityPublic> findByOrderByCreatedDateDesc(Account account) {
        return universityRepository.findByOrderByCreatedDateDesc(account);
    }

    @Override
    public Long findByIdUniCount(String uniName) {
        return universityRepository.findByIdUniCount(uniName);
    }

    @Override
    public void deleteData(
            Long id,
            Account accountData
    ) {
        universityRepository.deleteData(
                id,
                accountData
        );
    }
}
