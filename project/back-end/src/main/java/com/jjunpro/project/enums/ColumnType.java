package com.jjunpro.project.enums;

import java.util.Arrays;

public enum ColumnType implements EnumModel {

    NULL("null"),
    NICKNAME("nickname"),
    EMAIL("email"),
    USERNAME("username"),
    UNILIKE("uniLike"),
    UNINAME("uniName"),
    STOID("stoId"),
    UNITAG("uniTag"),
    POSTS("posts"),
    PHOTO("photo"),
    COMMENT("comment");

    public final String columnType;

    ColumnType(String columnType) {
        this.columnType = columnType;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return this.columnType;
    }

    /**
     * String -> ColumnType
     *
     * 전달받은 String 형을 ColumnType 열거형의 값에 동일한 값이 존재하면
     * 문자형을 ColumnType 타입으로 바꿔서 반환하는 메소드
     */
    public static ColumnType getColumnType(String value) {
        for (int i = 0; i < values().length; i++) {
            if (values()[i]
                    .getValue()
                    .equals(value)) {
                return values()[i];
            }
        }

        return NULL;
    }
}
