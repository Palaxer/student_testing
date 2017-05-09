package org.palax.validation.impl;

import org.apache.log4j.Logger;
import org.palax.validation.UserValidation;

import java.util.regex.Pattern;

/**
 * {@inheritDoc}
 */
public class DefaultUserValidation implements UserValidation {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DefaultUserValidation.class);
    /**Singleton object which is returned when you try to create a new instance */
    private static volatile UserValidation userValidation;

    private DefaultUserValidation() {
    }

    /**
     * Always return same {@link DefaultUserValidation} instance
     *
     * @return always return same {@link DefaultUserValidation} instance
     */
    public static UserValidation getInstance(){
        UserValidation localInstance = userValidation;
        if(localInstance == null) {
            synchronized (DefaultUserValidation.class) {
                localInstance = userValidation;
                if(localInstance == null) {
                    userValidation = new DefaultUserValidation();
                    logger.debug("Create first DefaultUserValidation instance");
                }
            }
        }
        logger.debug("Return DefaultUserValidation instance");
        return userValidation;
    }

    @Override
    public boolean loginValid(String login) {
        return Pattern.matches("[!-~]{3,10}", login);
    }

    @Override
    public boolean nameValid(String name) {
        return Pattern.matches("[A-z,А-я]{1,25}", name);
    }

    @Override
    public boolean surnameValid(String surName) {
        return Pattern.matches("[A-z,А-я]{1,30}-?[A-z,А-я]{1,25}", surName);
    }

    @Override
    public boolean passwdValid(String passwd) {
        return Pattern.matches("[!-~]{4,15}", passwd);
    }
}
