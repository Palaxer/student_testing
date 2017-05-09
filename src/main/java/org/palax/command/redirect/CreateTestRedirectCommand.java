package org.palax.command.redirect;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.service.CategoryService;
import org.palax.service.impl.DefaultCategoryService;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code CreateTestRedirectCommand} class implements {@link Command} used for redirection to create test page
 *
 * @author Taras Palashynskyy
 */
public class CreateTestRedirectCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(CreateTestRedirectCommand.class);
    private static CategoryService categoryService;

    public CreateTestRedirectCommand() {
        categoryService = DefaultCategoryService.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("createTestSelect", "active");
        request.setAttribute("categories", categoryService.findAll());

        return PathManager.getProperty("path.page.create-test");
    }
}
