package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.TestDao;
import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Category;
import org.palax.entity.Test;
import org.palax.entity.User;
import org.palax.util.DataSourceManager;
import org.palax.util.impl.DefaultDataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLTestDao} singleton class implements {@link TestDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLTestDao implements TestDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLTestDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static volatile TestDao testDao;
    private DataSourceManager dataSourceManager;
    /**Values which store column name for {@link ResultSet} */
    private static final String TEST_ID = "TEST_ID";
    private static final String TEST_NAME = "TEST_NAME";
    private static final String DESCRIPTION = "DESC";
    private static final String PASSED_SCORE = "PASSED_SCORE";
    private static final String PASSED_TIME = "PASSED_TIME";
    private static final String ACTIVE = "ACTIVE";
    private static final String CATEGORY_ID = "CATEGORY_ID";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String USER_ID = "USER_ID";
    private static final String LOGIN = "LOGIN";
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";

    private MySQLTestDao() {
        dataSourceManager = DefaultDataSourceManager.getInstance();
    }

    /**
     * Constructor is used to avoid use PowerMock in testing
     * @param dataSourceManager mock {@link DataSourceManager} object
     */
    public MySQLTestDao(DataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    /**
     * Always return same {@link MySQLTestDao} instance
     *
     * @return always return same {@link MySQLTestDao} instance
     */
    public static TestDao getInstance() {
        TestDao localInstance = testDao;
        if(localInstance == null) {
            synchronized (MySQLTestDao.class) {
                localInstance = testDao;
                if(localInstance == null) {
                    testDao = new MySQLTestDao();
                    logger.debug("Create first MySQLTestDao instance");
                }
            }
        }
        logger.debug("Return MySQLTestDao instance");
        return testDao;
    }

    @Override
    public List<Test> findAllByActive(Boolean active, int offSet, int numberOfElement) {
        String SQL = "SELECT * FROM test A LEFT JOIN user B ON (A.OWNER_ID=B.USER_ID)" +
                "LEFT JOIN category C ON (A.CATEGORY_ID=C.CATEGORY_ID) WHERE ACTIVE=? " +
                "LIMIT " + offSet + ", " + numberOfElement;

        ArrayList<Test> testList = new ArrayList<>();

        logger.debug("Try get all TEST with LIMIT");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setBoolean(1, true);
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                testList.add(parseResultSet(rs));
            }
            logger.debug("Get all TEST with LIMIT successfully " + testList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return testList;
    }

    @Override
    public List<Test> findAllByCategoryAndActive(Category category, boolean active, int offSet, int numberOfElement) {
        String SQL = "SELECT * FROM test A LEFT JOIN user B ON (A.OWNER_ID=B.USER_ID)" +
                "LEFT JOIN category C ON (A.CATEGORY_ID=C.CATEGORY_ID) WHERE A.CATEGORY_ID=? " +
                "AND ACTIVE=? LIMIT " + offSet + ", " + numberOfElement;

        ArrayList<Test> testList = new ArrayList<>();

        logger.debug("Try get all TEST by CATEGORY");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, category.getId());
            stm.setBoolean(2, active);
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                testList.add(parseResultSet(rs));
            }
            logger.debug("Get all TEST by CATEGORY successfully " + testList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return testList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Test> findAllByTutor(User tutor, int offSet, int numberOfElement) {
        String SQL = "SELECT * FROM test A LEFT JOIN user B ON (A.OWNER_ID=B.USER_ID)" +
                "LEFT JOIN category C ON (A.CATEGORY_ID=C.CATEGORY_ID) WHERE OWNER_ID=? " +
                "LIMIT " + offSet + ", " + numberOfElement;

        ArrayList<Test> testList = new ArrayList<>();

        logger.debug("Try get all TEST by USER");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, tutor.getId());
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                testList.add(parseResultSet(rs));
            }
            logger.debug("Get all TEST by USER successfully " + testList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return testList;
    }

    @Override
    public long countByActive(Boolean active) {
        String SQL = "SELECT COUNT(*) FROM test WHERE ACTIVE=" + active;

        long count = 0;

        logger.debug("Try count all TEST");

        try(Connection con = dataSourceManager.getConnection();
            Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SQL);

            while(rs.next()) {
                count = rs.getLong(1);
            }
            logger.debug("Count all TEST successfully" + count);

        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return count;
    }

    @Override
    public long countByTutor(User tutor) {
        String SQL = "SELECT COUNT(*) FROM test WHERE OWNER_ID=" + tutor.getId();

        long count = 0;

        logger.debug("Try count all TEST by USER ");

        try(Connection con = dataSourceManager.getConnection();
            Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SQL);

            while(rs.next()) {
                count = rs.getLong(1);
            }
            logger.debug("Count all TEST by USER  successfully" + count);

        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return count;
    }

    @Override
    public long countByCategoryAndActive(Category category, boolean active) {
        String SQL = "SELECT COUNT(*) FROM test WHERE CATEGORY_ID=" + category.getId() +
                " AND ACTIVE=" + active;

        long count = 0;

        logger.debug("Try count all TEST by CATEGORY");

        try(Connection con = dataSourceManager.getConnection();
            Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SQL);

            while(rs.next()) {
                count = rs.getLong(1);
            }
            logger.debug("Count all TEST by CATEGORY  successfully" + count);

        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Test findById(Long id) {
        String SQL = "SELECT * FROM test A LEFT JOIN user B ON (A.OWNER_ID=B.USER_ID)" +
                "LEFT JOIN category C ON (A.CATEGORY_ID=C.CATEGORY_ID) WHERE TEST_ID=?";
        Test test = null;

        logger.debug("Try get TEST by ID " + id);

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                test = parseResultSet(rs);
            }
            logger.debug("Get TEST by ID successfully " + test);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return test;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeTutorInAllTest(User from, User to) {
        Connection con = dataSourceManager.getConnection();
        boolean result = changeTutorInAllTest(from, to, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeTutorInAllTest(User from, User to, TransactionManager tx) {
        return changeTutorInAllTest(from, to, tx.getConnection());
    }

    private boolean changeTutorInAllTest(User from, User to, Connection con) {
        String SQL = "UPDATE test SET OWNER_ID=? WHERE OWNER_ID=?";

        logger.debug("Try change TUTOR in all TEST from" + from.getId() + " to " + to.getId());

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setObject(1, to.getId());
            stm.setObject(2, from.getId());

            if(stm.executeUpdate() >= 0) {
                logger.debug("Successful change TUTOR in all TEST from" + from.getId() + " to " + to.getId());
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("Failure change TUTOR in all TEST from" + from.getId() + " to " + to.getId());
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Test test) {
        Connection con = dataSourceManager.getConnection();
        boolean result = update(test, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Test test, TransactionManager tx) {
        return update(test, tx.getConnection());
    }

    private boolean update(Test test, Connection con) {
        String SQL = "UPDATE test SET TEST_NAME=?, `DESC`=?, OWNER_ID=?, CATEGORY_ID=?, PASSED_SCORE=?, PASSED_TIME=?, ACTIVE=? " +
                "WHERE TEST_ID=?";

        logger.debug("Try update TEST " + test);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setString(1, test.getName());
            stm.setString(2, test.getDescription());
            stm.setObject(3, test.getTutor() == null ? null : test.getTutor().getId());
            stm.setObject(4, test.getCategory() == null ? null : test.getCategory().getId());
            stm.setInt(5, test.getPassedScore());
            stm.setInt(6, test.getPassedTime());
            stm.setBoolean(7, test.getActive());
            if(test.getId() == null)
                stm.setNull(8,Types.INTEGER);
            else
                stm.setLong(8, test.getId());

            if(stm.executeUpdate() > 0) {
                logger.debug("TEST update successfully " + test);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("TEST update fail " + test);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(Test test) {
        Connection con = dataSourceManager.getConnection();
        boolean result = insert(test, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(Test test, TransactionManager tx) {
        return insert(test, tx.getConnection());
    }

    private boolean insert(Test test, Connection con) {
        String SQL = "INSERT INTO test (TEST_NAME, `DESC`, OWNER_ID, CATEGORY_ID, PASSED_SCORE, PASSED_TIME, ACTIVE) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        logger.debug("Try insert TEST " + test);

        try(PreparedStatement stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, test.getName());
            stm.setString(2, test.getDescription());
            stm.setObject(3, test.getTutor() == null ? null : test.getTutor().getId());
            stm.setObject(4, test.getCategory() == null ? null : test.getCategory().getId());
            stm.setInt(5, test.getPassedScore());
            stm.setInt(6, test.getPassedTime());
            stm.setBoolean(7, test.getActive());

            if(stm.executeUpdate() > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if(rs.next()) {
                    test.setId(rs.getLong(1));
                    logger.debug("TEST insert successfully " + test);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("TEST insert fail " + test);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Test test) {
        Connection con = dataSourceManager.getConnection();
        boolean result = delete(test, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Test test, TransactionManager tx) {
        return delete(test, tx.getConnection());
    }

    private boolean delete(Test test, Connection con) {
        String SQL = "DELETE FROM test WHERE TEST_ID=?";

        logger.debug("Try delete TEST " + test);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, test.getId());
            if(stm.executeUpdate() > 0) {
                logger.debug("TEST delete successfully " + test);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("TEST delete fail " + test);
        return false;
    }

    /**
     * Method parses the {@link ResultSet} and returns a {@link Test}
     *
     * @param rs {@link ResultSet} which be parsed
     * @return parse {@code rs} and return a {@link Test}
     */
    private Test parseResultSet(ResultSet rs) throws SQLException {
        Test test = new Test();

        test.setId(rs.getObject(TEST_ID, Long.class));
        test.setName(rs.getString(TEST_NAME));
        test.setDescription(rs.getString(DESCRIPTION));
        test.setPassedScore(rs.getInt(PASSED_SCORE));
        test.setPassedTime(rs.getInt(PASSED_TIME));
        test.setActive(rs.getBoolean(ACTIVE));

        if(rs.getLong(CATEGORY_ID) != 0) {
            Category category = new Category();
            category.setId(rs.getLong(CATEGORY_ID));
            category.setName(rs.getString(CATEGORY_NAME));
            test.setCategory(category);
        }
        if(rs.getLong(USER_ID) != 0) {
            User user = new User();
            user.setId(rs.getLong(USER_ID));
            user.setLogin(rs.getString(LOGIN));
            user.setName(rs.getString(NAME));
            user.setSurname(rs.getString(SURNAME));
            test.setTutor(user);
        }

        return test;
    }
}
