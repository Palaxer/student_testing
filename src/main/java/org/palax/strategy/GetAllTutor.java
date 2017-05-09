package org.palax.strategy;


import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.UserService;
import org.palax.service.impl.DefaultUserService;

import java.util.List;

/**
 * The {@code GetAllTutor} class which implements {@link GetUserStrategy} interface
 * and declares the method for receiving all tutor
 *
 * @author Taras Palashynskyy
 * @see GetUserStrategy
 */

public class GetAllTutor implements GetUserStrategy {
    private static UserService userService;

    public GetAllTutor(){
        userService = DefaultUserService.getInstance();
    }

    /**
     * {@inheritDoc}
     *
     * @return return {@link List} of complete {@link User}
     */
    @Override
    public List<User> getUser(int offSet, int numberOfElement) {

        return  userService.findAllByRole(Role.TUTOR, offSet, numberOfElement);
    }

    @Override
    public long count() {
        return userService.countByRole(Role.TUTOR);
    }
}
