package org.palax;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.command.CommandHelper;
import org.palax.exception.PageNotFoundException;
import org.palax.util.PathManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The {@code Controller} class extends {@link HttpServlet} is a controller that receives
 * and forwards all requests or redirect to the desired page
 *
 * @author Taras Palashynskyy
 */
public class Controller extends HttpServlet {
    private static final Logger logger = Logger.getLogger(Controller.class);
    private static final String PAGE_PREFIX = "jsp";

    private static CommandHelper commandHelper;

    public Controller() {
        commandHelper = CommandHelper.getInstance();
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     *  {@inheritDoc}
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            tryProcessRequest(request, response);
        } catch (PageNotFoundException e) {
            logger.debug("Threw a PageNotFoundException, full stack trace follows:",e);
            forwardToNotFoundErrorPage(request, response);
        } catch (Exception e) {
            logger.error("Threw an Exception, full stack trace follows:",e);
            forwardToServerErrorPage(request, response);
        }
    }

    private void tryProcessRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(!response.isCommitted()) {
            String page = executeCommand(request, response);
            resolvePagePath(page, request, response);
        }
    }

    private String executeCommand(HttpServletRequest request, HttpServletResponse response) {
        Command command = commandHelper.getCommand(request);
        return command.execute(request, response);
    }

    private void resolvePagePath(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (page.contains(PAGE_PREFIX))
            forwardToPage(page, request, response);
        else
            redirectToPage(page, request, response);
    }

    private void forwardToPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Forward to " + page);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private void redirectToPage(String page, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Forward to " + page);
        response.sendRedirect(request.getContextPath() + page);
    }

    private void forwardToNotFoundErrorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = PathManager.getProperty("path.page.error404");
        forwardToPage(page, request, response);
    }

    private void forwardToServerErrorPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = PathManager.getProperty("path.page.error500");
        forwardToPage(page, request, response);
    }
}
