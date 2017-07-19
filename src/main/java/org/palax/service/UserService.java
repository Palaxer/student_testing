package org.palax.service;

import org.palax.dao.UserDao;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.util.Pagination;

import java.util.List;

/**
 * The {@code UserService} service is a specified API for working with the {@link UserDao}
 *
 * @author Taras Palashynskyy
 */
public interface UserService {

    /**
     * Method to create {@link User}
     *
     * @param user this {@code user} will be created
     * @return returns {@code true} if {@link User} create success
     *         or else {@code false}
     */
    boolean create(User user);

    /**
     * Method to get all {@link User} with limit
     *
     * @return return {@link List} of all {@link User} by limit
     */
    List<User> findAll(Pagination pagination);

    /**
     * Method to get all {@link User} by {@code role} with limit
     *
     * @return return {@link List} of all {@link User} by limit
     */
    List<User> findAllByRole(Role role , Pagination pagination);

    /**
     * Method return {@link User} which find by {@code id}
     *
     * @param id it indicates an {@link User} {@code id} that you want return
     * @return return {@link User} by {@code id}
     */
    User findById(Long id);

    /**
     * Method return {@link User} which find by {@code login}
     *
     * @param login it indicates an {@link User} {@code login} that you want return
     * @return return {@link User} by {@code login}
     */
    User findByLogin(String login);

    /**
     * Method to get number of all {@link User} which store in DB
     *
     * @return number of all row in table
     */
    long count();

    long countByRole(Role role);

    /**
     * Method change {@link User} {@code role}
     *
     * @param user the {@code user} {@code role} will be change
     * @param role the {@code role} of which is changed
     * @return returns {@code true} if the {@code user} updated
     *         or else {@code false}
     */
    boolean changeRole(User user, Role role);

    /**
     * Method update {@link User}
     *
     * @param user the {@code user} will update if it already exists
     * @return returns {@code true} if the {@code user} updated
     *         or else {@code false}
     */
    boolean update(User user);

    /**
     * Method delete {@link User} and his completed tests
     *
     * @param user the {@code user} will delete if it already exists
     * @return returns {@code true} if the {@code user} deleted
     *         or else {@code false}
     */
    boolean delete(User user, User admin);

    /**
     * Method duplicate {@link User} without changing reference
     *
     * @param from the value will be copied from here
     * @param to the value will be copied to here
     */
    void duplicate(User from, User to);
}
