package com.jjunpro.project.enums;

public enum AlarmType implements EnumModel {

    NOTICE("NOTICE"),
    COMMENT("COMMENT");

    private final String alarmType;

    AlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return this.alarmType;
    }
}
