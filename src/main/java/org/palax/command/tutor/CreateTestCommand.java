package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.InvalidData;
import org.palax.entity.Category;
import org.palax.entity.Test;
import org.palax.entity.User;
import org.palax.service.CategoryService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultCategoryService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;
import org.palax.validation.TestValidation;
import org.palax.validation.impl.DefaultTestValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code CreateTestCommand} class implements {@link Command}
 * used for create test
 *
 * @author Taras Palashynskyy
 */
public class CreateTestCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(CreateTestCommand.class);
    private static CategoryService categoryService;
    private static TestService testService;
    private static TestValidation testValidation;

    public CreateTestCommand() {
        categoryService = DefaultCategoryService.getInstance();
        testService = DefaultTestService.getInstance();
        testValidation = DefaultTestValidation.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.create-test");

        try {
            request.setAttribute("categories", categoryService.findAll());

            Test test = new Test();
            Category category = categoryService.findByName(request.getParameter("category"));
            test.setCategory(category);
            test.setName(request.getParameter("name"));
            test.setDescription(request.getParameter("desc"));
            test.setPassedTime(Integer.valueOf(request.getParameter("pass-time")));

            User user = (User) request.getSession().getAttribute("user");
            test.setTutor(user);

            InvalidData invalidData = checkTestValidity(test);

            if(invalidData != null) {
                request.setAttribute("createTestSelect", "active");
                request.setAttribute("invalidData", invalidData);
                request.setAttribute("test", test);
            } else if (testService.create(test)) {
                page = PathManager.getProperty("path.redirect.test-info") + test.getId();
            } else
                request.setAttribute("createFailure", true);
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
            request.setAttribute("createFailure", true);
        }

        return page;
    }

    private InvalidData checkTestValidity(Test test) {
        InvalidData.Builder builder = InvalidData.newBuilder("has-error");
        boolean invalidDataFlag = false;

        if (!testValidation.nameValid(test.getName())) {
            builder.setInvalidNameAttr();
            invalidDataFlag = true;
        }
        if (!testValidation.descriptionValid(test.getDescription())) {
            builder.setInvalidDescAttr();
            invalidDataFlag = true;
        }
        if (!testValidation.passTimeValid(test.getPassedTime())) {
            builder.setInvalidPassTimeAttr();
            invalidDataFlag = true;
        }

        return invalidDataFlag ? builder.build() : null;
    }
}
