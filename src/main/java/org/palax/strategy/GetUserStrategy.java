package org.palax.strategy;

import org.palax.entity.User;

import java.util.List;

/**
 * The {@code GetUserStrategy} interface which used to implementation strategy pattern
 * and declares the method for receiving user
 *
 * @author Taras Palashynskyy
 */

public interface GetUserStrategy {

    /**
     * Method return {@link List} of {@link User} by some strategy
     *
     * @return return {@link List} of {@link User}
     */
    List<User> getUser(int offSet, int numberOfElement);

    long count();
}
