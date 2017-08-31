package com.cmos.smart4j.framework.helper;

import com.cmos.smart4j.framework.annotation.Inject;
import com.cmos.smart4j.framework.utils.CollectionUtil;
import com.cmos.smart4j.framework.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.ArrayUtil;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * ����ע������
 */
public final class IocHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);

    static {
        // ��ȡ���е�Bean����Beanʵ��֮���ӳ���ϵ����� Bean Map��
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            // ���� Bean Map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                //��BeanMap�л�ȡBean����Beanʵ��
                Class<?> beanClass = beanEntry.getKey();
                LOGGER.debug("bean class is: " + beanClass.getName());
                Object beanInstance = beanEntry.getValue();
                LOGGER.debug("bean instance is: " + beanInstance.toString());
                // ��ȡBean�ඨ������г�Ա��������� Bean Field��
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    // ���� Bean Field
                    for (Field beanField : beanFields) {
                        LOGGER.debug("bean field is: " + beanField.getName());
                        // �жϵ�ǰBean Field�Ƿ����Injectע��
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // ��Bean Map�л�ȡbean Field��Ӧ��ʵ��
                            Class<?> beanFieldClass = beanField.getType();
                            LOGGER.debug("beanFieldClass is: " + beanFieldClass.getName());
                            LOGGER.debug("beanClass is equals beanFieldClass: " + beanClass.equals(beanFieldClass));
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            LOGGER.debug("beanFieldInstance is: " + beanFieldInstance.toString());
                            LOGGER.debug("beanInstance is equals beanFieldInstance: " + beanInstance.equals(beanFieldInstance));
                            if (beanFieldInstance != null) {
                                // ͨ�������ʼ�� BeanField ��ֵ
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
