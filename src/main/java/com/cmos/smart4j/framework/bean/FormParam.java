package com.cmos.smart4j.framework.bean;

public class FormParam {

    private String fieldName;
    private Object fieldVlaue;

    public FormParam(String fieldName, Object fieldVlaue) {
        this.fieldName = fieldName;
        this.fieldVlaue = fieldVlaue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldVlaue() {
        return fieldVlaue;
    }
}
