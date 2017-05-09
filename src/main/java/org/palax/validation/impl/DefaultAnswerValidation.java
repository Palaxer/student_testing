package org.palax.validation.impl;

import org.apache.log4j.Logger;
import org.palax.validation.AnswerValidation;

import java.util.regex.Pattern;

/**
 * {@inheritDoc}
 */
public class DefaultAnswerValidation implements AnswerValidation {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DefaultAnswerValidation.class);
    /**Singleton object which is returned when you try to create a new instance */
    private static volatile AnswerValidation answerValidation;

    private DefaultAnswerValidation() {
    }

    /**
     * Always return same {@link DefaultAnswerValidation} instance
     *
     * @return always return same {@link DefaultAnswerValidation} instance
     */
    public static AnswerValidation getInstance(){
        AnswerValidation localInstance = answerValidation;
        if(localInstance == null) {
            synchronized (DefaultAnswerValidation.class) {
                localInstance = answerValidation;
                if(localInstance == null) {
                    answerValidation = new DefaultAnswerValidation();
                    logger.debug("Create first DefaultAnswerValidation instance");
                }
            }
        }
        logger.debug("Return DefaultAnswerValidation instance");
        return answerValidation;
    }

    @Override
    public boolean textValid(String text) {
        return Pattern.matches("[\\W\\w]{1,255}", text);
    }
}
