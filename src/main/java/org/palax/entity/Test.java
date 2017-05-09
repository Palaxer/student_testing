package org.palax.entity;

import java.util.List;

/**
 * The {@code Test} class represents a logical related set of {@link Question}
 *
 * @author Taras Palashynskyy
 */
public class Test {
    private Long id;
    private String name;
    private String description;
    private User tutor;
    private Category category;
    private List<Question> questions;
    private Integer passedScore;
    private Integer passedTime;
    private Boolean active;

    public Test() {
    }

    public Test(String name, String description, User tutor, Category category) {
        this.name = name;
        this.description = description;
        this.tutor = tutor;
        this.category = category;
    }

    public Test(String name, String description, User tutor, Category category, List<Question> questions) {
        this.name = name;
        this.description = description;
        this.tutor = tutor;
        this.category = category;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Integer getPassedScore() {
        return passedScore;
    }

    public void setPassedScore(Integer passedScore) {
        this.passedScore = passedScore;
    }

    public Integer getPassedTime() {
        return passedTime;
    }

    public void setPassedTime(Integer passedTime) {
        this.passedTime = passedTime;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Test test = (Test) o;

        if (id != null ? !id.equals(test.id) : test.id != null) return false;
        if (name != null ? !name.equals(test.name) : test.name != null) return false;
        if (description != null ? !description.equals(test.description) : test.description != null) return false;
        if (tutor != null ? !tutor.equals(test.tutor) : test.tutor != null) return false;
        if (category != null ? !category.equals(test.category) : test.category != null) return false;
        if (questions != null ? !questions.equals(test.questions) : test.questions != null) return false;
        if (passedScore != null ? !passedScore.equals(test.passedScore) : test.passedScore != null) return false;
        if (passedTime != null ? !passedTime.equals(test.passedTime) : test.passedTime != null) return false;
        return active != null ? active.equals(test.active) : test.active == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (tutor != null ? tutor.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        result = 31 * result + (passedScore != null ? passedScore.hashCode() : 0);
        result = 31 * result + (passedTime != null ? passedTime.hashCode() : 0);
        result = 31 * result + (active != null ? active.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category.getId() +
                ", questions=" + questions +
                ", passedScore=" + passedScore +
                ", passedTime=" + passedTime +
                ", active=" + active +
                '}';
    }
}
