package org.palax.entity;

import java.util.List;

/**
 * The {@code Question} class represents a logical question with related {@link Answer} to it
 *
 * @author Taras Palashynskyy
 */
public class Question {
    private Long id;
    private String text;
    private Test test;
    private List<Answer> answers;

    public Question() {
    }

    public Question(Long id) {
        this.id = id;
    }

    public Question(String text, Test test) {
        this.text = text;
        this.test = test;
    }

    public Question(String text, Test test, List<Answer> answers) {
        this.text = text;
        this.test = test;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (id != null ? !id.equals(question.id) : question.id != null) return false;
        if (text != null ? !text.equals(question.text) : question.text != null) return false;
        if (test != null ? !test.equals(question.test) : question.test != null) return false;
        return answers != null ? answers.equals(question.answers) : question.answers == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (test != null ? test.hashCode() : 0);
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", test=" + test.getId() +
                ", answers=" + answers +
                '}';
    }
}
