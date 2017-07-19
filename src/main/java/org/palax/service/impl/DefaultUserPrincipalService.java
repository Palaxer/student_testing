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
    private static final Logger logger = Logger.getLogger(DefaultUserPrincipalService.class);

    /**Map which store {@link User} base mapping to redirection page. */
    private static final Map<String, String> userBaseMapping = new HashMap<>();
    /**Map which store {@link User} permission mapping, where store
     * page {@code path} and {@code roleType} which has permission to page. */
    private static final Map<String, Set<String>> userPermissionMapping = new HashMap<>();
    private static final String COMMAND_PREFIX = "command.";
    private static volatile UserPrincipalService userPrincipalService;

    /**
     * Initialization user base mapping and permission mapping
     */
    private DefaultUserPrincipalService() {
        logger.debug("Initialization user base mapping");
        userBaseMapping.put("ADMIN", PathManager.getProperty("path.redirect.admin"));
        userBaseMapping.put("STUDENT", PathManager.getProperty("path.redirect.student"));
        userBaseMapping.put("TUTOR", PathManager.getProperty("path.redirect.tutor"));

        createUserPermissionMapping();
    }

    private void createUserPermissionMapping() {
        Enumeration<String> keys = UserPrincipalManager.getKeys();

        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            Set<String> value = fillCommandRoles(key);
            //Remove prefix in order to simplify the search for a command
            key = key.replace(COMMAND_PREFIX, "");
            userPermissionMapping.put(key, value);
        }
    }

    private Set<String> fillCommandRoles(String key) {
        Set<String> value = new HashSet<>();

        StringTokenizer stringTokenizer = new StringTokenizer(UserPrincipalManager.getProperty(key));
        while(stringTokenizer.hasMoreTokens()) {
            value.add(stringTokenizer.nextToken());
        }
        return value;
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
        Set<String> allowedRolesForCommand = userPermissionMapping.get(command);

        //Forbid access to all unregistered command
        if(allowedRolesForCommand == null)
            return false;

        //If the user is not authorized,
        //then check whether unauthorized users can access to the command
        if(user == null)
            return allowedRolesForCommand.contains("UNSIGNED");

        //If the user is authorized then check the ability
        //to access the command of all roles
        if(allowedRolesForCommand.contains("ALL"))
            return true;

        //If the user is authorized then check the ability
        //to access the command by its role
        return allowedRolesForCommand.contains(user.getRole().name());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String userBaseMapping(User user) {
        String page = PathManager.getProperty("path.page.login");
        if (user != null)
            page = getUserBasePage(user);

        return page;
    }

    private String getUserBasePage(User user) {
        return userBaseMapping.getOrDefault(user.getRole().name(),
                PathManager.getProperty("path.page.error500"));
    }
}
