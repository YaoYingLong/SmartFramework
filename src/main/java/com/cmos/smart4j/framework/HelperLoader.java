package com.cmos.smart4j.framework;

import com.cmos.smart4j.framework.helper.BeanHelper;
import com.cmos.smart4j.framework.helper.ClassHelper;
import com.cmos.smart4j.framework.helper.ControllerHelper;
import com.cmos.smart4j.framework.helper.IocHelper;
import com.cmos.smart4j.framework.utils.ClassUtil;

/**
 * 加载相应的Helper类
 */
public final class HelperLoader {

    public static void init() {
        Class<?>[] classList = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }

}
