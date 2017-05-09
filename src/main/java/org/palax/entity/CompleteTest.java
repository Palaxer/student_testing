package org.palax.entity;

import java.time.LocalDateTime;

/**
 * The {@code CompleteTest} class is the result fo a test done by a certain student
 *
 * @author Taras Palashynskyy
 */
public class CompleteTest {
    private Long id;
    private Integer score;
    private LocalDateTime startTime;
    private Integer elapsedTime;
    private User student;
    private Test test;
    private Boolean passed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompleteTest that = (CompleteTest) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (score != null ? !score.equals(that.score) : that.score != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (elapsedTime != null ? !elapsedTime.equals(that.elapsedTime) : that.elapsedTime != null) return false;
        if (student != null ? !student.equals(that.student) : that.student != null) return false;
        if (test != null ? !test.equals(that.test) : that.test != null) return false;
        return passed != null ? passed.equals(that.passed) : that.passed == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (score != null ? score.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (elapsedTime != null ? elapsedTime.hashCode() : 0);
        result = 31 * result + (student != null ? student.hashCode() : 0);
        result = 31 * result + (test != null ? test.hashCode() : 0);
        result = 31 * result + (passed != null ? passed.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CompleteTest{" +
                "id=" + id +
                ", score=" + score +
                ", student=" + student.getId() +
                ", startTime=" + startTime +
                ", elapsedTime=" + elapsedTime +
                ", test=" + test.getId() +
                ", passed=" + passed +
                '}';
    }
}
