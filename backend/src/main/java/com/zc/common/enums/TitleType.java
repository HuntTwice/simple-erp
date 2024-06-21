package com.zc.common.enums;

public enum TitleType {
    USER("用户管理"),
    DEPARTMENT("部门管理"),
    CUSTOMER("客户管理"),
    SUPPLIER("供应商管理"),
    GOODS("库存管理"),
    STOCK("采购管理"),
    SALE("销售管理"),
    OTHER("其他");

    private String value;

    TitleType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
