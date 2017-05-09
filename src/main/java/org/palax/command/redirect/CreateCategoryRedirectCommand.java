package org.palax.command.redirect;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code CreateCategoryRedirectCommand} class implements {@link Command}
 * used for redirection to create category page
 *
 * @author Taras Palashynskyy
 */
public class CreateCategoryRedirectCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(CreateCategoryRedirectCommand.class);

    public CreateCategoryRedirectCommand() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        request.setAttribute("createCategorySelect", "active");

        return PathManager.getProperty("path.page.create-category");
    }
}
