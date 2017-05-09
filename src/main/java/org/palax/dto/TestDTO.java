package org.palax.dto;

import org.palax.entity.Category;
import org.palax.entity.Test;
import org.palax.entity.User;

/**
 * The {@code TestDTO} class is a data transfer object that is used to represent
 * {@link Test} in view layer
 *
 * @author Taras Palashynskyy
 */
public class TestDTO {
    private Long id;
    private String name;
    private String description;
    private User tutor;
    private Category category;
    private Integer questionCount;
    private Integer passedScore;
    private Integer passedTime;
    private Integer completedTime;
    private Integer passPercent;
    private Boolean active;

    public TestDTO() {

    }

    public TestDTO(Test test) {
        this.id = test.getId();
        this.category = test.getCategory();
        this.name = test.getName();
        this.description = test.getDescription();
        this.tutor = test.getTutor();
        this.passedScore = test.getPassedScore();
        this.passedTime = test.getPassedTime();
        this.active = test.getActive();
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

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
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

    public Integer getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Integer completedTime) {
        this.completedTime = completedTime;
    }

    public Integer getPassPercent() {
        return passPercent;
    }

    public void setPassPercent(Integer passPercent) {
        this.passPercent = passPercent;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Test getTest() {
        Test test = new Test();
        test.setId(id);
        test.setTutor(tutor);
        test.setCategory(category);
        test.setDescription(description);
        test.setName(name);
        test.setPassedTime(passedTime);
        test.setPassedScore(passedScore);
        test.setActive(active);

        return test;
    }
}
