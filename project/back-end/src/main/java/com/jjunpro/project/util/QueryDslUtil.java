package com.jjunpro.project.util;

import com.jjunpro.project.domain.QStore;
import com.jjunpro.project.domain.QUniversity;
import com.jjunpro.project.dto.SearchDTO;
import com.jjunpro.project.enums.ColumnType;
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

        ColumnType columnType = ColumnType.getColumnType(searchDTO.getCategory());

        switch (columnType) {
            case USERNAME:
                return builder.and(qUniversity.account.username.eq(searchDTO.getKeyword()));

            case NICKNAME:
                return builder.and(qUniversity.account.nickname.eq(searchDTO.getKeyword()));

            case UNILIKE:
                return builder.and(qUniversity.uniLike.any().username.eq(searchDTO.getKeyword()));

            case UNINAME:
                return builder.and(qUniversity.uniName.contains(searchDTO.getKeyword()));

            case STOID:
                return builder.and(qStore.stoName.contains(searchDTO.getKeyword()));

            case UNITAG:
                return builder.and(qUniversity.uniTag.contains(searchDTO.getKeyword()));

            default:
                return builder.and(null);
        }
    }

    /**
     * 출력 조건문을 주어서 검색대상을 탐색
     */
    public BooleanBuilder getSearchCate(SearchDTO searchDTO) {
        BooleanBuilder builder = new BooleanBuilder();
        ColumnType columnType = ColumnType.getColumnType(searchDTO.getIfCateB());

        switch (columnType) {
            case POSTS:
                return builder.and(qUniversity.files.isEmpty());

            case PHOTO:
                return builder.and(qUniversity.files.isNotEmpty());

            default:
                return builder.and(null);
        }
    }

    /**
     * 특정 기준의 조건으로 정렬하여 탐색
     */
    public OrderSpecifier getSearchOrderBy(SearchDTO searchDTO) {
        ColumnType columnType = ColumnType.getColumnType(searchDTO.getIfCateA());

        // 검색 대상 정렬 분류 동적 조건
        switch (columnType) {
            case UNILIKE:
                return new OrderSpecifier(
                        Order.DESC,
                        qUniversity.uniLike.size()
                );

            case COMMENT:
                return new OrderSpecifier(
                        Order.DESC,
                        qUniversity.comments.size()
                );

            default:
                return new OrderSpecifier(
                        Order.DESC,
                        qUniversity.modifiedDate
                );
        }
    }
}
