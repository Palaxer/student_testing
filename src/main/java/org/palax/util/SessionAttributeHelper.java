package org.palax.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface SessionAttributeHelper {

    Object getAndRemove(HttpSession session, String name);
    boolean fromSessionToRequestScope(HttpServletRequest request, String name);
}
