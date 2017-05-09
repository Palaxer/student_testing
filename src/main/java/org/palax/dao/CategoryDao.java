package org.palax.dao;

import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Category;

import java.util.List;

/**
 * The {@code CategoryDao} interface for ORM database entity {@link Category}
 *
 * @author Taras Palashynskyy
 */

public interface CategoryDao {

    /**
     * Method to get all {@link Category}
     *
     * @return return {@link List} of all {@link Category}
     */
    List<Category> findAll();

    /**
     * Method return {@link Category} which find by {@code name}
     *
     * @param name it indicates an {@link Category} that you want return
     * @return return {@link Category}
     */
    Category findByName(String name);

    /**
     * Method return {@link Category} which find by {@code id}
     *
     * @param id it indicates an {@link Category} that you want return
     * @return return {@link Category}
     */
    Category findById(Long id);

    /**
     * Method update {@link Category}
     *
     * @param category the {@code category} will update if it already exists
     * @return returns {@code true} if the {@code category} updated
     *         or else {@code false}
     */
    boolean update(Category category);

    /**
     * Method update {@link Category} with transaction
     *
     * @param category the {@code category} will update if it already exists
     * @return returns {@code true} if the {@code category} updated
     *         or else {@code false}
     */
    boolean update(Category category, TransactionManager tx);

    /**
     * Method to insert {@link Category}
     *
     * @param category this {@code category} will be inserted
     * @return returns {@code true} if {@link Category} inserted success
     *         or else {@code false}
     */
    boolean insert(Category category);

    /**
     * Method to insert {@link Category} with transaction
     *
     * @param category this {@code category} will be inserted
     * @return returns {@code true} if {@link Category} inserted success
     *         or else {@code false}
     */
    boolean insert(Category category, TransactionManager tx);

    /**
     * Method delete {@link Category}
     *
     * @param category {@code category} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(Category category);

    /**
     * Method delete {@link Category} with transaction
     *
     * @param category {@code category} will be delete
     * @return returns {@code true} if delete success
     *         or else {@code false}
     */
    boolean delete(Category category, TransactionManager tx);
}
