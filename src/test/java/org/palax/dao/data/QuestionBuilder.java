package org.palax.dao.data;

import org.palax.entity.Question;
import org.palax.entity.Test;


/**
 * {@code QuestionBuilder} class for building test data to {@link Question} entity
 */
public class QuestionBuilder implements Builder<Question> {

    private Question question;

    private QuestionBuilder() {
        question = new Question();
    }

    public static QuestionBuilder getBuilder() {
        return new QuestionBuilder();
    }

    public QuestionBuilder constructQuestion(Long template, Builder<Test> testBuilder) {
        if(template != null) {
            question.setId(template);
            question.setText("text" + template);
        }

        question.setTest(testBuilder != null ? testBuilder.build() : null);

        return this;
    }

    public QuestionBuilder constructQuestion(Long template) {
        if(template != null) {
            question.setId(template);
        }

        return this;
    }

    @Override
    public Question build() {
        return question;
    }
}
