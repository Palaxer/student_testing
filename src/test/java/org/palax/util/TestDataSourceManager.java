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
    private static final Logger logger = Logger.getLogger(DataSourceManager.class);
    private static final String DB_URL = "jdbc:mysql://localhost:3306/?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DB_CATALOG = "student_testing_db_test";

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            con.setCatalog(DB_CATALOG);
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
