package org.palax.command.info;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.TestDTO;
import org.palax.entity.CompleteTest;
import org.palax.service.CompleteTestService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultCompleteTestService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code CompleteTestInfoCommand} class implements {@link Command} used for get
 * information about user {@link CompleteTest}
 *
 * @author Taras Palashynskyy
 */
public class CompleteTestInfoCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(CompleteTestInfoCommand.class);

    private static TestService testService;
    private static CompleteTestService completeTestService;

    public CompleteTestInfoCommand() {
        testService = DefaultTestService.getInstance();
        completeTestService = DefaultCompleteTestService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.complete-test-info");

        try {
            long id = Long.parseLong(request.getParameter("id"));

            CompleteTest completeTest = completeTestService.findById(id);
            TestDTO test = testService.findById(completeTest.getTest().getId());

            request.setAttribute("completeTest", completeTest);
            request.setAttribute("test", test);
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
        }

        return page;
    }
}
