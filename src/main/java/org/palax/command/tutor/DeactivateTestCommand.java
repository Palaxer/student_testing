package org.palax.command.tutor;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.dto.TestDTO;
import org.palax.entity.Role;
import org.palax.entity.User;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The {@code DeactivateTestCommand} class implements {@link Command} used for deactivate test
 *
 * @author Taras Palashynskyy
 */
public class DeactivateTestCommand implements Command {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DeactivateTestCommand.class);
    private static TestService testService;

    public DeactivateTestCommand() {
        testService = DefaultTestService.getInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.redirect.test-info") + request.getParameter("id");
        HttpSession session = request.getSession();

        TestDTO testDTO = testService.findById(Long.parseLong(request.getParameter("id")));
        User user = (User) session.getAttribute("user");

        if(!(testDTO.getTutor().getId().equals(user.getId()) || user.getRole() == Role.ADMIN)){
            return PathManager.getProperty("path.page.error-perm");
        }

        testDTO.setActive(false);
        if(testService.update(testDTO.getTest())) {
            session.setAttribute("updateSuccess", true);
        } else
            session.setAttribute("invalidUpdate", true);

        return page;
    }
}
