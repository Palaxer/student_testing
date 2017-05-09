package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.UserDao;
import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.util.DataSourceManager;
import org.palax.util.impl.DefaultDataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLUserDao} singleton class implements {@link UserDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLUserDao implements UserDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLUserDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static volatile UserDao userDao;
    private DataSourceManager dataSourceManager;
    /**Values which store column name for {@link ResultSet} */
    private static final String USER_ID = "USER_ID";
    private static final String LOGIN = "LOGIN";
    private static final String PASSWORD = "PASSWD";
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final String ROLE_ID = "ROLE_ID";
    private static final String ROLE_TYPE = "ROLE_TYPE";

    private MySQLUserDao() {
        dataSourceManager = DefaultDataSourceManager.getInstance();
    }

    public MySQLUserDao(DataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    /**
     * Always return same {@link MySQLUserDao} instance
     *
     * @return always return same {@link MySQLUserDao} instance
     */
    public static UserDao getInstance() {
        UserDao localInstance = userDao;
        if(localInstance == null) {
            synchronized (MySQLUserDao.class) {
                localInstance = userDao;
                if(localInstance == null) {
                    userDao = new MySQLUserDao();
                    logger.debug("Create first MySQLUserDao instance");
                }
            }
        }
        logger.debug("Return MySQLUserDao instance");
        return userDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll(int offSet, int numberOfElement) {
        String SQL = "SELECT * FROM user A LEFT JOIN role B ON (A.ROLE_ID=B.ROLE_ID) " +
                "LIMIT " + offSet + ", " + numberOfElement;

        List<User> userList = new ArrayList<>();

        logger.debug("Try get all USER with LIMIT");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                userList.add(parseResultSet(rs));
            }

            logger.debug("Get all USER with LIMIT successfully " + userList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return userList;
    }

    @Override
    public List<User> findAllByRole(Role role, int offSet, int numberOfElement) {
        String SQL = "SELECT * FROM user A LEFT JOIN role B ON (A.ROLE_ID=B.ROLE_ID) WHERE B.ROLE_TYPE=? " +
                "LIMIT " + offSet + ", " + numberOfElement;

        List<User> userList = new ArrayList<>();

        logger.debug("Try get all USER by ROLE with LIMIT");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setString(1, role.name());
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                userList.add(parseResultSet(rs));
            }

            logger.debug("Get all USER by ROLE with LIMIT successfully " + userList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return userList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        String SQL = "SELECT COUNT(*) FROM user";

        long count = 0;

        logger.debug("Try count all USER");

        try(Connection con = dataSourceManager.getConnection();
            Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SQL);

            while(rs.next()) {
                count = rs.getLong(1);
            }
            logger.debug("Count all USER successfully" + count);

        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return count;
    }

    @Override
    public long countByRole(Role role) {
        String SQL = "SELECT COUNT(*) FROM user A LEFT JOIN role B ON (A.ROLE_ID=B.ROLE_ID) " +
                "WHERE B.ROLE_TYPE='" + role.name() + "'";

        long count = 0;

        logger.debug("Try count all USER by ROLE");

        try(Connection con = dataSourceManager.getConnection();
            Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SQL);

            while(rs.next()) {
                count = rs.getLong(1);
            }
            logger.debug("Count all USER by ROLE successfully" + count);

        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(Long id) {
        String SQL = "SELECT * FROM user A LEFT JOIN role B ON (A.ROLE_ID=B.ROLE_ID) WHERE USER_ID=?";

        User user = null;

        logger.debug("Try get USER by ID " + id);

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                user = parseResultSet(rs);
            }

            logger.debug("Get USER by ID successfully " + user);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByLogin(String login) {
        String SQL = "SELECT * FROM user A LEFT JOIN role B ON (A.ROLE_ID=B.ROLE_ID) WHERE LOGIN=?";

        User user = null;

        logger.debug("Try get USER by NAME " + login);

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setString(1, login);
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                user = parseResultSet(rs);
            }

            logger.debug("Get USER by NAME successfully " + user);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(User user) {
        Connection con = dataSourceManager.getConnection();
        boolean result = update(user, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(User user, TransactionManager tx) {
        return update(user, tx.getConnection());
    }

    boolean update(User user, Connection con) {
        String SQL = "UPDATE user SET LOGIN=?, PASSWD=?, NAME=?, SURNAME=?, ROLE_ID=? WHERE USER_ID=?";

        logger.debug("Try update USER " + user);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setString(1, user.getLogin());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getName());
            stm.setString(4, user.getSurname());
            stm.setObject(5, user.getRole() == null ? null : user.getRole().getId());
            stm.setObject(6, user.getId());

            if(stm.executeUpdate() > 0) {
                logger.debug("USER update successfully " + user);
                return true;
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.info("Threw a SQLIntegrityConstraintViolationException, try update new USER " + user);
            logger.debug("Threw a SQLIntegrityConstraintViolationException, full stack trace follows:",e);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("USER update fail " + user);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(User user) {
        Connection con = dataSourceManager.getConnection();
        boolean result = insert(user, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(User user, TransactionManager tx) {
        return insert(user, tx.getConnection());
    }

    boolean insert(User user, Connection con) {
        String SQL = "INSERT INTO user (LOGIN, PASSWD, NAME, SURNAME, ROLE_ID) VALUES (?, ?, ?, ?, ?)";

        logger.debug("Try insert USER " + user);

        try(PreparedStatement stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, user.getLogin());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getName());
            stm.setString(4, user.getSurname());
            stm.setObject(5, user.getRole() == null ? null : user.getRole().getId());

            if(stm.executeUpdate() > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if(rs.next()) {
                    user.setId(rs.getLong(1));
                    logger.debug("USER insert successfully " + user);
                    return true;
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            logger.info("Threw a SQLIntegrityConstraintViolationException, try create new USER " + user);
            logger.debug("Threw a SQLIntegrityConstraintViolationException, full stack trace follows:",e);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("USER insert fail " + user);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(User user) {
        Connection con = dataSourceManager.getConnection();
        boolean result = delete(user, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(User user, TransactionManager tx) {
        return delete(user, tx.getConnection());
    }

    boolean delete(User user, Connection con) {
        String SQL = "DELETE FROM user WHERE USER_ID=?";

        logger.debug("Try delete USER " + user);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, user.getId());
            if(stm.executeUpdate() > 0) {
                logger.debug("USER delete successfully " + user);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("USER delete fail " + user);
        return false;
    }

    /**
     * Method parses the {@link ResultSet} and returns a {@link User}
     *
     * @param rs {@link ResultSet} which be parsed
     * @return parse {@code rs} and return a {@link User}
     */
    private User parseResultSet(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getObject(USER_ID, Long.class));
        user.setLogin(rs.getString(LOGIN));
        user.setPassword(rs.getString(PASSWORD));
        user.setName(rs.getString(NAME));
        user.setSurname(rs.getString(SURNAME));

        if(rs.getLong(ROLE_ID) != 0) {
            Role role = Role.valueOf(rs.getString(ROLE_TYPE));
            role.setId(rs.getLong(ROLE_ID));
            user.setRole(role);
        }

        return user;
    }
}
