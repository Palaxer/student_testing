package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.TestDTO;
import org.palax.entity.Question;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.AnswerService;
import org.palax.service.QuestionService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultAnswerService;
import org.palax.service.impl.DefaultQuestionService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;
import org.palax.util.SessionAttributeHelper;
import org.palax.util.impl.DefaultSessionAttributeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code GetTestQuestionsCommand} class implements {@link Command}
 * used for get all {@code question} related to {@code test}
 *
 * @author Taras Palashynskyy
 */
public class GetQuestionAnswersCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(GetQuestionAnswersCommand.class);

    private static QuestionService questionService;
    private static AnswerService answerService;
    private static TestService testService;
    private static SessionAttributeHelper sessionHelper;

    public GetQuestionAnswersCommand() {
        questionService = DefaultQuestionService.getInstance();
        answerService = DefaultAnswerService.getInstance();
        testService = DefaultTestService.getInstance();
        sessionHelper = DefaultSessionAttributeHelper.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.question-info");
        HttpSession session = request.getSession();

        try {
            Question question = questionService.findById(Long.parseLong(request.getParameter("id")));

            TestDTO testDTO = testService.findById(question.getTest().getId());

            User user = (User) session.getAttribute("user");
            if(!(testDTO.getTutor().getId().equals(user.getId()) || user.getRole() == Role.ADMIN)) {
                return PathManager.getProperty("path.page.error-perm");
            }

            if(testDTO.getActive()) {
                request.setAttribute("activeTest", true);
            }

            sessionHelper.fromSessionToRequestScope(request, "addFailure");
            sessionHelper.fromSessionToRequestScope(request, "updateSuccess");
            sessionHelper.fromSessionToRequestScope(request, "addSuccess");
            sessionHelper.fromSessionToRequestScope(request, "invalidData");
            sessionHelper.fromSessionToRequestScope(request, "deleteFailure");
            sessionHelper.fromSessionToRequestScope(request, "updateFailure");
            sessionHelper.fromSessionToRequestScope(request, "deleteSuccess");
            sessionHelper.fromSessionToRequestScope(request, "updateAnswers");
            sessionHelper.fromSessionToRequestScope(request, "text");

            request.setAttribute("answers", answerService.findAllByQuestion(question));
            request.setAttribute("question", question);


        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
            page = PathManager.getProperty("path.page.error404");
        }

        return page;
    }
}
