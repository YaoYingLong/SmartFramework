package com.cmos.smart4j.framework.helper;

import com.cmos.smart4j.framework.ConfigConstant;
import org.smart4j.framework.util.PropsUtil;

import java.util.Properties;

/**
 * 属性文件助手类
 */
public final class ConfigHelper {

    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getProperty(String propertyName) {
        return PropsUtil.getString(CONFIG_PROPS, propertyName);
    }

    public static String getProperty(String propertyName, String defaultValue) {
        return PropsUtil.getString(CONFIG_PROPS, propertyName, defaultValue);
    }
}
