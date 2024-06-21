package com.zc.common.enums;

public enum OperationType {

    UPDATE("修改"),
    INSERT("新增"),
    DELETE("删除"),

    DELETE_BATCH("批量删除"),

    LOGIN("登录"),

    REGISTER("注册"),
    BACK("退货"),
    SALE("销售"),
    STOCK("采购"),
    OTHER("其他");



    private String value;

    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
