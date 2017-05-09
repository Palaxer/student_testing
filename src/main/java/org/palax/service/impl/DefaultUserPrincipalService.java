package org.palax.service.impl;

import org.apache.log4j.Logger;
import org.palax.entity.User;
import org.palax.service.UserPrincipalService;
import org.palax.util.PathManager;
import org.palax.util.UserPrincipalManager;

import java.util.*;

/**
 * {@inheritDoc}
 */
public class DefaultUserPrincipalService implements UserPrincipalService {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DefaultUserPrincipalService.class);

    /**Map which store {@link User} base mapping to redirection page. */
    private static final Map<String, String> userBaseMapping = new HashMap<>();
    /**Map which store {@link User} permission mapping, where store
     * page {@code path} and {@code roleType} which has permission to page. */
    private static final Map<String, Set<String>> userPermissionMapping = new HashMap<>();
    /**Singleton object which is returned when you try to create a new instance */
    private static volatile UserPrincipalService userPrincipalService;

    /**
     * Initialization user base mapping and permission mapping
     */
    private DefaultUserPrincipalService() {
        logger.debug("Initialization user base mapping");
        userBaseMapping.put("ADMIN", PathManager.getProperty("path.redirect.admin"));
        userBaseMapping.put("STUDENT", PathManager.getProperty("path.redirect.student"));
        userBaseMapping.put("TUTOR", PathManager.getProperty("path.redirect.tutor"));

        Enumeration<String> key = UserPrincipalManager.getKey();

        while (key.hasMoreElements()) {
            String keys = key.nextElement();
            Set<String> value = new HashSet<>();

            StringTokenizer stringTokenizer = new StringTokenizer(UserPrincipalManager.getProperty(keys));
            while(stringTokenizer.hasMoreTokens()) {
                value.add(stringTokenizer.nextToken());
            }
            userPermissionMapping.put(keys.replace("command.", ""), value);
        }
    }

    /**
     * Always return same {@link DefaultUserPrincipalService} instance
     *
     * @return always return same {@link DefaultUserPrincipalService} instance
     */
    public static UserPrincipalService getInstance(){
        UserPrincipalService localInstance = userPrincipalService;
        if(localInstance == null) {
            synchronized (DefaultUserPrincipalService.class) {
                localInstance = userPrincipalService;
                if(localInstance == null) {
                    userPrincipalService = new DefaultUserPrincipalService();
                    logger.debug("Create first DefaultUserPrincipalService instance");
                }
            }
        }
        logger.debug("Return DefaultUserPrincipalService instance");
        return userPrincipalService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean permission(User user, String command) {

        if(user == null) {
            return userPermissionMapping.get(command).contains("UNSIGNED");
        }

        return userPermissionMapping.get(command).contains(user.getRole().name()) ||
                userPermissionMapping.get(command).contains("ALL");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String userBaseMapping(User user) {
        String page = PathManager.getProperty("path.page.login");

        if (user != null) {
            page = userBaseMapping.getOrDefault(user.getRole().name(),
                    PathManager.getProperty("path.page.error500"));
        }

        return page;
    }
}
