package com.jjunpro.project.service;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.University;
import com.jjunpro.project.dto.UniversityDTO;
import com.jjunpro.project.projection.UniversityPublic;

/**
 * UniversityPublic saveOrUpdate() :
 * UniversitySaveDTO 정보를 DB 저장합니다.
 * 전체적인 정보를 저장할때 사용합니다.
 * <p>
 * University saveOrUpdate() :
 * University 일부 정보를 DB 에 수정합니다.
 * 단일 부분을 수정할때 사용합니다.
 */
public interface UniversityService {

    UniversityPublic createUniversity(UniversityDTO dto);

    UniversityPublic updateUniversity(UniversityDTO dto);

    UniversityPublic findByPublicId(Long id, Account account);
}
