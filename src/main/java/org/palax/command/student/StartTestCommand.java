package org.palax.command.student;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.command.info.CompleteTestInfoCommand;
import org.palax.entity.Question;
import org.palax.entity.Test;
import org.palax.exception.PageNotFoundException;
import org.palax.service.AnswerService;
import org.palax.service.QuestionService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultAnswerService;
import org.palax.service.impl.DefaultQuestionService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * The {@code StartTestCommand} class implements {@link Command} used for get
 * information about {@link Test}
 *
 * @author Taras Palashynskyy
 */
public class StartTestCommand implements Command {
    private static final Logger logger = Logger.getLogger(CompleteTestInfoCommand.class);

    private static TestService testService;
    private static QuestionService questionService;
    private static AnswerService answerService;
    private static Random random;

    public StartTestCommand() {
        testService = DefaultTestService.getInstance();
        questionService = DefaultQuestionService.getInstance();
        answerService = DefaultAnswerService.getInstance();
        random = new Random();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.test");
        HttpSession session = request.getSession();

        try {
            long id = Long.parseLong(request.getParameter("id"));

            Test test = getTest(id);

            random.setSeed(System.nanoTime());

            request.setAttribute("test", test);
            session.setAttribute("startTestId", test.getId());
            session.setAttribute("startTestToken", random.nextLong());
            session.setAttribute("startTestTime", LocalDateTime.now());
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
        }

        return page;
    }

    private Test getTest(long id) {
        Test test = testService.findById(id).getTest();

        if(!test.getActive())
            throw new PageNotFoundException("Trying to start an inactive test");

        test.setQuestions(getTestQuestions(test));

        return test;
    }

    private List<Question> getTestQuestions(Test test) {
        List<Question> questions = questionService.findAllByTest(test);

        for (Question question : questions)
            question.setAnswers(answerService.findAllByQuestion(question));

        return questions;
    }
}
