package org.palax.command.auth;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.service.LoginService;
import org.palax.service.UserPrincipalService;
import org.palax.service.impl.DefaultLoginService;
import org.palax.service.impl.DefaultUserPrincipalService;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code LoginCommand} class implements {@link Command}
 * used for user login
 *
 * @author Taras Palashynskyy
 */
public class LoginCommand implements Command {
    private static LoginService loginService;
    private static UserPrincipalService userPrincipalService;

    public LoginCommand() {
        loginService = DefaultLoginService.getInstance();
        userPrincipalService = DefaultUserPrincipalService.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.login");

        String login = request.getParameter("login");
        String passwd = request.getParameter("passwd");

        if(login != null && passwd != null) {
            User user = loginService.login(login, passwd);
            if(user != null) {
                request.getSession().setAttribute("user", user);
                page = userPrincipalService.userBaseMapping(user);

                return page;
            }
        }

        request.setAttribute("loginError", true);
        request.setAttribute("login", login);

        return page;
    }
}
