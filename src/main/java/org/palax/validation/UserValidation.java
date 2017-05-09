package org.palax.validation;

import org.palax.entity.User;

/**
 * The {@code UserValidation} is a interface which provide method to validate {@link User}
 *
 * @author Taras Palashynskyy
 */
public interface UserValidation {

    boolean loginValid(String login);
    boolean nameValid(String name);
    boolean surnameValid(String surName);
    boolean passwdValid(String passwd);
}
