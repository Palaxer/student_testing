package org.palax.dao;

import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Role;
import org.palax.entity.User;

import java.util.List;

/**
 * The {@code UserDao} interface for ORM database entity {@link User}
 *
 * @author Taras Palashynskyy
 */

public interface UserDao {

    /**
     * Method to get all {@link User} with limit
     * @param offSet how many rows will be skipped
     * @param numberOfElement how many rows need to get
     * @return return {@link List} of all {@link User} by limit
     */
    List<User> findAll(int offSet, int numberOfElement);

    /**
     * Method to get all {@link User} by {@code role} with limit
     *
     * @param offSet how many rows will be skipped
     * @param numberOfElement how many rows need to get
     * @return return {@link List} of all {@link User} by {@code role} by limit
     */
    List<User> findAllByRole(Role role, int offSet, int numberOfElement);

    /**
     * Method to get number of all {@link User} which store in DB
     *
     * @return number of all row in table
     */
    long count();

    long countByRole(Role role);

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
     * Method update {@link User}
     *
     * @param user the {@code user} will update if it already exists
     * @return returns {@code true} if the {@code user} updated
     *         or else {@code false}
     */
    boolean update(User user);

    /**
     * Method update {@link User} with transaction
     *
     * @param user the {@code user} will update if it already exists
     * @return returns {@code true} if the {@code user} updated
     *         or else {@code false}
     */
    boolean update(User user, TransactionManager tx);

    /**
     * Method to insert {@link User}
     *
     * @param user this {@code user} will be inserted
     * @return returns {@code true} if {@link User} inserted success
     *         or else {@code false}
     */
    boolean insert(User user);

    /**
     * Method to insert {@link User} with transaction
     *
     * @param user this {@code user} will be inserted
     * @return returns {@code true} if {@link User} inserted success
     *         or else {@code false}
     */
    boolean insert(User user, TransactionManager tx);

    /**
     * Method delete {@link User}
     *
     * @param user {@code user} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(User user);

    /**
     * Method delete {@link User} with transaction
     *
     * @param user {@code user} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(User user, TransactionManager tx);
}
