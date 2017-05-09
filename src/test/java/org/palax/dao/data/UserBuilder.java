package org.palax.dao.data;

import org.palax.entity.Role;
import org.palax.entity.User;

/**
 * {@code UserBuilder} class for building test data to {@link User} entity
 */
public class UserBuilder implements Builder<User> {

    private User user;

    private UserBuilder() {
        user = new User();
    }

    public static UserBuilder getBuilder() {
        return new UserBuilder();
    }

    public UserBuilder constructUser(Long template, Builder<Role> roleBuilder) {
        if(template != null) {
            user.setId(template);
            user.setLogin("login" + template);
            user.setPassword("passwd" + template);
            user.setName("name" + template);
            user.setSurname("surname" + template);
        }
        user.setRole(roleBuilder != null ? roleBuilder.build() : null);

        return this;
    }

    public UserBuilder constructUser(Long template) {
        if(template != null) {
            user.setId(template);
            user.setLogin("login" + template);
            user.setName("name" + template);
            user.setSurname("surname" + template);
        }

        return this;
    }

    @Override
    public User build() {
        return user;
    }
}
