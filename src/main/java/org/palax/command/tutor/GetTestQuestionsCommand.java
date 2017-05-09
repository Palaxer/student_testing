package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.TestDTO;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.QuestionService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultQuestionService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;
import org.palax.util.SessionAttributeHelper;
import org.palax.util.impl.DefaultSessionAttributeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code GetTestQuestionsCommand} class implements {@link Command}
 * used for get all {@code question} related to {@code test}
 *
 * @author Taras Palashynskyy
 */
public class GetTestQuestionsCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(GetTestQuestionsCommand.class);

    private static QuestionService questionService;
    private static TestService testService;
    private static SessionAttributeHelper sessionHelper;

    public GetTestQuestionsCommand() {
        questionService = DefaultQuestionService.getInstance();
        testService = DefaultTestService.getInstance();
        sessionHelper = DefaultSessionAttributeHelper.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.questions");

        try {
            TestDTO testDTO = testService.findById(Long.parseLong(request.getParameter("id")));

            User user = (User) request.getSession().getAttribute("user");
            if(!(testDTO.getTutor().getId().equals(user.getId()) || user.getRole() == Role.ADMIN)) {
                return PathManager.getProperty("path.page.error-perm");
            }

            if(testDTO.getActive()) {
                request.setAttribute("activeTest", true);
            }

            sessionHelper.fromSessionToRequestScope(request, "addFailure");
            sessionHelper.fromSessionToRequestScope(request, "invalidData");
            sessionHelper.fromSessionToRequestScope(request, "updateSuccess");
            sessionHelper.fromSessionToRequestScope(request, "updateFailure");
            sessionHelper.fromSessionToRequestScope(request, "deleteSuccess");
            sessionHelper.fromSessionToRequestScope(request, "deleteFailure");
            sessionHelper.fromSessionToRequestScope(request, "updateQuestion");
            sessionHelper.fromSessionToRequestScope(request, "text");

            request.setAttribute("questions", questionService.findAllByTest(testDTO.getTest()));
            request.setAttribute("test", testDTO);


        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
            page = PathManager.getProperty("path.page.error404");
        }

        return page;
    }
}
