package org.palax.validation.impl;

import org.apache.log4j.Logger;
import org.palax.validation.QuestionValidation;

import java.util.regex.Pattern;

/**
 * {@inheritDoc}
 */
public class DefaultQuestionValidation implements QuestionValidation {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DefaultQuestionValidation.class);
    /**Singleton object which is returned when you try to create a new instance */
    private static volatile QuestionValidation questionValidation;

    private DefaultQuestionValidation() {
    }

    /**
     * Always return same {@link DefaultQuestionValidation} instance
     *
     * @return always return same {@link DefaultQuestionValidation} instance
     */
    public static QuestionValidation getInstance(){
        QuestionValidation localInstance = questionValidation;
        if(localInstance == null) {
            synchronized (DefaultQuestionValidation.class) {
                localInstance = questionValidation;
                if(localInstance == null) {
                    questionValidation = new DefaultQuestionValidation();
                    logger.debug("Create first DefaultQuestionValidation instance");
                }
            }
        }
        logger.debug("Return DefaultQuestionValidation instance");
        return questionValidation;
    }

    @Override
    public boolean textValid(String text) {
        return Pattern.matches("[\\W\\w]{5,400}", text);
    }
}
