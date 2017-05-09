package org.palax.util.impl;

import org.apache.log4j.Logger;
import org.palax.util.SessionAttributeHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * The {@code DefaultSessionAttributeHelper} class it a basic implementation of {@link SessionAttributeHelper} interface
 *
 * @author Taras Palashynskyy
 */
public class DefaultSessionAttributeHelper implements SessionAttributeHelper {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DefaultSessionAttributeHelper.class);
    /**Singleton object which is returned when you try to create a new instance */
    private static volatile SessionAttributeHelper sessionHelper;

    /**
     * Initialization data source object if the @{code dataSource} is not initialized
     * is stored @{code null} value
     */
    private DefaultSessionAttributeHelper() {
    }

    /**
     * Always return same {@link DefaultSessionAttributeHelper} instance
     *
     * @return always return same {@link DefaultSessionAttributeHelper} instance
     */
    public static SessionAttributeHelper getInstance() {
        SessionAttributeHelper localInstance = sessionHelper;
        if(localInstance == null) {
            synchronized (DefaultSessionAttributeHelper.class) {
                localInstance = sessionHelper;
                if(localInstance == null) {
                    sessionHelper = new DefaultSessionAttributeHelper();
                    logger.debug("Create first DefaultSessionAttributeHelper instance");
                }
            }
        }
        logger.debug("Return DefaultSessionAttributeHelper instance");
        return sessionHelper;
    }


    @Override
    public Object getAndRemove(HttpSession session, String name) {
        Object object = session.getAttribute(name);

        if(object != null)
            session.removeAttribute(name);

        return object;
    }

    @Override
    public boolean fromSessionToRequestScope(HttpServletRequest request, String name) {
        Object object = request.getSession().getAttribute(name);

        if(object != null) {
            request.getSession().removeAttribute(name);
            request.setAttribute(name, object);
            return true;
        }

        return false;
    }
}
