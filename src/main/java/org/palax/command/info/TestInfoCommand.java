package org.palax.command.info;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.TestDTO;
import org.palax.entity.CompleteTest;
import org.palax.entity.Role;
import org.palax.entity.Test;
import org.palax.entity.User;
import org.palax.service.CategoryService;
import org.palax.service.CompleteTestService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultCategoryService;
import org.palax.service.impl.DefaultCompleteTestService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;
import org.palax.util.SessionAttributeHelper;
import org.palax.util.impl.DefaultSessionAttributeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The {@code TestInfoCommand} class implements {@link Command} used for get
 * information about {@link Test}
 *
 * @author Taras Palashynskyy
 */
public class TestInfoCommand implements Command {
    private static final Logger logger = Logger.getLogger(CompleteTestInfoCommand.class);

    private static TestService testService;
    private static CompleteTestService completeTestService;
    private static CategoryService categoryService;
    private static SessionAttributeHelper sessionHelper;

    public TestInfoCommand() {
        testService = DefaultTestService.getInstance();
        completeTestService = DefaultCompleteTestService.getInstance();
        categoryService = DefaultCategoryService.getInstance();
        sessionHelper = DefaultSessionAttributeHelper.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.test-info");

        sessionHelper.fromSessionToRequestScope(request, "updateSuccess");
        sessionHelper.fromSessionToRequestScope(request, "invalidUpdate");
        sessionHelper.fromSessionToRequestScope(request, "invalidData");

        try {
            long id = Long.parseLong(request.getParameter("id"));

            TestDTO testDTO = testService.findById(id);
            List<CompleteTest> completeTests = completeTestService.findAllByTest(testDTO.getTest());
            User user = (User)request.getSession().getAttribute("user");

            if(isUserAllowedToEditTest(testDTO, user))
                request.setAttribute("permission", true);

            request.setAttribute("categories", categoryService.findAll());
            request.setAttribute("completeTests", completeTests);
            request.setAttribute("test", testDTO);
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
        }

        return page;
    }

    private boolean isUserAllowedToEditTest(TestDTO testDTO, User user) {
        return isUserOwnerOfTest(testDTO, user) || isAdmin(user);
    }

    private boolean isAdmin(User user) {
        return user.getRole() == Role.ADMIN;
    }

    private boolean isUserOwnerOfTest(TestDTO testDTO, User user) {
        return testDTO.getTutor().getId().equals(user.getId());
    }
}
