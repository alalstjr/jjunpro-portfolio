package com.jjunpro.project.enums;

public enum DomainType implements EnumModel {

    ACCOUNT("account"),
    UNIVERSITY("university"),
    COMMENT("comment"),
    ALARM("alarm");

    public final String domainType;

    DomainType(String domainType) {
        this.domainType = domainType;
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return this.domainType;
    }
}
