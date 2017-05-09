package org.palax.dao.data;

import org.palax.entity.Answer;
import org.palax.entity.Question;

/**
 * {@code AnswerBuilder} class for building test data to {@link Answer} entity
 */
public class AnswerBuilder implements Builder<Answer> {

    private Answer answer;

    private AnswerBuilder() {
        answer = new Answer();
    }

    public static AnswerBuilder getBuilder() {
        return new AnswerBuilder();
    }

    public AnswerBuilder constructAnswer(Long template, Boolean correct, Builder<Question> testBuilder) {
        if(template != null) {
            answer.setId(template);
            answer.setText("text" + template);
            answer.setCorrect(correct);
        }

        answer.setQuestion(testBuilder != null ? testBuilder.build() : null);

        return this;
    }

    @Override
    public Answer build() {
        return answer;
    }
}
