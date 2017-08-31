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
 * 依赖注入助手
 */
public final class IocHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);

    static {
        // 获取所有的Bean类与Bean实例之间的映射关系（简称 Bean Map）
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (CollectionUtil.isNotEmpty(beanMap)) {
            // 遍历 Bean Map
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                //从BeanMap中获取Bean类与Bean实例
                Class<?> beanClass = beanEntry.getKey();
                LOGGER.debug("bean class is: " + beanClass.getName());
                Object beanInstance = beanEntry.getValue();
                LOGGER.debug("bean instance is: " + beanInstance.toString());
                // 获取Bean类定义的所有成员变量（简称 Bean Field）
                Field[] beanFields = beanClass.getDeclaredFields();
                if (ArrayUtil.isNotEmpty(beanFields)) {
                    // 遍历 Bean Field
                    for (Field beanField : beanFields) {
                        LOGGER.debug("bean field is: " + beanField.getName());
                        // 判断当前Bean Field是否带有Inject注解
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            // 在Bean Map中获取bean Field对应的实例
                            Class<?> beanFieldClass = beanField.getType();
                            LOGGER.debug("beanFieldClass is: " + beanFieldClass.getName());
                            LOGGER.debug("beanClass is equals beanFieldClass: " + beanClass.equals(beanFieldClass));
                            Object beanFieldInstance = beanMap.get(beanFieldClass);
                            LOGGER.debug("beanFieldInstance is: " + beanFieldInstance.toString());
                            LOGGER.debug("beanInstance is equals beanFieldInstance: " + beanInstance.equals(beanFieldInstance));
                            if (beanFieldInstance != null) {
                                // 通过反射初始化 BeanField 的值
                                ReflectionUtil.setField(beanInstance, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
