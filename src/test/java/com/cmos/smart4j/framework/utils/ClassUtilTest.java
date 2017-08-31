package com.cmos.smart4j.framework.utils;

import org.junit.Assert;
import org.junit.Test;

public class ClassUtilTest {

    @Test
    public void getClassLoaderTest() {
        ClassLoader classLoader = ClassUtil.getClassLoader();
        Assert.assertNotNull(classLoader);
        System.out.println(classLoader.getClass());
        System.out.println(classLoader.getParent().getClass());
    }


}
