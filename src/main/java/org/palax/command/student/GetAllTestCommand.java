package org.palax.command.student;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.Category;
import org.palax.service.CategoryService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultCategoryService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;
import org.palax.util.impl.DefaultPagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The {@code GetAllTestCommand} class implements {@link Command} used for get
 * all active test
 *
 * @author Taras Palashynskyy
 */
public class GetAllTestCommand implements Command {
    private static final Logger logger = Logger.getLogger(GetAllTestCommand.class);

    private static final int ELEMENT_PER_PAGE = 2;
    private static TestService testService;
    private static CategoryService categoryService;

    public GetAllTestCommand() {
        testService = DefaultTestService.getInstance();
        categoryService = DefaultCategoryService.getInstance();
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String page = PathManager.getProperty("path.page.tests");
        request.setAttribute("allTestSelect", "active");

        List<Category> categories = categoryService.findAll();
        request.setAttribute("categories", categories);
        Category category = getCategoryFromRequest(request, categories);
        request.setAttribute("selectCategory", category != null ? category.getName() : "all");

        DefaultPagination pagination = new DefaultPagination(ELEMENT_PER_PAGE);
        pagination.setElementCount(testService.countByCategoryAndActive(category, true));
        pagination.setCurrentPage(request.getParameter("page"));

        request.setAttribute("pageNumber", pagination.getPageNumber());
        request.setAttribute("currentPage", pagination.getCurrentPage());

        request.setAttribute("tests", testService.findAllByCategoryAndActive(category, true, pagination));

        return page;
    }

    private Category getCategoryFromRequest(HttpServletRequest request, List<Category> categories) {
        Category category = null;
        String categoryName = request.getParameter("category").replace("%20", " ");

        if(!categoryName.equals("all")) {
            category = findCategoryByName(categories, categoryName);
        }

        return category;
    }

    private Category findCategoryByName(List<Category> categories, String categoryName) {
        Category category = null;

        for (Category el : categories) {
            if (el.getName().equals(categoryName))
                category = el;
        }

        return category;
    }
}
