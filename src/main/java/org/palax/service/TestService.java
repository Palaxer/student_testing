package org.palax.service;

import org.palax.dao.TestDao;
import org.palax.dto.TestDTO;
import org.palax.entity.Category;
import org.palax.entity.Test;
import org.palax.entity.User;

import java.util.List;

/**
 * The {@code TestService} service is a specified API for working with the {@link TestDao}
 *
 * @author Taras Palashynskyy
 */
public interface TestService {

    /**
     * Method return {@link TestDTO} which find by {@code id}
     *
     * @param id it indicates an {@link Test} {@code id} that you want return
     * @return return {@link TestDTO} by {@code id}
     */
    TestDTO findById(Long id);

    List<Test> findAllByTutor(User tutor, int offSet, int numberOfElement);

    List<Test> findAllByCategoryAndActive(Category category, boolean active, int offSet, int numberOfElement);

    long countByTutor(User tutor);

    long countByCategoryAndActive(Category category, boolean active);

    /**
     * Method update {@link Test}
     *
     * @param test the {@code test} will update if it already exists
     * @return returns {@code true} if the {@code test} updated
     *         or else {@code false}
     */
    boolean update(Test test);

    boolean create(Test test);
}
