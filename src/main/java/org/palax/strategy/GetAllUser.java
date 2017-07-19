package org.palax.strategy;

import org.palax.entity.User;
import org.palax.service.UserService;
import org.palax.service.impl.DefaultUserService;
import org.palax.util.Pagination;

import java.util.List;

/**
 * The {@code GetAllUser} class which implements {@link GetUserStrategy} interface
 * and declares the method for receiving all user
 *
 * @author Taras Palashynskyy
 * @see GetUserStrategy
 */
public class GetAllUser implements GetUserStrategy {
    private static UserService userService;

    public GetAllUser(){
        userService = DefaultUserService.getInstance();
    }

    @Override
    public List<User> getUser(Pagination pagination) {

        return  userService.findAll(pagination);
    }

    @Override
    public long count() {
        return userService.count();
    }
}
