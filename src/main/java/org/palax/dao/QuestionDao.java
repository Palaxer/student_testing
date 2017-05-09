package org.palax.dao;

import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Question;
import org.palax.entity.Test;

import java.util.List;

/**
 * The {@code QuestionDao} interface for ORM database entity {@link Question}
 *
 * @author Taras Palashynskyy
 */

public interface QuestionDao {

    /**
     * Method to get all {@link Question} which related to {@link Test}
     *
     * @param test a {@link List} of all {@link Question} to this {@code test} will be found
     * @return return {@link List} of all {@link Question} on this {@code test}
     */
    List<Question> findAllByTest(Test test);


    /**
     * Method return {@link Question} which find by {@code id}
     *
     * @param id it indicates an {@link Question} that you want return
     * @return return {@link Question}
     */
    Question findById(Long id);

    /**
     * Method to get {@link Question} count which related to {@link Test}
     *
     * @param  test {@link Question} count to this {@code test} will be found
     * @return return {@link Question} count
     */
    int countByTest(Test test);

    /**
     * Method update {@link Question}
     *
     * @param question the {@code question} will update if it already exists
     * @return returns {@code true} if the {@code question} updated
     *         or else {@code false}
     */
    boolean update(Question question);

    /**
     * Method update {@link Question} with transaction
     *
     * @param question the {@code question} will update if it already exists
     * @return returns {@code true} if the {@code question} updated
     *         or else {@code false}
     */
    boolean update(Question question, TransactionManager tx);

    /**
     * Method to insert {@link Question}
     *
     * @param question this {@code question} will be inserted
     * @return returns {@code true} if {@link Question} inserted success
     *         or else {@code false}
     */
    boolean insert(Question question);

    /**
     * Method to insert {@link Question} with transaction
     *
     * @param question this {@code question} will be inserted
     * @return returns {@code true} if {@link Question} inserted success
     *         or else {@code false}
     */
    boolean insert(Question question, TransactionManager tx);

    /**
     * Method delete {@link Question}
     *
     * @param question {@code question} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(Question question);

    /**
     * Method delete {@link Question} with transaction
     *
     * @param question {@code question} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(Question question, TransactionManager tx);
}
