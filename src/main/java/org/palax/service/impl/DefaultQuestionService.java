package org.palax.service.impl;

import org.apache.log4j.Logger;
import org.palax.dao.AnswerDao;
import org.palax.dao.QuestionDao;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.Question;
import org.palax.entity.Test;
import org.palax.service.QuestionService;

import java.util.List;


/**
 * {@inheritDoc}
 */
public class DefaultQuestionService implements QuestionService {
    private static final Logger logger = Logger.getLogger(DefaultQuestionService.class);
    private static volatile QuestionService questionService;
    private static QuestionDao questionDao;

    private DefaultQuestionService() {
        questionDao = MySQLDAOFactory.getQuestionDao();
    }

    /**
     * Always return same {@link DefaultQuestionService} instance
     *
     * @return always return same {@link DefaultQuestionService} instance
     */
    public static QuestionService getInstance(){
        QuestionService localInstance = questionService;
        if(localInstance == null) {
            synchronized (DefaultQuestionService.class) {
                localInstance = questionService;
                if(localInstance == null) {
                    questionService = new DefaultQuestionService();
                    logger.debug("Create first DefaultQuestionService instance");
                }
            }
        }
        logger.debug("Return DefaultQuestionService instance");
        return questionService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Question> findAllByTest(Test test) {
        return questionDao.findAllByTest(test);
    }

    @Override
    public Question findById(Long id) {
        return questionDao.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Question question) {
        return questionDao.update(question);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(Question question) {
        return questionDao.insert(question);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Question question) {
        return questionDao.delete(question);
    }
}
