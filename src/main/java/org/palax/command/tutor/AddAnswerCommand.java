package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.InvalidData;
import org.palax.dto.TestDTO;
import org.palax.entity.Answer;
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
import org.palax.validation.AnswerValidation;
import org.palax.validation.impl.DefaultAnswerValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code AddAnswerCommand} class implements {@link Command}
 * used for add {@code answer} to {@code question}
 *
 * @author Taras Palashynskyy
 */
public class AddAnswerCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(AddAnswerCommand.class);

    private static QuestionService questionService;
    private static TestService testService;
    private static AnswerValidation answerValidation;
    private static AnswerService answerService;

    public AddAnswerCommand() {
        questionService = DefaultQuestionService.getInstance();
        testService = DefaultTestService.getInstance();
        answerValidation = DefaultAnswerValidation.getInstance();
        answerService = DefaultAnswerService.getInstance();
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

            page = PathManager.getProperty("path.redirect.question-info") + question.getId();

            TestDTO testDTO = testService.findById(question.getTest().getId());

            User user = (User) session.getAttribute("user");
            if(!(testDTO.getTutor().getId().equals(user.getId()) || user.getRole() == Role.ADMIN)) {
                return PathManager.getProperty("path.page.error-perm");
            }

            if(testDTO.getActive()) {
                return page;
            }

            Answer answer = new Answer();
            answer.setQuestion(question);
            answer.setCorrect(false);
            answer.setText(request.getParameter("text"));

            InvalidData.Builder builder = InvalidData.newBuilder("has-error");
            boolean invalidDataFlag = false;

            if(!answerValidation.textValid(answer.getText())) {
                builder.setInvalidTextAttr();
                invalidDataFlag = true;
            }

            if (invalidDataFlag) {
                session.setAttribute("invalidData", builder.build());
                session.setAttribute("text", answer.getText());
            } else if (answerService.create(answer)) {
                session.setAttribute("addSuccess", true);
            } else
                session.setAttribute("addFailure", true);


        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
            session.setAttribute("addFailure", true);
        }

        return page;
    }
}
