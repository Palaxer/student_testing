package org.palax.dao.transaction;

import org.apache.log4j.Logger;
import org.palax.util.impl.DefaultDataSourceManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by palak on 15.02.2017.
 */
public class TransactionManager {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(TransactionManager.class);

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void begin() {
        close();

        connection = DefaultDataSourceManager.getInstance().getConnection();

        if(connection != null)
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                logger.error("Threw a SQLException, full stack trace follows:",e);
                close();
            }
    }

    public boolean commit() {
        if(connection != null) {
            try {
                connection.commit();
                return true;
            } catch (SQLException e) {
                logger.error("Threw a SQLException, full stack trace follows:", e);
                close();
            }
        }

        return false;
    }

    public boolean rollback() {
        if(connection != null) {
            try {
                connection.rollback();
                logger.debug("Statement was roll back");
                return true;
            } catch (SQLException e) {
                logger.error("Threw a SQLException, full stack trace follows:", e);
                close();
            }
        }

        return false;
    }

    public void close() {
        if(connection != null)
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Threw a SQLException, full stack trace follows:",e);
            }
    }
}
