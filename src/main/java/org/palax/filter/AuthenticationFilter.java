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
    private static UserPrincipalService userPrincipalService;

    public AuthenticationFilter() {
        userPrincipalService = DefaultUserPrincipalService.getInstance();
    }

    /**
     * Checks that user authenticated
     *
     * @param request  {@code request} all http request represent by {@link ServletRequest}
     * @param response {@code response} all http response represent by {@link ServletResponse}
     * @param chain    {@code chain} filter chain represents by {@link FilterChain}
     * @throws IOException      {@link IOException}
     * @throws ServletException {@link ServletException}
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String command = req.getParameter("command");
        String page;
        String path = req.getRequestURI();

        User user = null;
        if (session != null)
            user = (User) session.getAttribute("user");

        if (path.endsWith(".ico")) {
            chain.doFilter(request, response);
            return;
        } else if (path.endsWith(".css")) {
            chain.doFilter(request, response);
            return;
        }

        if (command == null) {
            page = userPrincipalService.userBaseMapping(user);
            req.getRequestDispatcher(page).forward(req, res);
        } else if (!userPrincipalService.permission(user, command)) {
            page = PathManager.getProperty("path.page.error-perm");
            req.getRequestDispatcher(page).forward(req, res);
        }

        chain.doFilter(request, response);
    }
}
