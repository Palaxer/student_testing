package org.palax.command.redirect;

import org.palax.command.Command;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code ProfileRedirectCommand} class implements {@link Command} used for redirection to profile page
 *
 * @author Taras Palashynskyy
 */
public class ProfileRedirectCommand implements Command  {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("profileSelect", "active");
        return PathManager.getProperty("path.page.profile");
    }
}
