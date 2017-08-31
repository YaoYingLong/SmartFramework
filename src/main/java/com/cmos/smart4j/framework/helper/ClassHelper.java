package com.cmos.smart4j.framework.helper;

import com.cmos.smart4j.framework.ConfigConstant;
import com.cmos.smart4j.framework.annotation.Controller;
import com.cmos.smart4j.framework.annotation.Service;
import com.cmos.smart4j.framework.utils.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * �����������
 */
public final class ClassHelper {

    /**
     * �����༯�ϣ����ڴ�������ص��ࣩ
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getProperty(ConfigConstant.APP_BASE_PACKAGE);
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * ��ȡӦ�ð����µ�������
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }

    /**
     * ��ȡӦ�ð��µ����е�Service��
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * ��ȡӦ�ð��µ����е�Controller��
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls : CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * ��ȡӦ�ð��µ����е�Bean��
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> beanClassSet = new HashSet<Class<?>>();
        beanClassSet.addAll(getServiceClassSet());
        beanClassSet.addAll(getControllerClassSet());
        return beanClassSet;
    }
}
