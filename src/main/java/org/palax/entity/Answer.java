package org.palax.entity;

/**
 * The {@code Answer} class is one of the possible answers to the {@link Question}
 *
 * @author Taras Palashynskyy
 */
public class Answer {
    private Long id;
    private String text;
    private Boolean correct;
    private Question question;

    public Answer() {
    }

    public Answer(String text, Boolean correct, Question question) {
        this.text = text;
        this.correct = correct;
        this.question = question;
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

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (id != null ? !id.equals(answer.id) : answer.id != null) return false;
        if (text != null ? !text.equals(answer.text) : answer.text != null) return false;
        if (correct != null ? !correct.equals(answer.correct) : answer.correct != null) return false;
        return question != null ? question.equals(answer.question) : answer.question == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (correct != null ? correct.hashCode() : 0);
        result = 31 * result + (question != null ? question.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", correct=" + correct +
                ", question=" + question.getId() +
                '}';
    }
}
