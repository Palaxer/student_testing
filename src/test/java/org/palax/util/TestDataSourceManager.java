package org.palax.util;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The {@code DefaultDataSourceManager} class it a basic implementation of {@link DataSourceManager} interface
 *
 * @author Taras Palashynskyy
 */

public class TestDataSourceManager implements DataSourceManager {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DataSourceManager.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?useSSL=false", "root", "root");
            con.setCatalog("student_testing_db_test");
            return con;
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeConnection(Connection con) {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }
    }
}
