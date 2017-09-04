package com.cmos.smart4j.framework.helper;

import com.cmos.smart4j.framework.annotation.Inject;
import com.cmos.smart4j.framework.utils.CollectionUtil;
import com.cmos.smart4j.framework.utils.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.ArrayUtil;

import java.lang.reflect.Field;
import java.util.Map;

public final class IocHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);

    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                Class<?> beanClass = beanEntry.getKey();
                LOGGER.debug("bean class is: " + beanClass.getName());
                Object beanInstance = beanEntry.getValue();
                LOGGER.debug("bean instance is: " + beanInstance.toString());
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    for (Field beanField : beanFields) {
                        LOGGER.debug("bean field is: " + beanField.getName());
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            LOGGER.debug("beanFieldClass is: " + beanFieldClass.getName());
                            LOGGER.debug("beanClass is equals beanFieldClass: " + beanClass.equals(beanFieldClass));
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            LOGGER.debug("beanFieldInstance is: " + beanFieldInstance.toString());
                            LOGGER.debug("beanInstance is equals beanFieldInstance: " + beanInstance.equals(beanFieldInstance));
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
