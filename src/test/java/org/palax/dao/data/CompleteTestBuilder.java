package org.palax.dao.data;

import org.palax.entity.CompleteTest;
import org.palax.entity.Test;
import org.palax.entity.User;

import java.time.LocalDateTime;

/**
 * {@code CompleteTestBuilder} class for building test data to {@link CompleteTest} entity
 */
public class CompleteTestBuilder implements Builder<CompleteTest> {

    private CompleteTest completeTest;

    private CompleteTestBuilder() {
        completeTest = new CompleteTest();
    }

    public static CompleteTestBuilder getBuilder() {
        return new CompleteTestBuilder();
    }

    public CompleteTestBuilder constructCompleteTest(Long template, Boolean passed, Builder<User> userBuilder,
                                                     Builder<Test> testBuilder) {
        if(template != null) {
            completeTest.setId(template);
            completeTest.setScore(10);
            completeTest.setElapsedTime(10);
            completeTest.setPassed(passed);
            completeTest.setStartTime(LocalDateTime.of(2017,5,8,0,0,0));
        }

        completeTest.setStudent(userBuilder != null ? userBuilder.build() : null);
        completeTest.setTest(testBuilder != null ? testBuilder.build() : null);

        return this;
    }

    @Override
    public CompleteTest build() {
        return completeTest;
    }
}
