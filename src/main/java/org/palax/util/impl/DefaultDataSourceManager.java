package org.palax.util.impl;

import org.apache.log4j.Logger;
import org.palax.util.DataSourceManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * The {@code DefaultDataSourceManager} class it a basic implementation of {@link DataSourceManager} interface
 *
 * @author Taras Palashynskyy
 */

public class DefaultDataSourceManager implements DataSourceManager {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DataSourceManager.class);
    /**Singleton object which is returned when you try to create a new instance */
    private static volatile DataSourceManager dataSourceManager;
    /**Data source object which use to get db connection. */
    private static DataSource ds;

    /**
     * Initialization data source object if the @{code dataSource} is not initialized
     * is stored @{code null} value
     */
    private DefaultDataSourceManager() {
        try {
            logger.debug("Try to get connection pool");
            Context initialContext = new InitialContext();
            Context envContext = (Context) initialContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/student-testing-db");
            if (ds != null)
                logger.debug("Connection pool is successfully received");
            else
                logger.error("Connection pool is not received");
        } catch (NamingException e) {
            logger.error("Threw a NamingException, full stack trace follows:",e);
        }
    }

    /**
     * Always return same {@link DefaultDataSourceManager} instance
     *
     * @return always return same {@link DefaultDataSourceManager} instance
     */
    public static DataSourceManager getInstance() {
        DataSourceManager localInstance = dataSourceManager;
        if(localInstance == null) {
            synchronized (DefaultDataSourceManager.class) {
                localInstance = dataSourceManager;
                if(localInstance == null) {
                    dataSourceManager = new DefaultDataSourceManager();
                    logger.debug("Create first DefaultDataSourceManager instance");
                }
            }
        }
        logger.debug("Return DefaultDataSourceManager instance");
        return dataSourceManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() {
        Connection connection = null;

        try {
            logger.debug("Try get connection");
            connection = ds.getConnection();
            if (connection != null)
                logger.debug("Connection is successfully received");
            else
                logger.error("Connection is not received");
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return connection;
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
