package org.palax.command.admin;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.exception.PageNotFoundException;
import org.palax.strategy.*;
import org.palax.util.PathManager;
import org.palax.util.SessionAttributeHelper;
import org.palax.util.impl.DefaultSessionAttributeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code GetAllUserCommand} class implements {@link Command}
 * used for get all user
 *
 * @author Taras Palashynskyy
 */
public class GetAllUserCommand implements Command {
    private static final Logger logger = Logger.getLogger(GetAllUserCommand.class);
    private static final int ELEMENT_PER_PAGE = 10;

    private static SessionAttributeHelper sessionHelper;

    public GetAllUserCommand() {
        sessionHelper = DefaultSessionAttributeHelper.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.users");
        request.setAttribute("allUserSelect", "active");

        sessionHelper.fromSessionToRequestScope(request, "notFound");
        sessionHelper.fromSessionToRequestScope(request, "deleteSuccess");

        int currentPage = 1;

        try {
            if(request.getParameter("page") != null)
                currentPage = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
        }

        String role = request.getParameter("role");
        request.setAttribute("role", role);

        GetUser userStrategy = new GetUser(role);

        long count = userStrategy.count();

        int pageNumber = (int) Math.ceil(count * 1.0 / ELEMENT_PER_PAGE);

        if(currentPage > pageNumber)
            throw new PageNotFoundException("The specified page was not found");

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("currentPage", currentPage);

        request.setAttribute("users", userStrategy.getUser(currentPage * ELEMENT_PER_PAGE - ELEMENT_PER_PAGE,
                ELEMENT_PER_PAGE));

        return page;
    }
}
