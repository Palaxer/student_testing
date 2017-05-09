package org.palax.dao;

import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.CompleteTest;
import org.palax.entity.Test;
import org.palax.entity.User;

import java.util.List;

/**
 * The {@code CompleteTestDao} interface for ORM database entity {@link CompleteTest}
 *
 * @author Taras Palashynskyy
 */

public interface CompleteTestDao {

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
     * Method to get all {@link CompleteTest} which related to {@link Test}
     *
     * @param  test {@link List} of all {@link CompleteTest} to this {@code test} will be found
     * @return return {@link List} of all {@link CompleteTest} on this {@code test}
     */
    List<CompleteTest> findAllByTest(Test test);

    /**
     * Method return {@link CompleteTest} which find by {@code id}
     *
     * @param id it indicates an {@link CompleteTest} that you want return
     * @return return {@link CompleteTest}
     */
    CompleteTest findById(Long id);

    /**
     * Method to get number of all {@link CompleteTest} by {@code user} which store in DB
     *
     * @return number of all {@link CompleteTest} by {@code user} in table
     */
    long countByUser(User user);

    /**
     * Method to get {@link CompleteTest} count which related to {@link Test}
     *
     * @param  test {@link CompleteTest} count to this {@code test} will be found
     * @return return {@link CompleteTest} count
     */
    int countByTest(Test test);

    /**
     * Method to get passed {@link CompleteTest} count which related to {@link Test}
     *
     * @param  test passed {@link CompleteTest} count to this {@code test} will be found
     * @return return passed {@link CompleteTest} count
     */
    int countByTestAndPassed(Test test, Boolean passed);

    /**
     * Method update {@link CompleteTest}
     *
     * @param completeTest the {@code completeTest} will update if it already exists
     * @return returns {@code true} if the {@code completeTest} updated
     *         or else {@code false}
     */
    boolean update(CompleteTest completeTest);

    /**
     * Method update {@link CompleteTest} with transaction
     *
     * @param completeTest the {@code completeTest} will update if it already exists
     * @return returns {@code true} if the {@code completeTest} updated
     *         or else {@code false}
     */
    boolean update(CompleteTest completeTest, TransactionManager tx);

    /**
     * Method to insert {@link CompleteTest}
     *
     * @param completeTest this {@code completeTest} will be inserted
     * @return returns {@code true} if {@link CompleteTest} inserted success
     *         or else {@code false}
     */
    boolean insert(CompleteTest completeTest);

    /**
     * Method to insert {@link CompleteTest} with transaction
     *
     * @param completeTest this {@code completeTest} will be inserted
     * @return returns {@code true} if {@link CompleteTest} inserted success
     *         or else {@code false}
     */
    boolean insert(CompleteTest completeTest, TransactionManager tx);

    /**
     * Method delete {@link CompleteTest}
     *
     * @param completeTest {@code completeTest} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(CompleteTest completeTest);

    /**
     * Method delete {@link CompleteTest} with transaction
     *
     * @param completeTest {@code completeTest} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(CompleteTest completeTest, TransactionManager tx);
}
