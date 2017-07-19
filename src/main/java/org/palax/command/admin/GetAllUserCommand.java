package org.palax.command.admin;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.strategy.GetUser;
import org.palax.util.PathManager;
import org.palax.util.SessionAttributeHelper;
import org.palax.util.impl.DefaultPagination;
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

        String role = request.getParameter("role");
        request.setAttribute("role", role);

        GetUser userStrategy = new GetUser(role);

        DefaultPagination pagination = new DefaultPagination(ELEMENT_PER_PAGE);
        pagination.setElementCount(userStrategy.count());
        pagination.setCurrentPage(request.getParameter("page"));

        request.setAttribute("pageNumber", pagination.getPageNumber());
        request.setAttribute("currentPage", pagination.getCurrentPage());

        request.setAttribute("users", userStrategy.getUser(pagination));

        return page;
    }
}
