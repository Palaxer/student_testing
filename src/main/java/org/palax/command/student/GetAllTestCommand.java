package org.palax.command.student;

import org.apache.log4j.Logger;
import org.palax.command.Command;
import org.palax.entity.Category;
import org.palax.service.CategoryService;
import org.palax.service.TestService;
import org.palax.service.impl.DefaultCategoryService;
import org.palax.service.impl.DefaultTestService;
import org.palax.util.PathManager;

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
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(GetAllTestCommand.class);

    private static final int ELEMENT_PER_PAGE = 10;
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

        Category category = null;
        int currentPage = 1;

        try {
            if(request.getParameter("page") != null)
                currentPage = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
        }

        List<Category> categories = categoryService.findAll();
        request.setAttribute("categories", categories);

        String categoryName = request.getParameter("category").replace("%20", " ");

        if(categoryName.equals("all")) {
            request.setAttribute("selectCategory", "all");
        } else {
            for(Category el : categories) {
                if(el.getName().equals(categoryName))
                    category = el;
            }

            request.setAttribute("selectCategory", category != null ? category.getName() : "all");
        }
        long count;

        count = testService.countByCategoryAndActive(category, true);

        int pageNumber = (int) Math.ceil(count * 1.0 / ELEMENT_PER_PAGE);

        if(currentPage > pageNumber && pageNumber != 0)
            return PathManager.getProperty("path.page.error404");

        request.setAttribute("pageNumber", pageNumber);
        request.setAttribute("currentPage", currentPage);

        request.setAttribute("tests", testService.findAllByCategoryAndActive(category, true,
                currentPage * ELEMENT_PER_PAGE - ELEMENT_PER_PAGE, ELEMENT_PER_PAGE));

        return page;
    }
}
