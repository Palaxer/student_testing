package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.InvalidData;
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
import org.palax.validation.AnswerValidation;
import org.palax.validation.TestValidation;
import org.palax.validation.impl.DefaultAnswerValidation;
import org.palax.validation.impl.DefaultTestValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The {@code UpdateAnswersCommand} class implements {@link Command}
 * used for update {@code answers}
 *
 * @author Taras Palashynskyy
 */
public class UpdateAnswersCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(UpdateAnswersCommand.class);

    private static QuestionService questionService;
    private static TestService testService;
    private static AnswerValidation answerValidation;
    private static AnswerService answerService;
    private static TestValidation testValidation;


    public UpdateAnswersCommand() {
        questionService = DefaultQuestionService.getInstance();
        testService = DefaultTestService.getInstance();
        answerValidation = DefaultAnswerValidation.getInstance();
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
            Question question = questionService.findById(Long.parseLong(request.getParameter("id")));
            question.setAnswers(answerService.findAllByQuestion(question));
            TestDTO testDTO = testService.findById(question.getTest().getId());
            User user = (User) session.getAttribute("user");

            if(!testValidation.isUserAllowedToEditTest(testDTO, user))
                return PathManager.getProperty("path.page.error-perm");

            page = PathManager.getProperty("path.redirect.question-info") + question.getId();

            if(testDTO.getActive())
                return page;

            InvalidData invalidData = checkAnswersValidity(question.getAnswers(), request);

            if(invalidData != null) {
                session.setAttribute("invalidData", invalidData);
                session.setAttribute("updateAnswers", question.getAnswers());
            } else if (answerService.update(question.getAnswers())) {
                session.setAttribute("updateSuccess", true);
            } else
                session.setAttribute("updateFailure", true);


        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
            session.setAttribute("updateFailure", true);
        }

        return page;
    }

    private InvalidData checkAnswersValidity(List<Answer> answers, HttpServletRequest request) {
        InvalidData.Builder builder = InvalidData.newBuilder("has-error");
        boolean invalidDataFlag = false;

        for(Answer answer : answers) {
            answer.setCorrect(request.getParameter("correct" + answer.getId()) != null);
            answer.setText(request.getParameter("text" + answer.getId()));

            if(!answerValidation.textValid(answer.getText())) {
                builder.setInvalidAnswerId(answer.getId());
                invalidDataFlag = true;
            }
        }

        return invalidDataFlag ? builder.build() : null;
    }
}
