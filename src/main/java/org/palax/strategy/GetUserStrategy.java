package org.palax.strategy;

import org.palax.entity.User;
import org.palax.util.Pagination;

import java.util.List;

/**
 * The {@code GetUserStrategy} interface which used to implementation strategy pattern
 * and declares the method for receiving user
 *
 * @author Taras Palashynskyy
 */
public interface GetUserStrategy {

    List<User> getUser(Pagination pagination);

    long count();
}
