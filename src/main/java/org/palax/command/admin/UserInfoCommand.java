package org.palax.command.admin;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.CompleteTestService;
import org.palax.service.RoleService;
import org.palax.service.UserService;
import org.palax.service.impl.DefaultCompleteTestService;
import org.palax.service.impl.DefaultRoleService;
import org.palax.service.impl.DefaultUserService;
import org.palax.util.PathManager;
import org.palax.util.SessionAttributeHelper;
import org.palax.util.impl.DefaultSessionAttributeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code UserInfoCommand} class implements {@link Command}
 * used for get user info
 *
 * @author Taras Palashynskyy
 */
public class UserInfoCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(UserInfoCommand.class);

    private static UserService userService;
    private static RoleService roleService;
    private static CompleteTestService completeTestService;
    private static SessionAttributeHelper sessionHelper;

    public UserInfoCommand() {
        userService = DefaultUserService.getInstance();
        roleService = DefaultRoleService.getInstance();
        completeTestService = DefaultCompleteTestService.getInstance();
        sessionHelper = DefaultSessionAttributeHelper.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.user-info");
        request.setAttribute("allUserSelect", "active");

        sessionHelper.fromSessionToRequestScope(request, "updateSuccess");
        sessionHelper.fromSessionToRequestScope(request, "invalidUpdate");
        sessionHelper.fromSessionToRequestScope(request, "deleteFailed");

        try {
            long id = Long.parseLong(request.getParameter("id"));

            User user = userService.findById(id);

            if(user != null) {
                request.setAttribute("user", user);

                request.setAttribute("roles", roleService.findAll());
                request.setAttribute("tests", completeTestService.findAllByStudent(user));

                return page;
            }
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
        }

        request.getSession().setAttribute("notFound", true);

        page = PathManager.getProperty("path.redirect.users");

        return page;
    }
}
