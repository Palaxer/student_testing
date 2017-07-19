package org.palax.command.student;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.command.info.CompleteTestInfoCommand;
import org.palax.entity.CompleteTest;
import org.palax.entity.User;
import org.palax.service.CompleteTestService;
import org.palax.service.impl.DefaultCompleteTestService;
import org.palax.util.PathManager;
import org.palax.util.impl.DefaultPagination;

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

        User user = (User)request.getSession().getAttribute("user");

        DefaultPagination pagination = new DefaultPagination(ELEMENT_PER_PAGE);
        pagination.setElementCount(completeTestService.countByUser(user));
        pagination.setCurrentPage(request.getParameter("page"));

        request.setAttribute("pageNumber", pagination.getPageNumber());
        request.setAttribute("currentPage", pagination.getCurrentPage());

        request.setAttribute("completeTests", completeTestService.findAllByStudent(user, pagination));

        return page;
    }
}
