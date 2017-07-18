package org.palax.strategy;

import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.UserService;
import org.palax.service.impl.DefaultUserService;

import java.util.List;

/**
 * The {@code GetAllAdmin} class which implements {@link GetUserStrategy} interface
 * and declares the method for receiving all admin
 *
 * @author Taras Palashynskyy
 * @see GetUserStrategy
 */
public class GetAllAdmin implements GetUserStrategy {
    private static UserService userService;

    public GetAllAdmin(){
        userService = DefaultUserService.getInstance();
    }

    /**
     * {@inheritDoc}
     *
     * @return return {@link List} of admin {@link User}
     */
    @Override
    public List<User> getUser(int offSet, int numberOfElement) {

        return  userService.findAllByRole(Role.ADMIN, offSet, numberOfElement);
    }

    @Override
    public long count() {
        return userService.countByRole(Role.ADMIN);
    }
}
