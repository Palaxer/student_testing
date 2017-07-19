package org.palax.command.admin;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.UserService;
import org.palax.service.impl.DefaultUserService;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code DeleteUserCommand} class implements {@link Command} used for delete user
 * and his completed tests
 *
 * @author Taras Palashynskyy
 */
public class DeleteUserCommand implements Command {
    private static final Logger logger = Logger.getLogger(DeleteUserCommand.class);

    private static UserService userService;

    public DeleteUserCommand() {
        userService = DefaultUserService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.redirect.users");
        HttpSession session = request.getSession();

        try {
            long id = Long.parseLong(request.getParameter("id"));

            User user = userService.findById(id);
            User admin = (User) session.getAttribute("user");

            if(user != null) {
                if (userService.delete(user, admin))
                    session.setAttribute("deleteSuccess", true);
                else {
                    session.setAttribute("deleteFailed", true);
                    page = PathManager.getProperty("path.redirect.user-info") + id;
                }
            }
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
        }

        return page;
    }
}
