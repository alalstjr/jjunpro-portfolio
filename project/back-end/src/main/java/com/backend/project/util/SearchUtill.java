package com.backend.project.util;

import com.backend.project.domain.Account;
import com.backend.project.dto.SearchDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SearchUtill {

    public SearchDTO setSearchDTO(
            String keyword,
            String classification,
            int offsetCount,
            String ifCateA,
            String ifCateB,
            Account accountData
    ) throws IOException {
        // Search DTO 생성
        SearchDTO searchDTO = new SearchDTO();

        if (keyword != null) {
            // Param 값을 DTO 에 담아줍니다.
            // (관리하기 쉽게 DTO 한곳에 모아줍니다.)
            searchDTO.setKeyword(keyword);
            searchDTO.setClassification(classification);
            searchDTO.setOffsetCount(offsetCount);
            searchDTO.setAccount(accountData);
            searchDTO.setIfCateA(ifCateA);
            searchDTO.setIfCateB(ifCateB);
        }
        else {
            throw new IOException("검색에 필요한 정보를 전부 받지 못했습니다.");
        }

        return searchDTO;
    }
}
