package org.palax.command.student;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.command.info.CompleteTestInfoCommand;
import org.palax.entity.CompleteTest;
import org.palax.entity.Test;
import org.palax.entity.User;
import org.palax.service.CompleteTestService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultCompleteTestService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;
import org.palax.util.SessionAttributeHelper;
import org.palax.util.impl.DefaultSessionAttributeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code SubmitTestCommand} class implements {@link Command} used for get
 * information about {@link Test}
 *
 * @author Taras Palashynskyy
 */
public class SubmitTestCommand implements Command {
    private static final Logger logger = Logger.getLogger(CompleteTestInfoCommand.class);

    private static TestService testService;
    private static CompleteTestService completeTestService;
    private static SessionAttributeHelper sessionHelper;

    public SubmitTestCommand() {
        testService = DefaultTestService.getInstance();
        completeTestService = DefaultCompleteTestService.getInstance();
        sessionHelper = DefaultSessionAttributeHelper.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.error404");
        HttpSession session = request.getSession();

        LocalDateTime submitTime = LocalDateTime.now();
        LocalDateTime startTime = (LocalDateTime) sessionHelper.getAndRemove(session, "startTestTime");
        Long startTestId = (Long) sessionHelper.getAndRemove(session, "startTestId");
        String startTestToken = sessionHelper.getAndRemove(session, "startTestToken").toString();
        String userStartTestToken = request.getParameter("startTestToken");

        User user = (User) session.getAttribute("user");

        try {
            long id = Long.parseLong(request.getParameter("id"));

            Test test = testService.findById(id).getTest();

            if (user != null && isUserCanPassTest(startTestId, startTestToken, userStartTestToken, test)) {
                Map<Long, Boolean> userAnswers = getAnswerFromRequest(request);

                CompleteTest completeTest = new CompleteTest();
                completeTest.setTest(test);
                completeTest.setStartTime(startTime);
                completeTest.setElapsedTime(calculateElapsedTime(submitTime, startTime));
                completeTest.setStudent(user);

                if (completeTestService.completeTest(completeTest, userAnswers))
                    page = PathManager.getProperty("path.redirect.complete-test-info") + completeTest.getId();
            }
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
        }

        return page;
    }

    private boolean isUserCanPassTest(Long startTestId, String startTestToken, String userStartTestToken, Test test) {
        return test.getActive() && isTestIdValid(startTestId, test) &&
                isTestTokenValid(userStartTestToken, startTestToken);
    }

    private boolean isTestIdValid(Long startTestId, Test test) {
        return startTestId.equals(test.getId());
    }

    private boolean isTestTokenValid(String userStartTestToken, String startTestToken) {
        return startTestToken.equals(userStartTestToken);
    }

    private Map<Long, Boolean> getAnswerFromRequest(HttpServletRequest request) {
        Map<Long, Boolean> userAnswers = new HashMap<>();

        Enumeration<String> parameters = request.getParameterNames();
        String param;

        while (parameters.hasMoreElements()) {
            param = parameters.nextElement();

            if(isRequiredParameter(param))
                userAnswers.put(Long.parseLong(param), request.getParameter(param) != null);

        }
        return userAnswers;
    }

    private boolean isRequiredParameter(String param) {
        return !(param.equals("command") || param.equals("id") || param.equals("startTestToken"));
    }

    private int calculateElapsedTime(LocalDateTime submitTime, LocalDateTime startTime) {
        return (int) ChronoUnit.SECONDS.between(startTime, submitTime);
    }
}
