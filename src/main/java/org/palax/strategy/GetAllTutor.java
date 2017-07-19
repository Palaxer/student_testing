package org.palax.strategy;


import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.UserService;
import org.palax.service.impl.DefaultUserService;
import org.palax.util.Pagination;

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

    @Override
    public List<User> getUser(Pagination pagination) {

        return  userService.findAllByRole(Role.TUTOR, pagination);
    }

    @Override
    public long count() {
        return userService.countByRole(Role.TUTOR);
    }
}
