package org.palax.dao;

import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Answer;
import org.palax.entity.Category;
import org.palax.entity.Test;
import org.palax.entity.User;

import java.util.List;

/**
 * The {@code TestDao} interface for ORM database entity {@link Test}
 *
 * @author Taras Palashynskyy
 */

public interface TestDao {

    List<Test> findAllByActive(Boolean active, int offSet, int numberOfElement);

    List<Test> findAllByCategoryAndActive(Category category, boolean active, int offSet, int numberOfElement);

    /**
     * Method to get all {@link Test} which related to {@link User}
     *
     * @param  tutor {@link List} of all {@link Test} to this {@code tutor} will be found
     * @return return {@link List} of all {@link Test} on this {@code tutor}
     */
    List<Test> findAllByTutor(User tutor, int offSet, int numberOfElement);

    long countByActive(Boolean active);

    long countByTutor(User tutor);

    long countByCategoryAndActive(Category category, boolean active);

    /**
     * Method return {@link Test} which find by {@code id}
     *
     * @param id it indicates an {@link Test} that you want return
     * @return return {@link Test}
     */
    Test findById(Long id);

    /**
     * Method to replace the {@code tutor} in all {@link Test}
     *
     * @param from the {@code user} replace from
     * @param to the {@code user} replace to
     * @return returns {@code true} if the replace successful
     *         or else {@code false}
     */
    boolean changeTutorInAllTest(User from, User to);

    /**
     * Method to replace the {@code tutor} in all {@link Test} with transaction
     *
     * @param from the {@code user} replace from
     * @param to the {@code user} replace to
     * @return returns {@code true} if the replace successful
     *         or else {@code false}
     */
    boolean changeTutorInAllTest(User from, User to, TransactionManager tx);

    /**
     * Method update {@link Test}
     *
     * @param test the {@code test} will update if it already exists
     * @return returns {@code true} if the {@code test} updated
     *         or else {@code false}
     */
    boolean update(Test test);

    /**
     * Method update {@link Test} with transaction
     *
     * @param test the {@code test} will update if it already exists
     * @return returns {@code true} if the {@code test} updated
     *         or else {@code false}
     */
    boolean update(Test test, TransactionManager tx);

    /**
     * Method to insert {@link Test}
     *
     * @param test this {@code test} will be inserted
     * @return returns {@code true} if {@link Test} inserted success
     *         or else {@code false}
     */
    boolean insert(Test test);

    /**
     * Method to insert {@link Test} with transaction
     *
     * @param test this {@code test} will be inserted
     * @return returns {@code true} if {@link Test} inserted success
     *         or else {@code false}
     */
    boolean insert(Test test, TransactionManager tx);

    /**
     * Method delete {@link Answer}
     *
     * @param test {@code test} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(Test test);

    /**
     * Method delete {@link Test} with transaction
     *
     * @param test {@code test} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(Test test, TransactionManager tx);
}
