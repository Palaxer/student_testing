package org.palax.command.admin;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.service.CategoryService;
import org.palax.service.impl.DefaultCategoryService;
import org.palax.util.PathManager;
import org.palax.util.SessionAttributeHelper;
import org.palax.util.impl.DefaultSessionAttributeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code GetAllCategoryCommand} class implements {@link Command}
 * used for get all category
 *
 * @author Taras Palashynskyy
 */
public class GetAllCategoryCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(GetAllCategoryCommand.class);

    private static CategoryService categoryService;
    private static SessionAttributeHelper sessionHelper;

    public GetAllCategoryCommand() {
        categoryService = DefaultCategoryService.getInstance();
        sessionHelper = DefaultSessionAttributeHelper.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.categories");
        request.setAttribute("allCategorySelect", "active");

        sessionHelper.fromSessionToRequestScope(request, "updateSuccess");
        sessionHelper.fromSessionToRequestScope(request, "updateFailure");
        sessionHelper.fromSessionToRequestScope(request, "deleteSuccess");
        sessionHelper.fromSessionToRequestScope(request, "deleteFailure");
        sessionHelper.fromSessionToRequestScope(request, "invalidData");

        request.setAttribute("categories", categoryService.findAll());

        return page;
    }
}
