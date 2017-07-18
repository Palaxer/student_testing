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
    private static final Logger logger = Logger.getLogger(DataSourceManager.class);
    private static final String DATA_SOURCE_JNDI_NAME = "jdbc/student-testing-db";
    private static final String ENVIRONMENT_JNDI_NAME = "java:/comp/env";

    private static volatile DataSourceManager dataSourceManager;
    private static DataSource ds;

    private DefaultDataSourceManager() {
        lookupDataSource();
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
            connection = tryGetConnection();
            checkConnection(connection);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return connection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void closeConnection(Connection connection) {
        try {
            tryColseConnection(connection);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }
    }

    private void lookupDataSource() {
        try {
            tryLookupDataSource();
            checkDataSourceLookup();
        } catch (NamingException e) {
            logger.error("Threw a NamingException, full stack trace follows:",e);
        }
    }

    private void tryLookupDataSource() throws NamingException {
        logger.debug("Try to get connection pool");
        Context initialContext = new InitialContext();
        Context envContext = (Context) initialContext.lookup(ENVIRONMENT_JNDI_NAME);
        ds = (DataSource) envContext.lookup(DATA_SOURCE_JNDI_NAME);
    }

    private void checkDataSourceLookup() {
        if (ds != null)
            logger.debug("Connection pool is successfully received");
        else
            logger.error("Connection pool is not received");
    }

    private Connection tryGetConnection() throws SQLException {
        logger.debug("Try get connection");
        return ds.getConnection();
    }

    private void checkConnection(Connection connection) {
        if (connection != null)
            logger.debug("Connection is successfully received");
        else
            logger.error("Connection is not received");
    }

    private void tryColseConnection(Connection connection) throws SQLException {
        if (connection != null)
            connection.close();
    }
}
