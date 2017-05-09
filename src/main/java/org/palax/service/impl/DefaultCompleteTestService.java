package org.palax.service.impl;

import org.apache.log4j.Logger;
import org.palax.dao.AnswerDao;
import org.palax.dao.CompleteTestDao;
import org.palax.dao.QuestionDao;
import org.palax.dao.TestDao;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.*;
import org.palax.service.CompleteTestService;

import java.util.List;
import java.util.Map;


/**
 * {@inheritDoc}
 */
public class DefaultCompleteTestService implements CompleteTestService {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DefaultCompleteTestService.class);
    /**Singleton object which is returned when you try to create a new instance */
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
    public List<CompleteTest> findAllByStudent(User student, int offSet, int numberOfElement) {
        List<CompleteTest> completeTests = completeTestDao.findAllByStudent(student, offSet, numberOfElement);

        for(CompleteTest completeTest : completeTests){
            completeTest.setTest(testDao.findById(completeTest.getTest().getId()));
        }

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

        int score = 0;
        boolean correct;

        List<Question> questions = questionDao.findAllByTest(completeTest.getTest());
        for (Question question : questions) {
            question.setAnswers(answerDao.findAllByQuestion(question));

            correct = true;

            for(Answer answer : question.getAnswers()) {
                if(!(answer.getCorrect().equals(userAnswers.get(answer.getId()) != null))) {
                    correct = false;
                    break;
                }
            }

            if(correct)
                score++;
        }

        completeTest.setScore(score);

        if(completeTest.getElapsedTime() <= completeTest.getTest().getPassedTime() &&
                completeTest.getScore() >= completeTest.getTest().getPassedScore()) {
            completeTest.setPassed(true);
        } else
            completeTest.setPassed(false);

        return completeTestDao.insert(completeTest);
    }
}
