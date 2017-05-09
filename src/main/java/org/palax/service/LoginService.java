package org.palax.service;

import org.palax.dao.UserDao;
import org.palax.entity.User;

/**
 * The {@code LoginService} service is a specified login API for working with the {@link UserDao}
 *
 * @author Taras Palashynskyy
 */
public interface LoginService {

    /**
     * Method checks whether there is a {@link User} which find by {@code login}
     * and the associated {@code passwd}
     *
     * @param login {@link User} login
     * @param passwd {@link User} password
     * @return returns the {@link User} who has been found or {@code null}
     */
    User login(String login, String passwd);
}
