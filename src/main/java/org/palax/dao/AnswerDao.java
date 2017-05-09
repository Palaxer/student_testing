package org.palax.dao;

import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Answer;
import org.palax.entity.Question;

import java.util.List;

/**
 * The {@code AnswerDao} interface for ORM database entity {@link Answer}
 *
 * @author Taras Palashynskyy
 */

public interface AnswerDao {

    /**
     * Method to get all {@link Answer} which related to {@link Question}
     *
     * @param question a {@link List} of all {@link Answer} to this {@code question} will be found
     * @return return {@link List} of all {@link Answer} on this {@code question}
     */
    List<Answer> findAllByQuestion(Question question);


    /**
     * Method return {@link Answer} which find by {@code id}
     *
     * @param id it indicates an {@link Answer} that you want return
     * @return return {@link Answer}
     */
    Answer findById(Long id);

    /**
     * Method update {@link Answer}
     *
     * @param answer the {@code answer} will update if it already exists
     * @return returns {@code true} if the {@code answer} updated
     *         or else {@code false}
     */
    boolean update(Answer answer);

    /**
     * Method update {@link Answer} with transaction
     *
     * @param answer the {@code answer} will update if it already exists
     * @return returns {@code true} if the {@code answer} updated
     *         or else {@code false}
     */
    boolean update(Answer answer, TransactionManager tx);

    /**
     * Method to insert {@link Answer}
     *
     * @param answer this {@code answer} will be inserted
     * @return returns {@code true} if {@link Answer} inserted success
     *         or else {@code false}
     */
    boolean insert(Answer answer);

    /**
     * Method to insert {@link Answer} with transaction
     *
     * @param answer this {@code answer} will be inserted
     * @return returns {@code true} if {@link Answer} inserted success
     *         or else {@code false}
     */
    boolean insert(Answer answer, TransactionManager tx);

    /**
     * Method delete {@link Answer}
     *
     * @param answer {@code answer} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(Answer answer);

    /**
     * Method delete {@link Answer} with transaction
     *
     * @param answer {@code answer} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(Answer answer, TransactionManager tx);
}
