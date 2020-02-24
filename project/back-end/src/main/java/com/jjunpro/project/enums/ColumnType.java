package com.jjunpro.project.enums;

public enum ColumnType implements EnumModel {

    NICKNAME("nickname"),
    EMAIL("email"),
    USERNAME("username");

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
}
