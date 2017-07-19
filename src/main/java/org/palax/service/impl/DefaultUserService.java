package org.palax.service.impl;

import org.apache.log4j.Logger;
import org.palax.dao.RoleDao;
import org.palax.dao.TestDao;
import org.palax.dao.UserDao;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.UserService;
import org.palax.util.Pagination;

import java.util.List;


/**
 * {@inheritDoc}
 */
public class DefaultUserService implements UserService {
    private static final Logger logger = Logger.getLogger(DefaultUserService.class);

    private static volatile UserService userService;
    private static UserDao userDao;
    private static RoleDao roleDao;
    private static TestDao testDao;

    private DefaultUserService() {
        userDao = MySQLDAOFactory.getUserDao();
        roleDao = MySQLDAOFactory.getRoleDao();
        testDao = MySQLDAOFactory.getTestDao();
    }

    /**
     * Always return same {@link DefaultUserService} instance
     *
     * @return always return same {@link DefaultUserService} instance
     */
    public static UserService getInstance(){
        UserService localInstance = userService;
        if(localInstance == null) {
            synchronized (DefaultUserService.class) {
                localInstance = userService;
                if(localInstance == null) {
                    userService = new DefaultUserService();
                    logger.debug("Create first DefaultUserService instance");
                }
            }
        }
        logger.debug("Return DefaultUserService instance");
        return userService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(User user) {
        user.setRole(roleDao.find(user.getRole()));
        return userDao.insert(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll(Pagination pagination) {
        return userDao.findAll(pagination.getElementOffSet(), pagination.getElementPerPage());
    }

    @Override
    public List<User> findAllByRole(Role role, Pagination pagination) {
        return userDao.findAllByRole(role, pagination.getElementOffSet(), pagination.getElementPerPage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count() {
        return userDao.count();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long countByRole(Role role) {
        return userDao.countByRole(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeRole(User user, Role role) {
        user.setRole(roleDao.find(role));
        return update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(User user) {
        return userDao.update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(User user, User admin) {
        boolean result = false;
        TransactionManager tx = new TransactionManager();

        tx.begin();

        if (testDao.changeTutorInAllTest(user, admin, tx)) {
            if (userDao.delete(user, tx)) {
                tx.commit();
                result = true;
            } else
                tx.rollback();
        }

        tx.close();

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void duplicate(User from, User to) {
        to.setId(from.getId());
        to.setRole(from.getRole());
        to.setSurname(from.getSurname());
        to.setName(from.getName());
        to.setPassword(from.getPassword());
        to.setLogin(from.getLogin());
    }
}
