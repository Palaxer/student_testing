package org.palax.validation.impl;

import org.apache.log4j.Logger;
import org.palax.validation.CategoryValidation;

import java.util.regex.Pattern;

/**
 * {@inheritDoc}
 */
public class DefaultCategoryValidation implements CategoryValidation {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DefaultCategoryValidation.class);
    /**Singleton object which is returned when you try to create a new instance */
    private static volatile CategoryValidation categoryValidation;

    private DefaultCategoryValidation() {
    }

    /**
     * Always return same {@link DefaultCategoryValidation} instance
     *
     * @return always return same {@link DefaultCategoryValidation} instance
     */
    public static CategoryValidation getInstance(){
        CategoryValidation localInstance = categoryValidation;
        if(localInstance == null) {
            synchronized (DefaultCategoryValidation.class) {
                localInstance = categoryValidation;
                if(localInstance == null) {
                    categoryValidation = new DefaultCategoryValidation();
                    logger.debug("Create first DefaultCategoryValidation instance");
                }
            }
        }
        logger.debug("Return DefaultCategoryValidation instance");
        return categoryValidation;
    }


    @Override
    public boolean nameValid(String name) {
        return Pattern.matches("[\\W\\w]{2,45}", name);
    }
}
