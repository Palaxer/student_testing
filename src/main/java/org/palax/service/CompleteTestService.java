package org.palax.service;

import org.palax.dao.CompleteTestDao;
import org.palax.dto.TestDTO;
import org.palax.entity.CompleteTest;
import org.palax.entity.Test;
import org.palax.entity.User;

import java.util.List;
import java.util.Map;

/**
 * The {@code CompleteTestService} service is a specified API for working with the {@link CompleteTestDao}
 *
 * @author Taras Palashynskyy
 */
public interface CompleteTestService {

    /**
     * Method to get all {@link CompleteTest} which related to {@link User}
     *
     * @param student a {@link List} of all {@link CompleteTest} to this {@code student} will be found
     * @return return {@link List} of all {@link CompleteTest} on this {@code student}
     */
    List<CompleteTest> findAllByStudent(User student);

    /**
     * Method to get all {@link CompleteTest} which related to {@link User}
     *
     * @param student a {@link List} of all {@link CompleteTest} to this {@code student} will be found
     * @param offSet how many rows will be skipped
     * @param numberOfElement how many rows need to get
     * @return return {@link List} of all {@link CompleteTest} on this {@code student}
     */
    List<CompleteTest> findAllByStudent(User student, int offSet, int numberOfElement);

    /**
     * Method to get all {@link CompleteTest} which related to {@link TestDTO}
     *
     * @param  test {@link List} of all {@link CompleteTest} to this {@code test} will be found
     * @return return {@link List} of all {@link CompleteTest} on this {@code test}
     */
    List<CompleteTest> findAllByTest(Test test);

    /**
     * Method to get number of all {@link CompleteTest} by {@code user} which store in DB
     *
     * @return number of all {@link CompleteTest} by {@code user} in table
     */
    long countByUser(User user);

    /**
     * Method return {@link CompleteTest} which find by {@code id}
     *
     * @param id it indicates an {@link CompleteTest} {@code id} that you want return
     * @return return {@link CompleteTest} by {@code id}
     */
    CompleteTest findById(Long id);

    boolean completeTest(CompleteTest completeTest, Map<Long, Boolean> userAnswers);

}
