package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.RoleDao;
import org.palax.entity.Role;
import org.palax.util.DataSourceManager;
import org.palax.util.impl.DefaultDataSourceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLRoleDao} singleton class implements {@link RoleDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLRoleDao implements RoleDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLRoleDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static volatile RoleDao roleDao;
    private DataSourceManager dataSourceManager;
    /**Values which store column name for {@link ResultSet} */
    private static final String ROLE_ID = "ROLE_ID";
    private static final String ROLE_TYPE = "ROLE_TYPE";

    private MySQLRoleDao() {
        dataSourceManager = DefaultDataSourceManager.getInstance();
    }

    /**
     * Constructor is used to avoid use PowerMock in testing
     * @param dataSourceManager mock {@link DataSourceManager} object
     */
    public MySQLRoleDao(DataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    /**
     * Always return same {@link MySQLRoleDao} instance
     *
     * @return always return same {@link MySQLRoleDao} instance
     */
    public static RoleDao getInstance() {
        RoleDao localInstance = roleDao;
        if(localInstance == null) {
            synchronized (MySQLRoleDao.class) {
                localInstance = roleDao;
                if(localInstance == null) {
                    roleDao = new MySQLRoleDao();
                    logger.debug("Create first MySQLRoleDao instance");
                }
            }
        }
        logger.debug("Return MySQLRoleDao instance");
        return roleDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> findAll() {
        String SQL = "SELECT * FROM role";

        ArrayList<Role> roleList = new ArrayList<>();

        logger.debug("Try get all ROLE");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                Role role = Role.valueOf(rs.getString(ROLE_TYPE));
                role.setId(rs.getLong(ROLE_ID));
                roleList.add(role);
            }
            logger.debug("Get all ROLE successfully " + roleList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return roleList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role find(Role role) {
        String SQL = "SELECT * FROM role WHERE ROLE_TYPE=?";
        Role resultRole = null;

        logger.debug("Try get ROLE by TYPE " + role.name());

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setString(1, role.name());
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                resultRole = Role.valueOf(rs.getString(ROLE_TYPE));
                resultRole.setId(rs.getLong(ROLE_ID));
            }
            logger.debug("Get ROLE by TYPE successfully " + role);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return resultRole;
    }
}
