package org.palax.command.profile;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.InvalidData;
import org.palax.entity.User;
import org.palax.service.UserService;
import org.palax.service.impl.DefaultUserService;
import org.palax.util.PathManager;
import org.palax.validation.UserValidation;
import org.palax.validation.impl.DefaultUserValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code UpdateUserCommand} class implements {@link Command} used for update user
 *
 * @author Taras Palashynskyy
 */
public class UpdateUserCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(UpdateUserCommand.class);
    private static UserService userService;
    private static UserValidation userValidation;

    public UpdateUserCommand() {
        userService = DefaultUserService.getInstance();
        userValidation = DefaultUserValidation.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.profile");
        request.setAttribute("profileSelect", "active");

        User user = (User) request.getSession().getAttribute("user");

        User updateUser = new User();
        userService.duplicate(user,updateUser);
        updateUser.setName(request.getParameter("name"));
        updateUser.setSurname(request.getParameter("surname"));

        InvalidData.Builder builder = InvalidData.newBuilder("has-error");
        boolean invalidDataFlag = false;

        if(!userValidation.nameValid(updateUser.getName())) {
            builder.setInvalidNameAttr();
            invalidDataFlag = true;
        }
        if(!userValidation.surnameValid(updateUser.getSurname())) {
            builder.setInvalidSurnameAttr();
            invalidDataFlag = true;
        }

        if(invalidDataFlag)
            request.setAttribute("invalidData", builder.build());
        else if(userService.update(updateUser)) {
            request.setAttribute("updateSuccess", true);
            userService.duplicate(updateUser, user);
        } else
            request.setAttribute("invalidUpdate", true);

        return page;
    }
}
