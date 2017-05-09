package org.palax.service;

import org.palax.dao.RoleDao;
import org.palax.entity.Role;

import java.util.List;

/**
 * The {@code RoleService} service is a specified API for working with the {@link RoleDao}
 *
 * @author Taras Palashynskyy
 */
public interface RoleService {

    /**
     * Method to get all {@link Role}
     *
     * @return return {@link List} of all {@link Role}
     */
    List<Role> findAll();

    /**
     * Method to get {@link Role}
     *
     * @return return {@link Role}
     */
    Role find(Role role);
}
