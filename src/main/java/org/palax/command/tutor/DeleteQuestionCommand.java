package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.TestDTO;
import org.palax.entity.Question;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.QuestionService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultQuestionService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code AddQuestionCommand} class implements {@link Command}
 * used for delete {@code question}
 *
 * @author Taras Palashynskyy
 */
public class DeleteQuestionCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(GetTestQuestionsCommand.class);

    private static QuestionService questionService;
    private static TestService testService;

    public DeleteQuestionCommand() {
        questionService = DefaultQuestionService.getInstance();
        testService = DefaultTestService.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = null;
        HttpSession session = request.getSession();

        try {
            Question question = questionService.findById(Long.parseLong(request.getParameter("id")));

            TestDTO testDTO = testService.findById(question.getTest().getId());

            User user = (User) session.getAttribute("user");
            if(!(testDTO.getTutor().getId().equals(user.getId()) || user.getRole() == Role.ADMIN)) {
                return PathManager.getProperty("path.page.error-perm");
            }

            page = PathManager.getProperty("path.redirect.questions") + testDTO.getId();

            if(testDTO.getActive()) {
                return page;
            }


            if (questionService.delete(question)) {
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
