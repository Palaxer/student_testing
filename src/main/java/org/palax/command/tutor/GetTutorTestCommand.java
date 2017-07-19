package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.command.admin.GetAllUserCommand;
import org.palax.entity.User;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;
import org.palax.util.SessionAttributeHelper;
import org.palax.util.impl.DefaultPagination;
import org.palax.util.impl.DefaultSessionAttributeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code GetTutorTestCommand} class implements {@link Command}
 * used for get all {@code test} related to {@code tutor}
 *
 * @author Taras Palashynskyy
 */
public class GetTutorTestCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(GetAllUserCommand.class);

    private static final int ELEMENT_PER_PAGE = 10;
    private static TestService testService;
    private static SessionAttributeHelper sessionHelper;

    public GetTutorTestCommand() {
        testService = DefaultTestService.getInstance();
        sessionHelper = DefaultSessionAttributeHelper.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.tutor-test");
        request.setAttribute("myTestSelect", "active");

        sessionHelper.fromSessionToRequestScope(request, "notFound");

        User user = (User)request.getSession().getAttribute("user");

        DefaultPagination pagination = new DefaultPagination(ELEMENT_PER_PAGE);
        pagination.setElementCount(testService.countByTutor(user));
        pagination.setCurrentPage(request.getParameter("page"));

        request.setAttribute("pageNumber", pagination.getPageNumber());
        request.setAttribute("currentPage", pagination.getCurrentPage());

        request.setAttribute("tests", testService.findAllByTutor(user, pagination));

        return page;
    }
}
