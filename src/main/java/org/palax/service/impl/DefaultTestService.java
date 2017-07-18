package org.palax.service.impl;

import org.apache.log4j.Logger;
import org.palax.dao.CompleteTestDao;
import org.palax.dao.QuestionDao;
import org.palax.dao.TestDao;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.dto.TestDTO;
import org.palax.entity.Category;
import org.palax.entity.Test;
import org.palax.entity.User;
import org.palax.service.TestService;

import java.util.List;


/**
 * {@inheritDoc}
 */
public class DefaultTestService implements TestService {
    private static final Logger logger = Logger.getLogger(DefaultTestService.class);
    private static volatile TestService testService;
    private static TestDao testDao;
    private static QuestionDao questionDao;
    private static CompleteTestDao completeTestDao;

    private DefaultTestService() {
        testDao = MySQLDAOFactory.getTestDao();
        questionDao = MySQLDAOFactory.getQuestionDao();
        completeTestDao = MySQLDAOFactory.getCompleteTestDao();
    }

    /**
     * Always return same {@link DefaultTestService} instance
     *
     * @return always return same {@link DefaultTestService} instance
     */
    public static TestService getInstance(){
        TestService localInstance = testService;
        if(localInstance == null) {
            synchronized (DefaultTestService.class) {
                localInstance = testService;
                if(localInstance == null) {
                    testService = new DefaultTestService();
                    logger.debug("Create first DefaultTestService instance");
                }
            }
        }
        logger.debug("Return DefaultTestService instance");
        return testService;
    }

    @Override
    public TestDTO findById(Long id) {
        Test test = testDao.findById(id);

        TestDTO testDTO = new TestDTO(test);
        testDTO.setQuestionCount(questionDao.countByTest(test));
        testDTO.setCompletedTime(completeTestDao.countByTest(test));
        int percent = 0;
        if(testDTO.getCompletedTime() !=0 )
            percent = completeTestDao.countByTestAndPassed(test, true) * 100 / testDTO.getCompletedTime();
        testDTO.setPassPercent(percent);

        return testDTO;
    }

    @Override
    public List<Test> findAllByTutor(User tutor, int offSet, int numberOfElement) {
        return testDao.findAllByTutor(tutor, offSet, numberOfElement);
    }

    @Override
    public List<Test> findAllByCategoryAndActive(Category category, boolean active, int offSet, int numberOfElement) {
        if(category == null)
            return testDao.findAllByActive(active, offSet, numberOfElement);

        return testDao.findAllByCategoryAndActive(category, active, offSet, numberOfElement);
    }

    @Override
    public long countByTutor(User tutor) {
        return testDao.countByTutor(tutor);
    }

    @Override
    public long countByCategoryAndActive(Category category, boolean active) {
        if(category == null)
            return testDao.countByActive(active);

        return testDao.countByCategoryAndActive(category, active);
    }

    @Override
    public boolean update(Test test) {
        return testDao.update(test);
    }

    @Override
    public boolean create(Test test) {
        test.setActive(false);
        test.setPassedScore(0);

        return testDao.insert(test);
    }
}
