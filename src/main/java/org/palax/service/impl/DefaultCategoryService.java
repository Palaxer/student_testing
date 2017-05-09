package org.palax.service.impl;

import org.apache.log4j.Logger;
import org.palax.dao.*;
import org.palax.dao.factory.MySQLDAOFactory;
import org.palax.entity.Category;
import org.palax.service.CategoryService;

import java.util.List;


/**
 * {@inheritDoc}
 */
public class DefaultCategoryService implements CategoryService {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(DefaultCategoryService.class);
    /**Singleton object which is returned when you try to create a new instance */
    private static volatile CategoryService categoryService;
    private static CategoryDao categoryDao;

    private DefaultCategoryService() {
        categoryDao = MySQLDAOFactory.getCategoryDao();
    }

    /**
     * Always return same {@link DefaultCategoryService} instance
     *
     * @return always return same {@link DefaultCategoryService} instance
     */
    public static CategoryService getInstance(){
        CategoryService localInstance = categoryService;
        if(localInstance == null) {
            synchronized (DefaultCategoryService.class) {
                localInstance = categoryService;
                if(localInstance == null) {
                    categoryService = new DefaultCategoryService();
                    logger.debug("Create first DefaultCategoryService instance");
                }
            }
        }
        logger.debug("Return DefaultCategoryService instance");
        return categoryService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findAll() {

        return categoryDao.findAll();
    }

    @Override
    public Category findByName(String name) {

        return categoryDao.findByName(name);
    }

    @Override
    public Category findById(Long id) {

        return categoryDao.findById(id);
    }

    @Override
    public boolean update(Category category) {

        return categoryDao.update(category);
    }

    @Override
    public boolean create(Category category) {

        return categoryDao.insert(category);
    }

    @Override
    public boolean delete(Category category) {
        return categoryDao.delete(category);
    }

}
