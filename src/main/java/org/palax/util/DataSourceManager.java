package org.palax.util;

import java.sql.Connection;

/**
 * The {@code DataSourceManager} interface which used to provide method to work with data source
 *
 * @author Taras Palashynskyy
 */
public interface DataSourceManager {

    /**
     * Method use to get {@link Connection} from {@code dataSource} connection pool
     *
     * @return {@link Connection} which comes from connection pool
     */
    Connection getConnection();

    /**
     * Method which used to close {@link Connection}
     *
     * @param con {@link Connection} to be closed
     */
    void closeConnection(Connection con);
}
