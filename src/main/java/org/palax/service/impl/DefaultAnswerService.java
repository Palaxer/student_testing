package org.palax.service.impl;

import org.apache.log4j.Logger;
import org.palax.dao.AnswerDao;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Answer;
import org.palax.entity.Question;
import org.palax.service.AnswerService;

import java.util.List;


/**
 * {@inheritDoc}
 */
public class DefaultAnswerService implements AnswerService {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DefaultAnswerService.class);
    /**Singleton object which is returned when you try to create a new instance */
    private static volatile AnswerService answerService;
    private static AnswerDao answerDao;

    private DefaultAnswerService() {
        answerDao = MySQLDAOFactory.getAnwerDao();
    }

    /**
     * Always return same {@link DefaultAnswerService} instance
     *
     * @return always return same {@link DefaultAnswerService} instance
     */
    public static AnswerService getInstance(){
        AnswerService localInstance = answerService;
        if(localInstance == null) {
            synchronized (DefaultAnswerService.class) {
                localInstance = answerService;
                if(localInstance == null) {
                    answerService = new DefaultAnswerService();
                    logger.debug("Create first DefaultAnswerService instance");
                }
            }
        }
        logger.debug("Return DefaultAnswerService instance");
        return answerService;
    }


    @Override
    public List<Answer> findAllByQuestion(Question question) {

        return answerDao.findAllByQuestion(question);
    }

    @Override
    public Answer findById(Long id) {

        return answerDao.findById(id);
    }

    @Override
    public boolean update(List<Answer> answers) {
        TransactionManager tx = new TransactionManager();

        boolean result = true;

        tx.begin();
        for(Answer answer : answers) {
            if(!answerDao.update(answer, tx)) {
                result = false;
                break;
            }
        }

        if(result)
            tx.commit();
        else
            tx.rollback();

        tx.close();

        return result;
    }

    @Override
    public boolean create(Answer answer) {

        return answerDao.insert(answer);
    }

    @Override
    public boolean delete(Answer answer) {

        return answerDao.delete(answer);
    }

}
