package org.palax.validation;

import org.palax.dto.TestDTO;
import org.palax.entity.Test;
import org.palax.entity.User;

/**
 * The {@code TestValidation} is a interface which provide method to validate {@link Test}
 *
 * @author Taras Palashynskyy
 */
public interface TestValidation {

    boolean nameValid(String name);
    boolean descriptionValid(String desc);
    boolean passScoreValid(Integer passScore);
    boolean passTimeValid(Integer passTime);
    boolean isUserAllowedToEditTest(TestDTO testDTO, User user);
}
