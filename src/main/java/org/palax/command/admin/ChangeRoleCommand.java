package org.palax.command.admin;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.UserService;
import org.palax.service.impl.DefaultUserService;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code ChangeRoleCommand} class implements {@link Command} used for change user role
 *
 * @author Taras Palashynskyy
 */
public class ChangeRoleCommand implements Command {
    private static final Logger logger = Logger.getLogger(UserInfoCommand.class);

    private static UserService userService;

    public ChangeRoleCommand() {
        userService = DefaultUserService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.redirect.users");
        HttpSession session = request.getSession();

        try {
            long id = Long.parseLong(request.getParameter("id"));
            page = PathManager.getProperty("path.redirect.user-info") + id;

            Role role = Role.valueOf(request.getParameter("role"));
            User user = userService.findById(id);

            if(user != null) {
                if (userService.changeRole(user, role))
                    session.setAttribute("updateSuccess", true);
                else
                    session.setAttribute("invalidUpdate", true);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Threw a IllegalArgumentException, full stack trace follows:", e);
        }

        return page;
    }
}
