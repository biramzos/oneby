package com.web.oneby.commons.Enums;

public enum ProductType {
    BOOKS("books");

    private String tableName;

    ProductType (String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
