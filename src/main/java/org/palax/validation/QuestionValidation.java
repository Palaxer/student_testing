package org.palax.validation;

import org.palax.entity.Question;

/**
 * The {@code QuestionValidation} is a interface which provide method to validate {@link Question}
 *
 * @author Taras Palashynskyy
 */
public interface QuestionValidation {

    boolean textValid(String text);
}
