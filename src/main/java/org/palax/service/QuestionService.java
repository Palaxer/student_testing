package org.palax.service;

import org.palax.dao.QuestionDao;
import org.palax.entity.Question;
import org.palax.entity.Test;

import java.util.List;

/**
 * The {@code QuestionService} service is a specified API for working with the {@link QuestionDao}
 *
 * @author Taras Palashynskyy
 */
public interface QuestionService {

    /**
     * Method to get all {@link Question} which related to {@link Test}
     *
     * @param test a {@link List} of all {@link Question} to this {@code test} will be found
     * @return return {@link List} of all {@link Question} on this {@code test}
     */
    List<Question> findAllByTest(Test test);

    Question findById(Long id);

    /**
     * Method update {@link Question}
     *
     * @param question the {@code question} will update if it already exists
     * @return returns {@code true} if the {@code question} updated
     *         or else {@code false}
     */
    boolean update(Question question);

    /**
     * Method to create {@link Question}
     *
     * @param question this {@code question} will be inserted
     * @return returns {@code true} if {@link Question} inserted success
     *         or else {@code false}
     */
    boolean create(Question question);

    /**
     * Method delete {@link Question}
     *
     * @param question {@code question} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(Question question);
}
