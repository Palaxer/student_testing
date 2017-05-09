package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.command.admin.GetAllUserCommand;
import org.palax.entity.User;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;
import org.palax.util.SessionAttributeHelper;
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

        int currentPage = 1;

        User user = (User)request.getSession().getAttribute("user");

        try {
            if(request.getParameter("page") != null)
                currentPage = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
        }

        long count = testService.countByTutor(user);

        int pageNumber = (int) Math.ceil(count * 1.0 / ELEMENT_PER_PAGE);

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("currentPage", currentPage);

        request.setAttribute("tests", testService.findAllByTutor(user, currentPage * ELEMENT_PER_PAGE - ELEMENT_PER_PAGE,
                ELEMENT_PER_PAGE));

        return page;
    }
}
