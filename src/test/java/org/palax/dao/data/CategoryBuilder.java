package org.palax.dao.data;

import org.palax.entity.Category;

/**
 * {@code CategoryBuilder} class for building test data to {@link Category} entity
 */
public class CategoryBuilder implements Builder<Category> {

    private Category category;

    private CategoryBuilder() {
        category = new Category();
    }

    public static CategoryBuilder getBuilder() {
        return new CategoryBuilder();
    }

    public CategoryBuilder constructCategory(Long template) {
        if(template != null) {
            category.setId(template);
            category.setName("name" + template);
        }

        return this;
    }

    @Override
    public Category build() {
        return category;
    }
}
