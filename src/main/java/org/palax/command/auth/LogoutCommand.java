package org.palax.command.auth;

import org.palax.command.Command;
import org.palax.entity.User;
import org.palax.util.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * The {@code LogoutCommand} class implements {@link Command}
 * used for remove {@code user} from session
 *
 * @author Taras Palashynskyy
 */
public class LogoutCommand implements Command {

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.login");
        HttpSession session = request.getSession();

        String login = ((User)session.getAttribute("user")).getLogin();

        Enumeration<String> enumeration = session.getAttributeNames();
        String attr;

        while (enumeration.hasMoreElements()) {
            attr = enumeration.nextElement();
            if(!attr.equals("language"))
                session.removeAttribute(attr);
        }

        request.setAttribute("logout", true);
        request.setAttribute("login", login);

        return page;
    }
}
