package org.palax.service.impl;

import org.apache.log4j.Logger;
import org.palax.dao.AnswerDao;
import org.palax.dao.CompleteTestDao;
import org.palax.dao.QuestionDao;
import org.palax.dao.TestDao;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.*;
import org.palax.service.CompleteTestService;
import org.palax.util.Pagination;

import java.util.List;
import java.util.Map;


/**
 * {@inheritDoc}
 */
public class DefaultCompleteTestService implements CompleteTestService {
    private static final Logger logger = Logger.getLogger(DefaultCompleteTestService.class);
    private static final int SECONDS_IN_MINUTE = 60;

    private static volatile CompleteTestService completeTestService;
    private static CompleteTestDao completeTestDao;
    private static TestDao testDao;
    private static QuestionDao questionDao;
    private static AnswerDao answerDao;

    private DefaultCompleteTestService() {
        completeTestDao = MySQLDAOFactory.getCompleteTestDao();
        testDao = MySQLDAOFactory.getTestDao();
        questionDao = MySQLDAOFactory.getQuestionDao();
        answerDao = MySQLDAOFactory.getAnwerDao();
    }

    /**
     * Always return same {@link DefaultCompleteTestService} instance
     *
     * @return always return same {@link DefaultCompleteTestService} instance
     */
    public static CompleteTestService getInstance(){
        CompleteTestService localInstance = completeTestService;
        if(localInstance == null) {
            synchronized (DefaultCompleteTestService.class) {
                localInstance = completeTestService;
                if(localInstance == null) {
                    completeTestService = new DefaultCompleteTestService();
                    logger.debug("Create first DefaultCompleteTestService instance");
                }
            }
        }
        logger.debug("Return DefaultCompleteTestService instance");
        return completeTestService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CompleteTest> findAllByStudent(User student) {
        return completeTestDao.findAllByStudent(student);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CompleteTest> findAllByStudent(User student, Pagination pagination) {
        List<CompleteTest> completeTests = completeTestDao.findAllByStudent(student, pagination.getElementOffSet(),
                pagination.getElementPerPage());

        for(CompleteTest completeTest : completeTests)
            completeTest.setTest(testDao.findById(completeTest.getTest().getId()));

        return completeTests;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CompleteTest> findAllByTest(Test test) {
        return completeTestDao.findAllByTest(test);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countByUser(User user) {
        return completeTestDao.countByUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompleteTest findById(Long id) {
        return completeTestDao.findById(id);
    }

    @Override
    public boolean completeTest(CompleteTest completeTest, Map<Long, Boolean> userAnswers) {
        int score = calculateScore(completeTest, userAnswers);
        completeTest.setScore(score);
        completeTest.setPassed(isTestPassed(completeTest));

        return completeTestDao.insert(completeTest);
    }

    private int calculateScore(CompleteTest completeTest, Map<Long, Boolean> userAnswers) {
        int score = 0;

        List<Question> questions = questionDao.findAllByTest(completeTest.getTest());
        for (Question question : questions) {
            question.setAnswers(answerDao.findAllByQuestion(question));
            if(isCorrectAnswer(userAnswers, question))
                score++;
        }
        return score;
    }

    private boolean isCorrectAnswer(Map<Long, Boolean> userAnswers, Question question) {
        boolean correct = true;
        for(Answer answer : question.getAnswers())
            if(!isAnswerTheSame(userAnswers, answer)) {
                correct = false;
                break;
            }

        return correct;
    }

    private boolean isAnswerTheSame(Map<Long, Boolean> userAnswers, Answer answer) {
        return answer.getCorrect().equals(answerGivenByUser(userAnswers, answer));
    }

    private boolean answerGivenByUser(Map<Long, Boolean> userAnswers, Answer answer) {
        return userAnswers.get(answer.getId()) != null;
    }

    private boolean isTestPassed(CompleteTest completeTest) {
        boolean result = false;

        if(checkTimeLimit(completeTest) && checkScoreLimit(completeTest))
            result = true;

        return result;
    }

    private boolean checkScoreLimit(CompleteTest completeTest) {
        return completeTest.getScore() >= completeTest.getTest().getPassedScore();
    }

    private boolean checkTimeLimit(CompleteTest completeTest) {
        int userElapsedTimeInMinutes = completeTest.getElapsedTime() / SECONDS_IN_MINUTE;
        return userElapsedTimeInMinutes <= completeTest.getTest().getPassedTime();
    }
}
