package org.palax.dao.data;

import org.palax.entity.Category;
import org.palax.entity.Test;
import org.palax.entity.User;

/**
 * {@code TestBuilder} class for building test data to {@link Test} entity
 */
public class TestBuilder implements Builder<Test> {

    private Test test;

    private TestBuilder() {
        test = new Test();
    }

    public static TestBuilder getBuilder() {
        return new TestBuilder();
    }

    public TestBuilder constructTest(Long template, Boolean active, Builder<User> userBuilder, Builder<Category> categoryBuilder) {
        if(template != null) {
            test.setId(template);
            test.setActive(active);
            test.setPassedScore(10);
            test.setPassedTime(10);
            test.setName("name" + template);
            test.setDescription("desc" + template);
        }

        test.setTutor(userBuilder != null ? userBuilder.build() : null);
        test.setCategory(categoryBuilder != null ? categoryBuilder.build() : null);

        return this;
    }

    public TestBuilder constructTest(Long template) {
        if(template != null) {
            test.setId(template);
        }

        return this;
    }

    @Override
    public Test build() {
        return test;
    }
}
