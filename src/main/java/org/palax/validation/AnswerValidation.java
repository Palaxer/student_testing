package org.palax.validation;

import org.palax.entity.Answer;

/**
 * The {@code AnswerValidation} is a interface which provide method to validate {@link Answer}
 *
 * @author Taras Palashynskyy
 */
public interface AnswerValidation {

    boolean textValid(String text);
}
