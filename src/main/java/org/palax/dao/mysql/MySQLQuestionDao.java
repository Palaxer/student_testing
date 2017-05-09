package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.QuestionDao;
import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Question;
import org.palax.entity.Test;
import org.palax.util.DataSourceManager;
import org.palax.util.impl.DefaultDataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLQuestionDao} singleton class implements {@link QuestionDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLQuestionDao implements QuestionDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLQuestionDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static volatile QuestionDao questionDao;
    private DataSourceManager dataSourceManager;
    /**Values which store column name for {@link ResultSet} */
    private static final String QUESTION_ID = "QUESTION_ID";
    private static final String TEXT = "TEXT";
    private static final String TEST_ID = "TEST_ID";

    private MySQLQuestionDao() {
        dataSourceManager = DefaultDataSourceManager.getInstance();
    }

    /**
     * Constructor is used to avoid use PowerMock in testing
     * @param dataSourceManager mock {@link DataSourceManager} object
     */
    public MySQLQuestionDao(DataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    /**
     * Always return same {@link MySQLQuestionDao} instance
     *
     * @return always return same {@link MySQLQuestionDao} instance
     */
    public static QuestionDao getInstance() {
        QuestionDao localInstance = questionDao;
        if(localInstance == null) {
            synchronized (MySQLQuestionDao.class) {
                localInstance = questionDao;
                if(localInstance == null) {
                    questionDao = new MySQLQuestionDao();
                    logger.debug("Create first MySQLQuestionDao instance");
                }
            }
        }
        logger.debug("Return MySQLQuestionDao instance");
        return questionDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Question> findAllByTest(Test test) {
        String SQL = "SELECT * FROM question WHERE TEST_ID=?";

        ArrayList<Question> questionList = new ArrayList<>();

        logger.debug("Try get all QUESTION by TEST");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, test.getId());
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                questionList.add(parseResultSet(rs));
            }
            logger.debug("Get all QUESTION by TEST successfully " + questionList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return questionList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Question findById(Long id) {
        String SQL = "SELECT * FROM question WHERE QUESTION_ID=?";
        Question question = null;

        logger.debug("Try get QUESTION by ID " + id);

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                question = parseResultSet(rs);
            }
            logger.debug("Get QUESTION by ID successfully " + question);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return question;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countByTest(Test test) {
        String SQL = "SELECT COUNT(*) FROM question WHERE TEST_ID=" + test.getId();

        int count = 0;

        logger.debug("Try count all QUESTION by TEST");

        try(Connection con = dataSourceManager.getConnection();
            Statement stm = con.createStatement()) {
            ResultSet rs = stm.executeQuery(SQL);

            while(rs.next()) {
                count = rs.getInt(1);
            }
            logger.debug("Count all QUESTION by TEST successfully" + count);

        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        return count;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Question question) {
        Connection con = dataSourceManager.getConnection();
        boolean result = update(question, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Question question, TransactionManager tx) {
        return update(question, tx.getConnection());
    }

    private boolean update(Question question, Connection con) {
        String SQL = "UPDATE question SET TEXT=?, TEST_ID=? WHERE QUESTION_ID=?";

        logger.debug("Try update QUESTION " + question);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setString(1, question.getText());
            stm.setObject(2, question.getTest() == null ? null : question.getTest().getId());
            stm.setObject(3, question.getId());

            if(stm.executeUpdate() > 0) {
                logger.debug("QUESTION update successfully " + question);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("QUESTION update fail " + question);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(Question question) {
        Connection con = dataSourceManager.getConnection();
        boolean result = insert(question, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(Question question, TransactionManager tx) {
        return insert(question, tx.getConnection());
    }

    private boolean insert(Question question, Connection con) {
        String SQL = "INSERT INTO question (TEXT, TEST_ID) VALUES (?, ?)";

        logger.debug("Try insert QUESTION " + question);

        try(PreparedStatement stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, question.getText());
            stm.setObject(2, question.getTest() == null ? null : question.getTest().getId());

            if(stm.executeUpdate() > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if(rs.next()) {
                    question.setId(rs.getLong(1));
                    logger.debug("QUESTION insert successfully " + question);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("QUESTION insert fail " + question);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Question question) {
        Connection con = dataSourceManager.getConnection();
        boolean result = delete(question, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Question question, TransactionManager tx) {
        return delete(question, tx.getConnection());
    }

    private boolean delete(Question question, Connection con) {
        String SQL = "DELETE FROM question WHERE QUESTION_ID=?";

        logger.debug("Try delete QUESTION " + question);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, question.getId());
            if(stm.executeUpdate() > 0) {
                logger.debug("QUESTION delete successfully " + question);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("QUESTION delete fail " + question);
        return false;
    }

    /**
     * Method parses the {@link ResultSet} and returns a {@link Question}
     *
     * @param rs {@link ResultSet} which be parsed
     * @return parse {@code rs} and return a {@link Question}
     */
    private Question parseResultSet(ResultSet rs) throws SQLException {
        Question question = new Question();

        question.setId(rs.getObject(QUESTION_ID, Long.class));
        question.setText(rs.getString(TEXT));

        if(rs.getLong(TEST_ID) != 0) {
            Test test = new Test();
            test.setId(rs.getLong(TEST_ID));
            question.setTest(test);
        }

        return question;
    }
}
