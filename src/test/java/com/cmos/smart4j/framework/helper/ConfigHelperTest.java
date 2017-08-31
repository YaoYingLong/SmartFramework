package com.cmos.smart4j.framework.helper;

import com.cmos.smart4j.framework.ConfigConstant;
import org.junit.Assert;
import org.junit.Test;

public class ConfigHelperTest {

    @Test
    public void getPropertyTest() {
        String driver = ConfigHelper.getProperty(ConfigConstant.JDBC_DRIVER);
        Assert.assertEquals(driver, "com.mysql.jdbc.Driver");

        String url = ConfigHelper.getProperty(ConfigConstant.JDBC_URL);
        Assert.assertEquals(url, "jdbc:mysql://localhost:3306/smart_framework");

        String username = ConfigHelper.getProperty(ConfigConstant.JDBC_USERNAME);
        Assert.assertEquals(username, "root");

        String password = ConfigHelper.getProperty(ConfigConstant.JDBC_PASSWORD);
        Assert.assertEquals(password, "root");

        String base_package = ConfigHelper.getProperty(ConfigConstant.APP_BASE_PACKAGE);
        Assert.assertEquals(base_package, "com.cmos.smart4j");

        String jsp_path = ConfigHelper.getProperty(ConfigConstant.APP_JSP_PATH);
        Assert.assertEquals(jsp_path, "/view/");

        String asset_path = ConfigHelper.getProperty(ConfigConstant.APP_ASSET_PATH);
        Assert.assertEquals(asset_path, "/asset/");

        String NullTest = ConfigHelper.getProperty("KK");
        Assert.assertEquals(NullTest, "");

        String DefaultTest = ConfigHelper.getProperty("KK", "kk");
        Assert.assertEquals(DefaultTest, "kk");
    }

}
