package org.palax.command.admin;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.Category;
import org.palax.service.CategoryService;
import org.palax.service.impl.DefaultCategoryService;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code DeleteCategoryCommand} class implements {@link Command} used for delete category
 *
 * @author Taras Palashynskyy
 */
public class DeleteCategoryCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(UserInfoCommand.class);

    private static CategoryService categoryService;

    public DeleteCategoryCommand() {
        categoryService = DefaultCategoryService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.redirect.categories");
        HttpSession session = request.getSession();

        try {
            long id = Long.parseLong(request.getParameter("id"));

            Category category = categoryService.findById(id);

            if (categoryService.delete(category)) {
                session.setAttribute("deleteSuccess", true);
            } else
                session.setAttribute("deleteFailure", true);
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
            session.setAttribute("deleteFailure", true);
        }

        return page;
    }
}
