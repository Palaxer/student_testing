package org.palax.command.auth;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.InvalidData;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.UserService;
import org.palax.service.impl.DefaultUserService;
import org.palax.util.PathManager;
import org.palax.validation.UserValidation;
import org.palax.validation.impl.DefaultUserValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code RegistrationCommand} class implements {@link Command}
 * used for registration new user
 *
 * @author Taras Palashynskyy
 */

public class RegistrationCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(RegistrationCommand.class);
    private static UserService userService;
    private static UserValidation userValidation;

    public RegistrationCommand() {
        userService = DefaultUserService.getInstance();
        userValidation = DefaultUserValidation.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.registration");

        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("passwd"));
        user.setName(request.getParameter("name"));
        user.setSurname(request.getParameter("surname"));
        user.setRole(Role.STUDENT);

        InvalidData.Builder builder = InvalidData.newBuilder("has-error");
        boolean invalidDataFlag = false;

        if(!userValidation.loginValid(user.getLogin())){
            builder.setInvalidLoginAttr();
            invalidDataFlag = true;
        }
        if(!userValidation.nameValid(user.getName())) {
            builder.setInvalidNameAttr();
            invalidDataFlag = true;
        }
        if(!userValidation.surnameValid(user.getSurname())) {
            builder.setInvalidSurnameAttr();
            invalidDataFlag = true;
        }
        if(!userValidation.passwdValid(user.getPassword())) {
            builder.setInvalidPasswdAttr();
            invalidDataFlag = true;
        }
        if(!user.getPassword().equals(request.getParameter("confirmPasswd"))) {
            builder.setInvalidConfirmPasswdAttr();
            invalidDataFlag = true;
        }

        if(invalidDataFlag)
            request.setAttribute("invalidData", builder.build());
        else if(userService.create(user)) {
            page = PathManager.getProperty("path.page.login");
            request.setAttribute("login", user.getLogin());
        } else
            request.setAttribute("loginExist", true);

        request.setAttribute("user", user);

        return page;
    }
}
