package com.jjunpro.project.util;

import com.jjunpro.project.domain.QStore;
import com.jjunpro.project.domain.QUniversity;
import com.jjunpro.project.dto.SearchDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Component;

@Component
public class QueryDslUtil {

    private QUniversity qUniversity = QUniversity.university;
    private QStore      qStore      = QStore.store;

    /**
     * 출력 조건문을 주어서 검색대상을 탐색
     */
    public BooleanBuilder getSearchKeyword(SearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();
        switch (searchDTO.getCategory()) {
            case "username":
                return builder.and(qUniversity.account.username.eq(searchDTO.getKeyword()));

            case "nickname":
                return builder.and(qUniversity.account.nickname.eq(searchDTO.getKeyword()));

            case "uniLike":
                return builder.and(qUniversity.uniLike.any().username.eq(searchDTO.getKeyword()));

            case "uniName":
                return builder.and(qUniversity.uniName.contains(searchDTO.getKeyword()));

            case "stoId":
                return builder.and(qStore.stoName.contains(searchDTO.getKeyword()));

            case "uniTag":
                return builder.and(qUniversity.uniTag.contains(searchDTO.getKeyword()));

            case "userId":
                return builder.and(qUniversity.account.username.contains(searchDTO.getKeyword()));

            default:
                return builder.and(null);
        }
    }

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
