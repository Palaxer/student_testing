package org.palax.util;

import java.sql.Connection;

/**
 * The {@code DataSourceManager} interface which used to provide method to work with data source
 *
 * @author Taras Palashynskyy
 */
public interface DataSourceManager {

    Connection getConnection();

    void closeConnection(Connection con);
}
