package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.CompleteTestDao;
import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.CompleteTest;
import org.palax.entity.Role;
import org.palax.entity.Test;
import org.palax.entity.User;
import org.palax.util.DataSourceManager;
import org.palax.util.impl.DefaultDataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLCompleteTestDao} singleton class implements {@link CompleteTestDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLCompleteTestDao implements CompleteTestDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLCompleteTestDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static volatile CompleteTestDao completeTestDao;
    private DataSourceManager dataSourceManager;
    /**Values which store column name for {@link ResultSet} */
    private static final String COMPLETE_ID = "COMPLETE_ID";
    private static final String SCORE = "SCORE";
    private static final String START_TIME = "START_TIME";
    private static final String ELAPSED_TIME = "ELAPSED_TIME";
    private static final String PASSED = "PASSED";
    private static final String TEST_ID = "TEST_ID";
    private static final String USER_ID = "USER_ID";
    private static final String LOGIN = "LOGIN";
    private static final String PASSWORD = "PASSWD";
    private static final String NAME = "NAME";
    private static final String SURNAME = "SURNAME";
    private static final String ROLE_ID = "ROLE_ID";
    private static final String ROLE_TYPE = "ROLE_TYPE";

    private MySQLCompleteTestDao() {
        dataSourceManager = DefaultDataSourceManager.getInstance();
    }

    /**
     * Constructor is used to avoid use PowerMock in testing
     * @param dataSourceManager mock {@link DataSourceManager} object
     */
    public MySQLCompleteTestDao(DataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    /**
     * Always return same {@link MySQLCompleteTestDao} instance
     *
     * @return always return same {@link MySQLCompleteTestDao} instance
     */
    public static CompleteTestDao getInstance() {
        CompleteTestDao localInstance = completeTestDao;
        if(localInstance == null) {
            synchronized (MySQLCompleteTestDao.class) {
                localInstance = completeTestDao;
                if(localInstance == null) {
                    completeTestDao = new MySQLCompleteTestDao();
                    logger.debug("Create first MySQLCompleteTestDao instance");
                }
            }
        }
        logger.debug("Return MySQLCompleteTestDao instance");
        return completeTestDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CompleteTest> findAllByStudent(User student) {
        String SQL = "SELECT * FROM complete_test A LEFT JOIN user B ON (A.USER_ID=B.USER_ID)" +
                "LEFT JOIN role C ON (B.ROLE_ID=C.ROLE_ID) WHERE A.USER_ID=?";

        ArrayList<CompleteTest> completeTestList = new ArrayList<>();

        logger.debug("Try get all COMPLETE_TEST by USER");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, student.getId());
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                completeTestList.add(parseResultSet(rs));
            }
            logger.debug("Get all COMPLETE_TEST by USER successfully " + completeTestList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return completeTestList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CompleteTest> findAllByStudent(User student, int offSet, int numberOfElement) {
        String SQL = "SELECT * FROM complete_test A LEFT JOIN user B ON (A.USER_ID=B.USER_ID)" +
                "LEFT JOIN role C ON (B.ROLE_ID=C.ROLE_ID) WHERE A.USER_ID=? " +
                "LIMIT " + offSet + ", " + numberOfElement;

        ArrayList<CompleteTest> completeTestList = new ArrayList<>();

        logger.debug("Try get all COMPLETE_TEST by USER  with LIMIT");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, student.getId());
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                completeTestList.add(parseResultSet(rs));
            }
            logger.debug("Get all COMPLETE_TEST by USER  with LIMIT successfully " + completeTestList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return completeTestList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CompleteTest> findAllByTest(Test test) {
        String SQL = "SELECT * FROM complete_test A LEFT JOIN user B ON (A.USER_ID=B.USER_ID)" +
                "LEFT JOIN role C ON (B.ROLE_ID=C.ROLE_ID) WHERE TEST_ID=?";

        ArrayList<CompleteTest> completeTestList = new ArrayList<>();

        logger.debug("Try get all COMPLETE_TEST by TEST");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, test.getId());
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                completeTestList.add(parseResultSet(rs));
            }
            logger.debug("Get all COMPLETE_TEST by TEST successfully " + completeTestList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return completeTestList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompleteTest findById(Long id) {
        String SQL = "SELECT * FROM complete_test A LEFT JOIN user B ON (A.USER_ID=B.USER_ID)" +
                "LEFT JOIN role C ON (B.ROLE_ID=C.ROLE_ID) WHERE COMPLETE_ID=?";
        CompleteTest completeTest = null;

        logger.debug("Try get COMPLETE_TEST by ID " + id);

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                completeTest = parseResultSet(rs);
            }
            logger.debug("Get COMPLETE_TEST by ID successfully " + completeTest);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return completeTest;
    }

    @Override
    public long countByUser(User user) {
        String SQL = "SELECT COUNT(*) FROM complete_test WHERE USER_ID=" + user.getId();

        long count = 0;

        logger.debug("Try count all COMPLETE_TEST by USER ");

        try(Connection con = dataSourceManager.getConnection();
            Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SQL);

            while(rs.next()) {
                count = rs.getLong(1);
            }
            logger.debug("Count all COMPLETE_TEST by USER  successfully" + count);

        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return count;
    }

    @Override
    public int countByTest(Test test) {
        String SQL = "SELECT COUNT(*) FROM complete_test WHERE TEST_ID=" + test.getId();

        int count = 0;

        logger.debug("Try count all COMPLETE_TEST by TEST");

        try(Connection con = dataSourceManager.getConnection();
            Statement stm = con.createStatement()) {

            ResultSet rs = stm.executeQuery(SQL);

            while(rs.next()) {
                count = rs.getInt(1);
            }
            logger.debug("Count all COMPLETE_TEST by TEST successfully" + count);

        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return count;
    }

    @Override
    public int countByTestAndPassed(Test test, Boolean passed) {
        String SQL = "SELECT COUNT(*) FROM complete_test WHERE TEST_ID=" + test.getId() +
                " AND PASSED=" + passed;

        int count = 0;

        logger.debug("Try count passed COMPLETE_TEST by TEST");

        try(Connection con = dataSourceManager.getConnection();
            Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SQL);

            while(rs.next()) {
                count = rs.getInt(1);
            }
            logger.debug("Count passed COMPLETE_TEST by TEST successfully" + count);

        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(CompleteTest completeTest) {
        boolean result = false;
        try(Connection con = dataSourceManager.getConnection()) {
            result = update(completeTest, con);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(CompleteTest completeTest, TransactionManager tx) {
        return update(completeTest, tx.getConnection());
    }

    private boolean update(CompleteTest completeTest, Connection con) {
        String SQL = "UPDATE complete_test SET SCORE=?, START_TIME=?, ELAPSED_TIME=?, USER_ID=?, TEST_ID=?, PASSED=? " +
                "WHERE COMPLETE_ID=?";

        logger.debug("Try update COMPLETE_TEST " + completeTest);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setObject(1, completeTest.getScore());
            stm.setTimestamp(2, Timestamp.valueOf(completeTest.getStartTime()));
            stm.setObject(3, completeTest.getElapsedTime());
            stm.setObject(4, completeTest.getStudent() == null ? null : completeTest.getStudent().getId());
            stm.setObject(5, completeTest.getTest() == null ? null : completeTest.getTest().getId());
            stm.setBoolean(6, completeTest.getPassed());
            stm.setObject(7, completeTest.getId());

            if(stm.executeUpdate() > 0) {
                logger.debug("COMPLETE_TEST update successfully " + completeTest);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("COMPLETE_TEST update fail " + completeTest);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(CompleteTest completeTest) {
        boolean result = false;
        try(Connection con = dataSourceManager.getConnection()) {
            result = insert(completeTest, con);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(CompleteTest completeTest, TransactionManager tx) {
        return insert(completeTest, tx.getConnection());
    }

    private boolean insert(CompleteTest completeTest, Connection con) {
        String SQL = "INSERT INTO complete_test (SCORE, START_TIME, ELAPSED_TIME, USER_ID, TEST_ID, PASSED) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        logger.debug("Try insert COMPLETE_TEST " + completeTest);

        try(PreparedStatement stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            stm.setObject(1, completeTest.getScore());
            stm.setTimestamp(2, Timestamp.valueOf(completeTest.getStartTime()));
            stm.setObject(3, completeTest.getElapsedTime());
            stm.setObject(4, completeTest.getStudent() == null ? null : completeTest.getStudent().getId());
            stm.setObject(5, completeTest.getTest() == null ? null : completeTest.getTest().getId());
            stm.setBoolean(6, completeTest.getPassed());

            if(stm.executeUpdate() > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if(rs.next()) {
                    completeTest.setId(rs.getLong(1));
                    logger.debug("COMPLETE_TEST insert successfully " + completeTest);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("COMPLETE_TEST insert fail " + completeTest);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(CompleteTest completeTest) {
        boolean result = false;
        try(Connection con = dataSourceManager.getConnection()) {
            result = delete(completeTest, con);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(CompleteTest completeTest, TransactionManager tx) {
        return delete(completeTest, tx.getConnection());
    }

    private boolean delete(CompleteTest completeTest, Connection con) {
        String SQL = "DELETE FROM complete_test WHERE COMPLETE_ID=?";

        logger.debug("Try delete COMPLETE_TEST " + completeTest);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, completeTest.getId());
            if(stm.executeUpdate() > 0) {
                logger.debug("COMPLETE_TEST delete successfully " + completeTest);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("COMPLETE_TEST delete fail " + completeTest);
        return false;
    }

    /**
     * Method parses the {@link ResultSet} and returns a {@link CompleteTest}
     *
     * @param rs {@link ResultSet} which be parsed
     * @return parse {@code rs} and return a {@link CompleteTest}
     */
    private CompleteTest parseResultSet(ResultSet rs) throws SQLException {
        CompleteTest completeTest = new CompleteTest();

        completeTest.setId(rs.getObject(COMPLETE_ID, Long.class));
        completeTest.setScore(rs.getObject(SCORE, Integer.class));
        completeTest.setElapsedTime(rs.getObject(ELAPSED_TIME, Integer.class));
        completeTest.setPassed(rs.getBoolean(PASSED));

        if(rs.getLong(USER_ID) != 0) {
            User user = new User();
            user.setId(rs.getLong(USER_ID));
            user.setLogin(rs.getString(LOGIN));
            user.setPassword(rs.getString(PASSWORD));
            user.setName(rs.getString(NAME));
            user.setSurname(rs.getString(SURNAME));

            if(rs.getLong(ROLE_ID) != 0) {
                Role role = Role.valueOf(rs.getString(ROLE_TYPE));
                role.setId(rs.getLong(ROLE_ID));
                user.setRole(role);
            }
            completeTest.setStudent(user);
        }
        if(rs.getLong(TEST_ID) != 0) {
            Test test = new Test();
            test.setId(rs.getLong(TEST_ID));
            completeTest.setTest(test);
        }

        if(rs.getTimestamp(START_TIME) != null)
            completeTest.setStartTime(rs.getTimestamp(START_TIME).toLocalDateTime());

        return completeTest;
    }
}
