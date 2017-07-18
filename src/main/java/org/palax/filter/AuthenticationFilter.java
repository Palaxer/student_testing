package org.palax.filter;

import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.service.impl.DefaultUserPrincipalService;
import org.palax.util.PathManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter used for check that user authenticated.
 */
public class AuthenticationFilter implements Filter {
    private static final String USER_ATTR_NAME = "user";
    private static final String COMMAND_ATTR_NAME = "command";

    private static UserPrincipalService userPrincipalService;

    public AuthenticationFilter() {
        userPrincipalService = DefaultUserPrincipalService.getInstance();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String command = req.getParameter(COMMAND_ATTR_NAME);
        User user = getUserFromRequestSession(req);

        if(resourceRequireAuth(req.getRequestURI())) {
            resolveCommandAuth(req, res, command, user);
        }

        chain.doFilter(request, response);
    }

    private User getUserFromRequestSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = null;

        if (session != null)
            user = (User) session.getAttribute(USER_ATTR_NAME);

        return user;
    }

    private boolean resourceRequireAuth(String path) {
        boolean requireAuth = true;

        if (path.endsWith(".ico")) {
            requireAuth = false;
        } else if (path.endsWith(".css")) {
            requireAuth = false;
        }

        return requireAuth;
    }

    private void resolveCommandAuth(HttpServletRequest req, HttpServletResponse res, String command, User user)
            throws ServletException, IOException {
        if (command == null)
            forwardToBaseMapping(req, res, user);
        else {
            checkUserPermission(req, res, command, user);
        }
    }

    private void forwardToBaseMapping(HttpServletRequest req, HttpServletResponse res, User user)
            throws ServletException, IOException {
        String page;
        page = userPrincipalService.userBaseMapping(user);
        req.getRequestDispatcher(page).forward(req, res);
    }

    private void checkUserPermission(HttpServletRequest req, HttpServletResponse res, String command, User user)
            throws ServletException, IOException {
        if(!userPrincipalService.permission(user, command)) {
            String page = PathManager.getProperty("path.page.error-perm");
            req.getRequestDispatcher(page).forward(req, res);
        }
    }
}
