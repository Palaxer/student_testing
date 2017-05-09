package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.InvalidData;
import org.palax.dto.TestDTO;
import org.palax.entity.Question;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.QuestionService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultQuestionService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;
import org.palax.validation.QuestionValidation;
import org.palax.validation.impl.DefaultQuestionValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code AddQuestionCommand} class implements {@link Command}
 * used for add {@code question} to {@code test}
 *
 * @author Taras Palashynskyy
 */
public class AddQuestionCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(GetTestQuestionsCommand.class);

    private static QuestionService questionService;
    private static TestService testService;
    private static QuestionValidation questionValidation;

    public AddQuestionCommand() {
        questionService = DefaultQuestionService.getInstance();
        testService = DefaultTestService.getInstance();
        questionValidation = DefaultQuestionValidation.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.redirect.questions") + request.getParameter("id");
        HttpSession session = request.getSession();

        try {
            TestDTO testDTO = testService.findById(Long.parseLong(request.getParameter("id")));

            User user = (User) session.getAttribute("user");
            if(!(testDTO.getTutor().getId().equals(user.getId()) || user.getRole() == Role.ADMIN)) {
                return PathManager.getProperty("path.page.error-perm");
            }

            if(testDTO.getActive()) {
                return page;
            }

            Question question = new Question();
            question.setTest(testDTO.getTest());
            question.setText(request.getParameter("text"));

            InvalidData.Builder builder = InvalidData.newBuilder("has-error");
            boolean invalidDataFlag = false;

            if(!questionValidation.textValid(question.getText())) {
                builder.setInvalidTextAttr();
                invalidDataFlag = true;
            }

            if (invalidDataFlag) {
                session.setAttribute("invalidData", builder.build());
                session.setAttribute("text", question.getText());
            } else if (questionService.create(question)) {
                page = PathManager.getProperty("path.redirect.question-info") + question.getId();
            } else
                session.setAttribute("addFailure", true);


        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
            session.setAttribute("addFailure", true);
        }

        return page;
    }
}
