package org.palax.service.impl;

import org.apache.log4j.Logger;
import org.palax.dao.UserDao;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.User;
import org.palax.service.LoginService;


/**
 * {@inheritDoc}
 */
public class DefaultLoginService implements LoginService {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DefaultLoginService.class);
    /**Singleton object which is returned when you try to create a new instance */
    private static volatile LoginService loginService;
    private static UserDao userDao;

    private DefaultLoginService() {
        userDao = MySQLDAOFactory.getUserDao();
    }

    /**
     * Always return same {@link DefaultLoginService} instance
     *
     * @return always return same {@link DefaultLoginService} instance
     */
    public static LoginService getInstance(){
        LoginService localInstance = loginService;
        if(localInstance == null) {
            synchronized (DefaultLoginService.class) {
                localInstance = loginService;
                if(localInstance == null) {
                    loginService = new DefaultLoginService();
                    logger.debug("Create first DefaultLoginService instance");
                }
            }
        }
        logger.debug("Return DefaultLoginService instance");
        return loginService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User login(String login, String passwd) {
        User user = userDao.findByLogin(login);

        if(user != null && user.getPassword().equals(passwd))
            return user;

        return null;
    }
}
