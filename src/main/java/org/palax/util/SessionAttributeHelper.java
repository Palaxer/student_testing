package org.palax.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The {@code SessionAttributeHelper} class used
 * to manipulate attributes in a session
 *
 * @author Taras Palashynskyy
 */
public interface SessionAttributeHelper {

    Object getAndRemove(HttpSession session, String name);
    boolean fromSessionToRequestScope(HttpServletRequest request, String name);
}
