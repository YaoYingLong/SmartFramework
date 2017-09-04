package com.cmos.smart4j.framework.bean;

import com.cmos.smart4j.framework.utils.CollectionUtil;
import org.smart4j.framework.util.CastUtil;

import java.util.Map;

public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name) {
        return CastUtil.castLong(this.paramMap.get(name));
    }

    public Map<String, Object> getMap() {
        return this.paramMap;
    }

    public boolean isEmpty(){
        return CollectionUtil.isEmpty(paramMap);
    }
}
