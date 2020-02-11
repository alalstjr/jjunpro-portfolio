package com.backend.project.util;

import com.backend.project.domain.QStore;
import com.backend.project.domain.QUniversity;
import com.backend.project.dto.SearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Component;

@Component
public class RepositoryUtill {

    private QUniversity qUniversity = QUniversity.university;
    private QStore      qStore      = QStore.store;

    /**
     * 출력 조건문을 주어서 검색대상을 탐색
     */
    public BooleanBuilder getSearchCate(SearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();
        switch (searchDTO.getIfCateB()) {
            case "posts":
                return builder.and(qUniversity.files.isEmpty());

            case "photo":
                return builder.and(qUniversity.files.isNotEmpty());

            default:
                return builder.and(null);
        }
    }

    /**
     * 분류를 기준으로 검색대상을 탐색
     */
    public BooleanBuilder getSearchClassification(SearchDTO searchDTO) {
        // 검색 대상 분류 동적 조건
        BooleanBuilder builder = new BooleanBuilder();

        // keyword가 없는경우 모두검색
        if (searchDTO
                .getKeyword()
                .equals("all")) {
            return builder.and(null);
        }

        switch (searchDTO.getClassification()) {
            case "uniName":
                return builder.and(qUniversity.uniName.contains(searchDTO.getKeyword()));

            case "stoId":
                return builder.and(qStore.stoName.contains(searchDTO.getKeyword()));

            case "uniTag":
                return builder.and(qUniversity.uniTag.contains(searchDTO.getKeyword()));

            case "userId":
                return builder.and(qUniversity.account.userId.contains(searchDTO.getKeyword()));

            default:
                return builder.and(null);
        }
    }

    /**
     * 특정 기준의 조건으로 정렬하여 탐색
     */
    public OrderSpecifier getSearchOrderBy(SearchDTO searchDTO) {
        OrderSpecifier orderByData;

        // 검색 대상 정렬 분류 동적 조건
        switch (searchDTO.getIfCateA()) {
            case "like":
                return orderByData = new OrderSpecifier(
                        Order.DESC,
                        qUniversity.uniLike.size()
                );

            case "comment":
                return orderByData = new OrderSpecifier(
                        Order.DESC,
                        qUniversity.comments.size()
                );

            default:
                return orderByData = new OrderSpecifier(
                        Order.DESC,
                        qUniversity.modifiedDate
                );
        }
    }
}
