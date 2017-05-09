package org.palax.command.admin;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.InvalidData;
import org.palax.entity.Category;
import org.palax.service.CategoryService;
import org.palax.service.impl.DefaultCategoryService;
import org.palax.util.PathManager;
import org.palax.validation.CategoryValidation;
import org.palax.validation.impl.DefaultCategoryValidation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code UpdateCategoryCommand} class implements {@link Command} used for update category
 *
 * @author Taras Palashynskyy
 */
public class UpdateCategoryCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(UserInfoCommand.class);

    private static CategoryService categoryService;
    private static CategoryValidation categoryValidation;

    public UpdateCategoryCommand() {
        categoryService = DefaultCategoryService.getInstance();
        categoryValidation = DefaultCategoryValidation.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.redirect.categories");
        HttpSession session = request.getSession();

        try {
            long id = Long.parseLong(request.getParameter("id"));

            Category category = categoryService.findById(id);
            category.setName(request.getParameter("name"));

            InvalidData.Builder builder = InvalidData.newBuilder("has-error");
            boolean invalidDataFlag = false;

            if (!categoryValidation.nameValid(category.getName())) {
                builder.setInvalidNameAttr();
                invalidDataFlag = true;
            }

            if (invalidDataFlag) {
                session.setAttribute("invalidData", builder.build());
            } else if (categoryService.update(category)) {
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
