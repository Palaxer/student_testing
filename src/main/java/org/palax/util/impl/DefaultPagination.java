package org.palax.util.impl;

import org.apache.log4j.Logger;
import org.palax.exception.PageNotFoundException;
import org.palax.util.Pagination;

public class DefaultPagination implements Pagination {
    private static final Logger logger = Logger.getLogger(DefaultPagination.class);

    private int currentPage;
    private int pageNumber;
    private int elementPerPage;

    public DefaultPagination() {
        currentPage = 1;
        elementPerPage = 10;
    }

    public DefaultPagination(int elementPerPage) {
        this();
        this.elementPerPage = elementPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        checkCurrentPageLimit();
    }

    public void setCurrentPage(String currentPage) {
        try {
            if(currentPage != null)
                setCurrentPage(Integer.parseInt(currentPage));
        } catch (NumberFormatException e) {
            logger.error("Threw a NumberFormatException, full stack trace follows:", e);
        }
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setElementCount(long elementCount) {
        this.pageNumber = (int) Math.ceil(elementCount * 1.0 / elementPerPage);
    }

    @Override
    public int getElementPerPage() {
        return elementPerPage;
    }

    public void setElementPerPage(int elementPerPage) {
        this.elementPerPage = elementPerPage;
    }

    @Override
    public int getElementOffSet() {
        return currentPage * elementPerPage - elementPerPage;
    }

    private void checkCurrentPageLimit() {
        if(isBiggerThanLimit() || isLowerThanLimit())
            throw new PageNotFoundException("The specified page was not found");
    }

    private boolean isBiggerThanLimit() {
        return currentPage > pageNumber;
    }

    private boolean isLowerThanLimit() {
        return currentPage <= 0;
    }

}
