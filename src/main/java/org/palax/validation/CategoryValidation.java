package org.palax.validation;

import org.palax.entity.Category;

/**
 * The {@code CategoryValidation} is a interface which provide method to validate {@link Category}
 *
 * @author Taras Palashynskyy
 */
public interface CategoryValidation {

    boolean nameValid(String name);
}
