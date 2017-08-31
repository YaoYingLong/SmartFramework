package com.cmos.smart4j.framework.bean;

/**
 * 返回数据对象
 * 封装了一个Object类型的模型数据，
 * 框架会将该对象写入HttpServletResponse对象中，
 * 从而直接输出到浏览器
 */
public class Data {

    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
