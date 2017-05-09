package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.InvalidData;
import org.palax.dto.TestDTO;
import org.palax.entity.Category;
import org.palax.entity.Role;
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
import javax.servlet.http.HttpSession;

/**
 * The {@code UpdateTestCommand} class implements {@link Command} used for update test
 *
 * @author Taras Palashynskyy
 */
public class UpdateTestCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(UpdateTestCommand.class);
    private static CategoryService categoryService;
    private static TestService testService;
    private static TestValidation testValidation;

    public UpdateTestCommand() {
        categoryService = DefaultCategoryService.getInstance();
        testService = DefaultTestService.getInstance();
        testValidation = DefaultTestValidation.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.redirect.test-info") + request.getParameter("id");
        HttpSession session = request.getSession();

        try {
            TestDTO testDTO = testService.findById(Long.parseLong(request.getParameter("id")));
            Category category = categoryService.findByName(request.getParameter("category"));
            testDTO.setCategory(category);
            testDTO.setName(request.getParameter("name"));
            testDTO.setDescription(request.getParameter("desc"));
            testDTO.setPassedScore(Integer.valueOf(request.getParameter("pass-score")));
            testDTO.setPassedTime(Integer.valueOf(request.getParameter("pass-time")));
            testDTO.setId(Long.parseLong(request.getParameter("id")));
            testDTO.setQuestionCount(Integer.valueOf(request.getParameter("q-count")));

            User user = (User) session.getAttribute("user");
            if(!(testDTO.getTutor().getId().equals(user.getId()) || user.getRole() == Role.ADMIN)) {
                return PathManager.getProperty("path.page.error-perm");
            }

            InvalidData.Builder builder = InvalidData.newBuilder("has-error");
            boolean invalidDataFlag = false;

            if(!testValidation.nameValid(testDTO.getName())) {
                builder.setInvalidNameAttr();
                invalidDataFlag = true;
            }
            if(!testValidation.descriptionValid(testDTO.getDescription())) {
                builder.setInvalidDescAttr();
                invalidDataFlag = true;
            }
            if(!testValidation.passScoreValid(testDTO.getPassedScore()) ||
                    testDTO.getPassedScore() > testDTO.getQuestionCount()) {
                builder.setInvalidPassScoreAttr();
                invalidDataFlag = true;
            }
            if(!testValidation.passTimeValid(testDTO.getPassedTime())) {
                builder.setInvalidPassTimeAttr();
                invalidDataFlag = true;
            }

            if(invalidDataFlag)
                session.setAttribute("invalidData", builder.build());
            else if(testService.update(testDTO.getTest()))
                session.setAttribute("updateSuccess", true);
            else
                session.setAttribute("invalidUpdate", true);

        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
            session.setAttribute("invalidUpdate", true);
        }

        return page;
    }
}
