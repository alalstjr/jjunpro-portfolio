package com.jjunpro.project.util;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.dto.UniversityDTO;
import com.jjunpro.project.projection.UniversityPublic;
import com.jjunpro.project.service.UniversityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniversityUtilTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UniversityService universityService;

    /* 임시 게시글을 하나 생성합니다. */
    public UniversityPublic getUniversityPublic(AccountContext userDetails) {
        UniversityDTO dto = new UniversityDTO();
        dto.setAccount(userDetails.getAccount());
        dto.setUniSubject("subject");
        dto.setUniContent("content");
        dto.setUniName("uniName");
        dto.setIp("0.0.0.0");
        dto.setStoId("1");
        dto.setStoAddress("addr");
        dto.setStoName("name");
        dto.setStoUrl("url");

        UniversityPublic university = universityService.createUniversity(dto);
        log.info("임시 게시글 university nickname -> " + university.getAccount_nickname());
        log.info("임시 게시글 university id -> " + university.getId());
        return university;
    }
}
