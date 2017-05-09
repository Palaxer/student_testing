package org.palax.command.redirect;

import org.palax.command.Command;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code RegistrationRedirectCommand} class implements {@link Command} used for redirection to registration page
 *
 * @author Taras Palashynskyy
 */
public class RegistrationRedirectCommand implements Command  {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return PathManager.getProperty("path.page.registration");
    }
}
