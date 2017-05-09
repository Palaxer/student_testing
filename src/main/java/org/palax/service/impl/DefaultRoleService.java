package org.palax.service.impl;

import org.apache.log4j.Logger;
import org.palax.dao.RoleDao;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.Role;
import org.palax.service.RoleService;

import java.util.List;


/**
 * {@inheritDoc}
 */
public class DefaultRoleService implements RoleService {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DefaultRoleService.class);
    /**Singleton object which is returned when you try to create a new instance */
    private static volatile RoleService roleService;
    private static RoleDao roleDao;

    private DefaultRoleService() {
        roleDao = MySQLDAOFactory.getRoleDao();
    }

    /**
     * Always return same {@link DefaultRoleService} instance
     *
     * @return always return same {@link DefaultRoleService} instance
     */
    public static RoleService getInstance(){
        RoleService localInstance = roleService;
        if(localInstance == null) {
            synchronized (DefaultRoleService.class) {
                localInstance = roleService;
                if(localInstance == null) {
                    roleService = new DefaultRoleService();
                    logger.debug("Create first DefaultRoleService instance");
                }
            }
        }
        logger.debug("Return DefaultRoleService instance");
        return roleService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> findAll() {

        return roleDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role find(Role role) {

        return roleDao.find(role);
    }
}
