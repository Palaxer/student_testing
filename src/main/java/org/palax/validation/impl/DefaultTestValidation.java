package org.palax.validation.impl;

import org.apache.log4j.Logger;
import org.palax.validation.TestValidation;

import java.util.regex.Pattern;

/**
 * {@inheritDoc}
 */
public class DefaultTestValidation implements TestValidation {
    private static final Logger logger = Logger.getLogger(DefaultTestValidation.class);
    private static volatile TestValidation testValidation;

    private DefaultTestValidation() {
    }

    /**
     * Always return same {@link DefaultTestValidation} instance
     *
     * @return always return same {@link DefaultTestValidation} instance
     */
    public static TestValidation getInstance(){
        TestValidation localInstance = testValidation;
        if(localInstance == null) {
            synchronized (DefaultTestValidation.class) {
                localInstance = testValidation;
                if(localInstance == null) {
                    testValidation = new DefaultTestValidation();
                    logger.debug("Create first DefaultTestValidation instance");
                }
            }
        }
        logger.debug("Return DefaultTestValidation instance");
        return testValidation;
    }


    @Override
    public boolean nameValid(String name) {
        return Pattern.matches("[\\W\\w]{5,60}", name);
    }

    @Override
    public boolean descriptionValid(String desc) {
        return Pattern.matches("[\\W\\w]{5,255}", desc);
    }

    @Override
    public boolean passScoreValid(Integer passScore) {
        return passScore >= 0;
    }

    @Override
    public boolean passTimeValid(Integer passTime) {
        return passTime >= 0;
    }
}
