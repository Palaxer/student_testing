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
 * The {@code ChangeUserPasswdCommand} class implements {@link Command} used for change user password
 *
 * @author Taras Palashynskyy
 */
public class ChangeUserPasswdCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(UpdateUserCommand.class);
    private static UserService userService;
    private static UserValidation userValidation;

    public ChangeUserPasswdCommand() {
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
        updateUser.setPassword(request.getParameter("passwd"));

        InvalidData.Builder builder = InvalidData.newBuilder("has-error");
        boolean invalidDataFlag = false;

        if(!userValidation.passwdValid(updateUser.getPassword())) {
            builder.setInvalidPasswdAttr();
            invalidDataFlag = true;
        }
        if(!updateUser.getPassword().equals(request.getParameter("confirmPasswd"))) {
            builder.setInvalidConfirmPasswdAttr();
            invalidDataFlag = true;
        }

        if(invalidDataFlag)
            request.setAttribute("invalidDataPasswd", builder.build());
        else if(userService.update(updateUser)) {
            request.setAttribute("updateSuccessPasswd", true);
            userService.duplicate(updateUser, user);
        } else
            request.setAttribute("invalidUpdatePasswd", true);

        return page;
    }
}
