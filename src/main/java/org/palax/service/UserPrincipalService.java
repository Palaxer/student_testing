package org.palax.service;

import org.palax.entity.User;

/**
 * The {@code UserPrincipalService} class that provides check the access rights to the resources
 *
 * @author Taras Palashynskyy
 */
public interface UserPrincipalService {
    /**
     * Method checked {@link User} permission to {@code path}
     *
     * @param user the {@code user} who checked for access
     * @param path the {@code path} access to which is requested
     * @return returns {@code true} if the {@code user} has permission to {@code path}
     *         or else {@code false}
     */
    boolean permission(User user, String path);

    /**
     * Method finds the {@link User} base page on which it is necessary
     * to redirect at login to system
     *
     * @param user a {@code user} who is logged on
     * @return return {@code user} base page or {@code "path.page.error500"}
     *         if {@code user} was {@code null}
     */
    String userBaseMapping(User user);
}
