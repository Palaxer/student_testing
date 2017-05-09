package org.palax.service;

import org.palax.dao.CategoryDao;
import org.palax.entity.Category;

import java.util.List;

/**
 * The {@code CategoryService} service is a specified API for working with the {@link CategoryDao}
 *
 * @author Taras Palashynskyy
 */
public interface CategoryService {

    /**
     * Method to get all {@link Category}
     *
     * @return return {@link List} of all {@link Category}
     */
    List<Category> findAll();

    /**
     * Method to get {@link Category} by {@code name}
     *
     * @return return {@link Category}
     */
    Category findByName(String name);

    Category findById(Long id);

    boolean update(Category category);

    boolean create(Category category);

    boolean delete(Category category);
}
