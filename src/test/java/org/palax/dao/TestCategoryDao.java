package org.palax.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.palax.dao.data.CategoryBuilder;
import org.palax.dao.mysql.MySQLCategoryDao;
import org.palax.entity.Category;
import org.palax.util.DataSourceManager;
import org.palax.util.TestDBManager;
import org.palax.util.TestDataSourceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCategoryDao {

    private static CategoryDao categoryDao;

    @Mock
    private DataSourceManager ds;
    @Mock
    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private ResultSet rs;

    @BeforeClass
    public static void setUpClass() {
        categoryDao = new MySQLCategoryDao(new TestDataSourceManager());
        TestDBManager.setUpTestDML();
    }

    @Test
    public void integrityTestFindAll() throws SQLException {
        List<Category> expectedCategories = new ArrayList<>();
        expectedCategories.add(CategoryBuilder.getBuilder().constructCategory(1L).build());
        expectedCategories.add(CategoryBuilder.getBuilder().constructCategory(2L).build());

        List<Category> actualCategories =  categoryDao.findAll();

        assertEquals(expectedCategories, actualCategories);
    }

    @Test
    public void testFindAll() throws SQLException {
        CategoryDao categoryDao = new MySQLCategoryDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        categoryDao.findAll();

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestFindByName() {
        Category expectedCategory = CategoryBuilder.getBuilder().constructCategory(1L).build();

        Category actualCategory =  categoryDao.findByName(expectedCategory.getName());

        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    public void testFindByName() throws SQLException {
        CategoryDao categoryDao = new MySQLCategoryDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        categoryDao.findByName("name");

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestFindById() {
        Category expectedCategory = CategoryBuilder.getBuilder().constructCategory(2L).build();

        Category actualCategory =  categoryDao.findById(expectedCategory.getId());

        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    public void testFindById() throws SQLException {
        CategoryDao categoryDao = new MySQLCategoryDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        categoryDao.findById(1L);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestUpdate() {
        Category category = CategoryBuilder.getBuilder().constructCategory(2L).build();
        category.setName("update");

        assertTrue(categoryDao.update(category));
        assertEquals(category, categoryDao.findById(category.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testUpdate() throws SQLException {
        CategoryDao categoryDao = new MySQLCategoryDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        categoryDao.update(CategoryBuilder.getBuilder().constructCategory(1L).build());

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestInsert() {
        Category category = CategoryBuilder.getBuilder().constructCategory(3L).build();

        assertTrue(categoryDao.insert(category));
        assertEquals(category, categoryDao.findById(category.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testInsert() throws SQLException {
        CategoryDao categoryDao = new MySQLCategoryDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        categoryDao.insert(CategoryBuilder.getBuilder().constructCategory(1L).build());

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestDelete() {
        Category category = CategoryBuilder.getBuilder().constructCategory(1L).build();

        assertTrue(categoryDao.delete(category));
        assertNull(categoryDao.findById(category.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testDelete() throws SQLException {
        CategoryDao categoryDao = new MySQLCategoryDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        categoryDao.delete(CategoryBuilder.getBuilder().constructCategory(1L).build());

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }
}
