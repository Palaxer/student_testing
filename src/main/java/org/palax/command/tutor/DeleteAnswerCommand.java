package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.TestDTO;
import org.palax.entity.Answer;
import org.palax.entity.Question;
import org.palax.entity.User;
import org.palax.service.AnswerService;
import org.palax.service.QuestionService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultAnswerService;
import org.palax.service.impl.DefaultQuestionService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;
import org.palax.validation.TestValidation;
import org.palax.validation.impl.DefaultTestValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code DeleteAnswerCommand} class implements {@link Command}
 * used for delete {@code answer}
 *
 * @author Taras Palashynskyy
 */
public class DeleteAnswerCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DeleteAnswerCommand.class);

    private static QuestionService questionService;
    private static TestService testService;
    private static AnswerService answerService;
    private static TestValidation testValidation;


    public DeleteAnswerCommand() {
        questionService = DefaultQuestionService.getInstance();
        testService = DefaultTestService.getInstance();
        answerService = DefaultAnswerService.getInstance();
        testValidation = DefaultTestValidation.getInstance();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        HttpSession session = request.getSession();

        try {
            Answer answer = answerService.findById(Long.parseLong(request.getParameter("id")));
            Question question = questionService.findById(answer.getQuestion().getId());

            page = PathManager.getProperty("path.redirect.question-info") + question.getId();

            TestDTO testDTO = testService.findById(question.getTest().getId());
            User user = (User) session.getAttribute("user");

            if(!testValidation.isUserAllowedToEditTest(testDTO, user))
                return PathManager.getProperty("path.page.error-perm");

            if(testDTO.getActive())
                return page;

            if (answerService.delete(answer)) {
                session.setAttribute("deleteSuccess", true);
            } else
                session.setAttribute("deleteFailure", true);


        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
            session.setAttribute("deleteFailure", true);
        }

        return page;
    }
}
