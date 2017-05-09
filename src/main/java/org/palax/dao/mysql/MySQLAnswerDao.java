package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.AnswerDao;
import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Answer;
import org.palax.entity.Question;
import org.palax.util.DataSourceManager;
import org.palax.util.impl.DefaultDataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLAnswerDao} singleton class implements {@link AnswerDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLAnswerDao implements AnswerDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLAnswerDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static volatile AnswerDao answerDao;
    private DataSourceManager dataSourceManager;
    /**Values which store column name for {@link ResultSet} */
    private static final String ANSWER_ID = "ANSWER_ID";
    private static final String TEXT = "TEXT";
    private static final String CORRECT = "CORRECT";
    private static final String QUESTION_ID = "QUESTION_ID";

    private MySQLAnswerDao() {
        dataSourceManager = DefaultDataSourceManager.getInstance();
    }

    /**
     * Constructor is used to avoid use PowerMock in testing
     * @param dataSourceManager mock {@link DataSourceManager} object
     */
    public MySQLAnswerDao(DataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    /**
     * Always return same {@link MySQLAnswerDao} instance
     *
     * @return always return same {@link MySQLAnswerDao} instance
     */
    public static AnswerDao getInstance() {
        AnswerDao localInstance = answerDao;
        if(localInstance == null) {
            synchronized (MySQLAnswerDao.class) {
                localInstance = answerDao;
                if(localInstance == null) {
                    answerDao = new MySQLAnswerDao();
                    logger.debug("Create first MySQLAnswerDao instance");
                }
            }
        }
        logger.debug("Return MySQLAnswerDao instance");
        return answerDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Answer> findAllByQuestion(Question question) {
        String SQL = "SELECT * FROM answer WHERE QUESTION_ID=?";

        ArrayList<Answer> answerList = new ArrayList<>();

        logger.debug("Try get all ANSWER by QUESTION");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, question.getId());
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                answerList.add(parseResultSet(rs));
            }
            logger.debug("Get all ANSWER by QUESTION successfully " + answerList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return answerList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Answer findById(Long id) {
        String SQL = "SELECT * FROM answer WHERE ANSWER_ID=?";
        Answer answer = null;

        logger.debug("Try get ANSWER by ID " + id);

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                answer = parseResultSet(rs);
            }
            logger.debug("Get ANSWER by ID successfully " + answer);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return answer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Answer answer) {
        Connection con = dataSourceManager.getConnection();
        boolean result = update(answer, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Answer answer, TransactionManager tx) {
        return update(answer, tx.getConnection());
    }

    private boolean update(Answer answer, Connection con) {
        String SQL = "UPDATE answer SET TEXT=?, CORRECT=?, QUESTION_ID=? WHERE ANSWER_ID=?";

        logger.debug("Try update ANSWER " + answer);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setString(1, answer.getText());
            stm.setBoolean(2, answer.getCorrect());
            stm.setObject(3, answer.getQuestion() == null ? null : answer.getQuestion().getId());
            stm.setObject(4, answer.getId());

            if(stm.executeUpdate() > 0) {
                logger.debug("ANSWER update successfully " + answer);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("ANSWER update fail " + answer);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(Answer answer) {
        Connection con = dataSourceManager.getConnection();
        boolean result = insert(answer, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(Answer answer, TransactionManager tx) {
        return insert(answer, tx.getConnection());
    }

    private boolean insert(Answer answer, Connection con) {
        String SQL = "INSERT INTO answer (TEXT, CORRECT, QUESTION_ID) VALUES (?, ?, ?)";

        logger.debug("Try insert ANSWER " + answer);

        try(PreparedStatement stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, answer.getText());
            stm.setBoolean(2, answer.getCorrect());
            stm.setObject(3, answer.getQuestion() == null ? null : answer.getQuestion().getId());

            if(stm.executeUpdate() > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if(rs.next()) {
                    answer.setId(rs.getLong(1));
                    logger.debug("ANSWER insert successfully " + answer);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("ANSWER insert fail " + answer);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Answer answer) {
        Connection con = dataSourceManager.getConnection();
        boolean result = delete(answer, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Answer answer, TransactionManager tx) {
        return delete(answer, tx.getConnection());
    }

    private boolean delete(Answer answer, Connection con) {
        String SQL = "DELETE FROM answer WHERE ANSWER_ID=?";

        logger.debug("Try delete ANSWER " + answer);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, answer.getId());
            if(stm.executeUpdate() > 0) {
                logger.debug("ANSWER delete successfully " + answer);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("ANSWER delete fail " + answer);
        return false;
    }

    /**
     * Method parses the {@link ResultSet} and returns a {@link Answer}
     *
     * @param rs {@link ResultSet} which be parsed
     * @return parse {@code rs} and return a {@link Answer}
     */
    private Answer parseResultSet(ResultSet rs) throws SQLException {
        Answer answer = new Answer();

        answer.setId(rs.getObject(ANSWER_ID, Long.class));
        answer.setText(rs.getString(TEXT));
        answer.setCorrect(rs.getBoolean(CORRECT));

        if(rs.getLong(QUESTION_ID) != 0) {
            Question question = new Question();
            question.setId(rs.getLong(QUESTION_ID));
            answer.setQuestion(question);
        }

        return answer;
    }
}
