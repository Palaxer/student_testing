package org.palax.service;

import org.palax.dao.QuestionDao;
import org.palax.entity.Answer;
import org.palax.entity.Question;

import java.util.List;

/**
 * The {@code QuestionService} service is a specified API for working with the {@link QuestionDao}
 *
 * @author Taras Palashynskyy
 */
public interface AnswerService {

    /**
     * Method to get all {@link Answer} which related to {@link Question}
     *
     * @param question a {@link List} of all {@link Answer} to this {@code question} will be found
     * @return return {@link List} of all {@link Answer} on this {@code question}
     */
    List<Answer> findAllByQuestion(Question question);

    Answer findById(Long id);

    /**
     * Method update {@link Answer}
     *
     * @param answers the {@code answers} will update if it already exists
     * @return returns {@code true} if the {@code question} updated
     *         or else {@code false}
     */
    boolean update(List<Answer> answers);

    /**
     * Method to create {@link Answer}
     *
     * @param answer this {@code answer} will be inserted
     * @return returns {@code true} if {@link Answer} inserted success
     *         or else {@code false}
     */
    boolean create(Answer answer);

    /**
     * Method delete {@link Answer}
     *
     * @param answer {@code answer} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(Answer answer);
}
