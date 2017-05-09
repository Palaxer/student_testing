package org.palax.dao;

import org.palax.entity.Role;

import java.util.List;

/**
 * The {@code RoleDao} interface for ORM database entity {@link Role}
 *
 * @author Taras Palashynskyy
 */

public interface RoleDao {

    /**
     * Method to get all {@link Role}
     *
     * @return return {@link List} of all {@link Role}
     */
    List<Role> findAll();

    /**
     * Method return {@link Role} which find by {@code name}
     *
     * @param role it indicates an {@link Role} that you want return
     * @return return {@link Role}
     */
    Role find(Role role);
}
