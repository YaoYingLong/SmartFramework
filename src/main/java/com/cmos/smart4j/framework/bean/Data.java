package com.cmos.smart4j.framework.bean;

/**
 * �������ݶ���
 * ��װ��һ��Object���͵�ģ�����ݣ�
 * ��ܻὫ�ö���д��HttpServletResponse�����У�
 * �Ӷ�ֱ������������
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
