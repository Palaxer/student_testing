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
    private GetUserStrategy strategy;

    public GetUser(String role) {
        switch (role.toUpperCase()) {
            case "ALL" :
                strategy = new GetAllUser();
                break;
            case "ADMIN" :
                strategy = new GetAllAdmin();
                break;
            case "TUTOR" :
                strategy = new GetAllTutor();
                break;
            case "STUDENT" :
                strategy = new GetAllStudent();
                break;
            default:
                throw new IllegalArgumentException(role + " - role does not exist");
        }
    }

    public List<User> getUser(int offSet, int numberOfElement) {
        return strategy.getUser(offSet, numberOfElement);
    }

    @Override
    public long count() {
        return strategy.count();
    }


}
