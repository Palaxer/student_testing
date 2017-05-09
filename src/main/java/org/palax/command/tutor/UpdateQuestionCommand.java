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
 * The {@code UpdateQuestionCommand} class implements {@link Command}
 * used for update {@code question}
 *
 * @author Taras Palashynskyy
 */
public class UpdateQuestionCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(GetTestQuestionsCommand.class);

    private static QuestionService questionService;
    private static TestService testService;
    private static QuestionValidation questionValidation;

    public UpdateQuestionCommand() {
        questionService = DefaultQuestionService.getInstance();
        testService = DefaultTestService.getInstance();
        questionValidation = DefaultQuestionValidation.getInstance();
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
            question.setText(request.getParameter("text"));

            TestDTO testDTO = testService.findById(question.getTest().getId());

            User user = (User) session.getAttribute("user");
            if(!(testDTO.getTutor().getId().equals(user.getId()) || user.getRole() == Role.ADMIN)) {
                return PathManager.getProperty("path.page.error-perm");
            }

            page = PathManager.getProperty("path.redirect.questions") + testDTO.getId();

            if(testDTO.getActive()) {
                return page;
            }

            InvalidData.Builder builder = InvalidData.newBuilder("has-error");
            boolean invalidDataFlag = false;

            if(!questionValidation.textValid(question.getText())) {
                builder.setInvalidTextAttr();
                invalidDataFlag = true;
            }

            if (invalidDataFlag) {
                session.setAttribute("invalidData", builder.build());
                session.setAttribute("updateQuestion", question);
            } else if (questionService.update(question)) {
                session.setAttribute("updateSuccess", true);
            } else
                session.setAttribute("updateFailure", true);


        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
            session.setAttribute("updateFailure", true);
        }

        return page;
    }
}
