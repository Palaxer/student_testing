package org.palax.strategy;

import org.palax.entity.User;

import java.util.List;

/**
 * The {@code GetUser} is a context class which provide a way to set
 * which strategy will used to get {@link User}
 *
 * @author Taras Palashynskyy
 */

public class GetUser implements GetUserStrategy {
    /**Object that stores an instance of a necessary strategy. */
    private GetUserStrategy strategy;

    /**
     * Constructor which sets the necessary strategy
     *
     * @param strategy instance which implements {@link GetUserStrategy} interface
     */
    public GetUser(GetUserStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Method return {@link List} of {@link User} by some strategy
     *
     * @return return {@link List} of {@link User}
     */
    public List<User> getUser(int offSet, int numberOfElement) {
        return strategy.getUser(offSet, numberOfElement);
    }

    @Override
    public long count() {
        return strategy.count();
    }


}
