package org.palax.dao.factory;

import org.palax.dao.*;
import org.palax.dao.mysql.*;

/**
 * The {@code MySQLDAOFactory} class is a factory method to
 * return instances of DAO implementations for MySQL
 *
 * @author Taras Palashynskyy
 */
public class MySQLDAOFactory {

    private MySQLDAOFactory() {
    }

    public static RoleDao getRoleDao() {
        return MySQLRoleDao.getInstance();
    }

    public static UserDao getUserDao() {
        return MySQLUserDao.getInstance();
    }

    public static AnswerDao getAnwerDao() {
        return MySQLAnswerDao.getInstance();
    }

    public static CategoryDao getCategoryDao() {
        return MySQLCategoryDao.getInstance();
    }

    public static QuestionDao getQuestionDao() {
        return MySQLQuestionDao.getInstance();
    }

    public static TestDao getTestDao() {
        return MySQLTestDao.getInstance();
    }

    public static CompleteTestDao getCompleteTestDao() {
        return MySQLCompleteTestDao.getInstance();
    }
}
