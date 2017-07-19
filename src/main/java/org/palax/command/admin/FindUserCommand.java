package org.palax.command.admin;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserService;
import org.palax.service.impl.DefaultUserService;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code FindUserCommand} class implements {@link Command}
 * used for find user by {@code login}
 *
 * @author Taras Palashynskyy
 */
public class FindUserCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(FindUserCommand.class);

    private static UserService userService;

    public FindUserCommand() {
        userService = DefaultUserService.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.redirect.users");
        request.setAttribute("allUserSelect", "active");

        String login = request.getParameter("login");

        if(checkLogin(login)) {
            User user = userService.findByLogin(login);

            if(user != null) {
                page = PathManager.getProperty("path.redirect.user-info") + user.getId();

                return page;
            }
        }

        request.getSession().setAttribute("notFound", true);

        return page;
    }

    private boolean checkLogin(String login) {
        return login != null && !login.isEmpty();
    }
}
