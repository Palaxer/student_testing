package org.palax.command;

import org.palax.exception.PageNotFoundException;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@code EmptyCommand} class implements {@link Command} used in case of incorrect or blank command
 *
 * @author Taras Palashynskyy
 */

public class EmptyCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        throw new PageNotFoundException("An empty command came");
    }
}
