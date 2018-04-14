package com.gdut.pandora.common;

import lombok.Getter;

/**
 * Created by buzheng on 18/4/14.
 */
public enum  ReturnTypeEnum {

    BOOLEAN_TYPE("BOOLEAN"),
    DEFAULT("LIST");

    @Getter
    private String type;

    ReturnTypeEnum(String type) {
        this.type = type;
    }
}
