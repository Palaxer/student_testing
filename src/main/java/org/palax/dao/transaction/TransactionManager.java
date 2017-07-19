package org.palax.dao.transaction;

import org.apache.log4j.Logger;
import org.palax.util.impl.DefaultDataSourceManager;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static final Logger logger = Logger.getLogger(TransactionManager.class);

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void begin() {
        close();

        connection = DefaultDataSourceManager.getInstance().getConnection();

        if(connection != null)
            beginTransaction();
    }

    public boolean commit() {
        if(connection != null)
            return commitTransaction();

        return false;
    }

    public boolean rollback() {
        if(connection != null)
            return rollbackTransaction();

        return false;
    }

    public void close() {
        if(connection != null)
            closeTransaction();
    }

    private void beginTransaction() {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
            close();
        }
    }

    private boolean commitTransaction() {
        try {
            connection.commit();
            return true;
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
            close();
        }
        return false;
    }

    private boolean rollbackTransaction() {
        try {
            connection.rollback();
            logger.debug("Statement was roll back");
            return true;
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
            close();
        }
        return false;
    }

    private void closeTransaction() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }
    }
}
