package com.cmos.smart4j.framework.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * ���鹤����
 */
public final class ArrayUtil {

    public static boolean isNotEmpty(Object[] array) {
        return !ArrayUtils.isEmpty(array);
    }

    public static boolean isEmpty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }
}
