package org.palax.command.tutor;

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

/**
 * The {@code CreateCategoryCommand} class implements {@link Command}
 * used for create category
 *
 * @author Taras Palashynskyy
 */
public class CreateCategoryCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(CreateCategoryCommand.class);
    private static CategoryService categoryService;
    private static CategoryValidation categoryValidation;

    public CreateCategoryCommand() {
        categoryService = DefaultCategoryService.getInstance();
        categoryValidation = DefaultCategoryValidation.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.create-category");
        request.setAttribute("createCategorySelect", "active");

        try {
            Category category = new Category();
            category.setName(request.getParameter("name"));

            InvalidData invalidData = checkCategoryValidity(category);

            if(invalidData != null) {
                request.setAttribute("invalidData", invalidData);
                request.setAttribute("name", category.getName());
            } else if (categoryService.create(category)) {
                request.setAttribute("createSuccess", true);
            } else
                request.setAttribute("createFailure", true);
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
            request.setAttribute("createFailure", true);
        }

        return page;
    }

    private InvalidData checkCategoryValidity(Category category) {
        InvalidData.Builder builder = InvalidData.newBuilder("has-error");
        boolean invalidDataFlag = false;

        if (!categoryValidation.nameValid(category.getName())) {
            builder.setInvalidNameAttr();
            invalidDataFlag = true;
        }

        return invalidDataFlag ? builder.build() : null;
    }
}
