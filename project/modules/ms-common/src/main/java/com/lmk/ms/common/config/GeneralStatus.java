package com.lmk.ms.common.config;

/**
 * 通用的状态标识
 * @author laomake@hotmail.com
 * @version 1.0
 * @date 2021/10/28
 */
public enum GeneralStatus {
    DELETE(-1),
    DISABLE(0),
    ENABLE(1);

    private int value;

    GeneralStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
