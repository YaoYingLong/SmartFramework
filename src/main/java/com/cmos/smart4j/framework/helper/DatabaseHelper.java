package com.cmos.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

import static org.smart4j.framework.dao.DatabaseHelper.getConnection;

public final class DatabaseHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class);

    private static ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();

    public static void beginTransaction() {
        Connection conn = getConnection();
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("begin transaction failure", e);
            throw new RuntimeException(e);
        }
    }

    public static void commitTransaction() {
        Connection conn = getConnection();
        try {
            conn.commit();
            conn.close();
        } catch (SQLException e) {
            LOGGER.error("commit transaction failure", e);
            throw new RuntimeException(e);
        } finally {
            CONNECTION_HOLDER.remove();
        }
    }

    public static void rollbackTransaction() {
        Connection conn = getConnection();
        try {
            conn.rollback();
            conn.close();
        } catch (SQLException e) {
            LOGGER.error("rollback transaction failure", e);
            throw new RuntimeException(e);
        } finally {
            CONNECTION_HOLDER.remove();
        }
    }

}
