package org.palax.dao.mysql;

import org.apache.log4j.Logger;
import org.palax.dao.CategoryDao;
import org.palax.dao.transaction.TransactionManager;
import org.palax.entity.Category;
import org.palax.util.DataSourceManager;
import org.palax.util.impl.DefaultDataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MySQLCategoryDao} singleton class implements {@link CategoryDao} and specified for MySQL DB
 *
 * @author Taras Palashynskyy
 */

public class MySQLCategoryDao implements CategoryDao {
    /**Object for logging represent by {@link Logger}. */
    private static final Logger logger = Logger.getLogger(MySQLCategoryDao.class);

    /**Singleton object which is returned when you try to create a new instance */
    private static volatile CategoryDao categoryDao;
    private DataSourceManager dataSourceManager;
    /**Values which store column name for {@link ResultSet} */
    private static final String CATEGORY_ID = "CATEGORY_ID";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";

    private MySQLCategoryDao() {
        dataSourceManager = DefaultDataSourceManager.getInstance();
    }

    /**
     * Constructor is used to avoid use PowerMock in testing
     * @param dataSourceManager mock {@link DataSourceManager} object
     */
    public MySQLCategoryDao(DataSourceManager dataSourceManager) {
        this.dataSourceManager = dataSourceManager;
    }

    /**
     * Always return same {@link MySQLCategoryDao} instance
     *
     * @return always return same {@link MySQLCategoryDao} instance
     */
    public static CategoryDao getInstance() {
        CategoryDao localInstance = categoryDao;
        if(localInstance == null) {
            synchronized (MySQLCategoryDao.class) {
                localInstance = categoryDao;
                if(localInstance == null) {
                    categoryDao = new MySQLCategoryDao();
                    logger.debug("Create first MySQLCategoryDao instance");
                }
            }
        }
        logger.debug("Return MySQLCategoryDao instance");
        return categoryDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Category> findAll() {
        String SQL = "SELECT * FROM category";

        ArrayList<Category> roleList = new ArrayList<>();

        logger.debug("Try get all CATEGORY");

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                roleList.add(parseResultSet(rs));
            }
            logger.debug("Get all CATEGORY successfully " + roleList);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return roleList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category findByName(String name) {
        String SQL = "SELECT * FROM category WHERE CATEGORY_NAME=?";
        Category category = null;

        logger.debug("Try get CATEGORY by CATEGORY_NAME " + name);


        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                category = parseResultSet(rs);
            }
            logger.debug("Get CATEGORY by CATEGORY_NAME successfully " + category);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return category;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Category findById(Long id) {
        String SQL = "SELECT * FROM category WHERE CATEGORY_ID=?";
        Category category = null;

        logger.debug("Try get CATEGORY by ID " + id);

        try(Connection con = dataSourceManager.getConnection();
            PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, id);
            ResultSet rs = stm.executeQuery();

            while(rs.next()) {
                category = parseResultSet(rs);
            }
            logger.debug("Get CATEGORY by ID successfully " + category);
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:", e);
        }

        return category;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Category category) {
        Connection con = dataSourceManager.getConnection();
        boolean result = update(category, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean update(Category category, TransactionManager tx) {
        return update(category, tx.getConnection());
    }

    private boolean update(Category category, Connection con) {
        String SQL = "UPDATE category SET CATEGORY_NAME=? WHERE CATEGORY_ID=?";

        logger.debug("Try update CATEGORY " + category);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setString(1, category.getName());
            stm.setObject(2, category.getId());
            if(stm.executeUpdate() > 0) {
                logger.debug("CATEGORY update successfully " + category);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("CATEGORY update fail " + category);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(Category category) {
        Connection con = dataSourceManager.getConnection();
        boolean result = insert(category, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insert(Category category, TransactionManager tx) {
        return insert(category, tx.getConnection());
    }

    private boolean insert(Category category, Connection con) {
        String SQL = "INSERT INTO category (CATEGORY_NAME) VALUES (?)";

        logger.debug("Try insert CATEGORY " + category);

        try(PreparedStatement stm = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, category.getName());

            if(stm.executeUpdate() > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if(rs.next()) {
                    category.setId(rs.getLong(1));
                    logger.debug("CATEGORY insert successfully " + category);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("CATEGORY insert fail " + category);
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Category category) {
        Connection con = dataSourceManager.getConnection();
        boolean result = delete(category, con);
        dataSourceManager.closeConnection(con);

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Category category, TransactionManager tx) {
        return delete(category, tx.getConnection());
    }

    private boolean delete(Category category, Connection con) {
        String SQL = "DELETE FROM category WHERE CATEGORY_ID=?";

        logger.debug("Try delete CATEGORY " + category);

        try(PreparedStatement stm = con.prepareStatement(SQL)) {
            stm.setLong(1, category.getId());
            if(stm.executeUpdate() > 0) {
                logger.debug("CATEGORY delete successfully " + category);
                return true;
            }
        } catch (SQLException e) {
            logger.error("Threw a SQLException, full stack trace follows:",e);
        }

        logger.debug("CATEGORY delete fail " + category);
        return false;
    }

    /**
     * Method parses the {@link ResultSet} and returns a {@link Category}
     *
     * @param rs {@link ResultSet} which be parsed
     * @return parse {@code rs} and return a {@link Category}
     */
    private Category parseResultSet(ResultSet rs) throws SQLException {
        Category category = new Category();

        category.setId(rs.getObject(CATEGORY_ID, Long.class));
        category.setName(rs.getString(CATEGORY_NAME));

        return category;
    }
}
