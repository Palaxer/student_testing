package org.palax.command.student;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.command.info.CompleteTestInfoCommand;
import org.palax.entity.CompleteTest;
import org.palax.entity.User;
import org.palax.service.CompleteTestService;
import org.palax.service.impl.DefaultCompleteTestService;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code TestHistoryCommand} class implements {@link Command} used for get
 * information about user {@link CompleteTest}
 *
 * @author Taras Palashynskyy
 */
public class TestHistoryCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(CompleteTestInfoCommand.class);

    private static final int ELEMENT_PER_PAGE = 10;
    private static CompleteTestService completeTestService;

    public TestHistoryCommand() {
        completeTestService = DefaultCompleteTestService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.test-history");
        request.setAttribute("testHistorySelect", "active");

        int currentPage = 1;

        try {
            if(request.getParameter("page") != null)
                currentPage = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
        }

        User user = (User)request.getSession().getAttribute("user");

        long count = completeTestService.countByUser(user);

        int pageNumber = (int) Math.ceil(count * 1.0 / ELEMENT_PER_PAGE);

        if(currentPage > pageNumber && pageNumber != 0)
            return PathManager.getProperty("path.page.error404");

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("currentPage", currentPage);

        request.setAttribute("completeTests", completeTestService.findAllByStudent(user, currentPage * ELEMENT_PER_PAGE - ELEMENT_PER_PAGE,
                ELEMENT_PER_PAGE));

        return page;
    }
}
