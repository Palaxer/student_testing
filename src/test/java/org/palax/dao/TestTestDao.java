package org.palax.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.palax.dao.data.CategoryBuilder;
import org.palax.dao.data.RoleBuilder;
import org.palax.dao.data.TestBuilder;
import org.palax.dao.data.UserBuilder;
import org.palax.dao.mysql.MySQLTestDao;
import org.palax.entity.Role;
import org.palax.util.DataSourceManager;
import org.palax.util.TestDBManager;
import org.palax.util.TestDataSourceManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestTestDao {

    private static TestDao testDao;

    @Mock
    private DataSourceManager ds;
    @Mock
    private Connection con;
    @Mock
    private PreparedStatement ps;
    @Mock
    private Statement stm;
    @Mock
    private ResultSet rs;

    @BeforeClass
    public static void setUpClass() {
        testDao = new MySQLTestDao(new TestDataSourceManager());
        TestDBManager.setUpTestDML();
    }

    @Test
    public void integrityTestFindAllByActive() throws SQLException {
        List<org.palax.entity.Test> expectedTests = new ArrayList<>();
        expectedTests.add(TestBuilder.getBuilder().constructTest(1L, true,
                UserBuilder.getBuilder().constructUser(1L),
                CategoryBuilder.getBuilder().constructCategory(1L)).build());
        expectedTests.add(TestBuilder.getBuilder().constructTest(2L, true,
                UserBuilder.getBuilder().constructUser(3L),
                CategoryBuilder.getBuilder().constructCategory(2L)).build());


        List<org.palax.entity.Test> actualTests =  testDao.findAllByActive(true, 0, 10);

        assertEquals(expectedTests, actualTests);
    }

    @Test
    public void testFindAllByActive() throws SQLException {
        TestDao testDao = new MySQLTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        testDao.findAllByActive(true, 0, 10);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestFindAllByCategoryAndActive() {
        CategoryBuilder categoryBuilder = CategoryBuilder.getBuilder().constructCategory(1L);
        List<org.palax.entity.Test> expectedTests = new ArrayList<>();
        expectedTests.add(TestBuilder.getBuilder().constructTest(1L, true,
                UserBuilder.getBuilder().constructUser(1L),
                categoryBuilder).build());

        List<org.palax.entity.Test> actualTests =  testDao.findAllByCategoryAndActive(categoryBuilder.build(), true, 0, 10);

        assertEquals(expectedTests, actualTests);
    }

    @Test
    public void testFindAllByCategoryAndActive() throws SQLException {
        TestDao testDao = new MySQLTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        testDao.findAllByCategoryAndActive(CategoryBuilder.getBuilder().constructCategory(1L).build(), true, 0, 10);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }


    @Test
    public void integrityTestFindAllByTutor() {
        UserBuilder userBuilder = UserBuilder.getBuilder().constructUser(1L);
        List<org.palax.entity.Test> expectedTests = new ArrayList<>();
        expectedTests.add(TestBuilder.getBuilder().constructTest(1L, true, userBuilder,
                CategoryBuilder.getBuilder().constructCategory(1L)).build());
        expectedTests.add(TestBuilder.getBuilder().constructTest(3L, false, userBuilder,
                CategoryBuilder.getBuilder().constructCategory(1L)).build());


        List<org.palax.entity.Test> actualTests =  testDao.findAllByTutor(userBuilder.build(), 0, 10);

        assertEquals(expectedTests, actualTests);
    }

    @Test
    public void testFindAllByTutor() throws SQLException {
        TestDao testDao = new MySQLTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        testDao.findAllByTutor(UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build(), 0, 10);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestCountByActive() {
        long expectedCount = 2;

        long actualCount = testDao.countByActive(true);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountByActive() throws SQLException {
        TestDao testDao = new MySQLTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.createStatement()).thenReturn(stm);
        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        testDao.countByActive(true);

        verify(con, times(1)).close();
        verify(stm, times(1)).close();
    }

    @Test
    public void integrityTestCountByTutor() {
        long expectedCount = 2;

        long actualCount = testDao.countByTutor(UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build());

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountByTutor() throws SQLException {
        TestDao testDao = new MySQLTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.createStatement()).thenReturn(stm);
        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        testDao.countByTutor(UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build());

        verify(con, times(1)).close();
        verify(stm, times(1)).close();
    }

    @Test
    public void integrityTestCountByCategoryAndActive() {
        long expectedCount = 1;

        long actualCount = testDao.countByCategoryAndActive(CategoryBuilder.getBuilder().constructCategory(1L).build(),
                true);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountByCategoryAndActive() throws SQLException {
        TestDao testDao = new MySQLTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.createStatement()).thenReturn(stm);
        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        testDao.countByCategoryAndActive(CategoryBuilder.getBuilder().constructCategory(1L).build(), true);

        verify(con, times(1)).close();
        verify(stm, times(1)).close();
    }

    @Test
    public void integrityTestFindById() {
        org.palax.entity.Test expectedTest = TestBuilder.getBuilder().constructTest(1L, true,
                UserBuilder.getBuilder().constructUser(1L),
                CategoryBuilder.getBuilder().constructCategory(1L)).build();

        org.palax.entity.Test actualTest =  testDao.findById(expectedTest.getId());

        assertEquals(expectedTest, actualTest);
    }

    @Test
    public void testFindById() throws SQLException {
        TestDao testDao = new MySQLTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        testDao.findById(1L);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestChangeTutorInAllTest() {
        UserBuilder from = UserBuilder.getBuilder().constructUser(1L);

        UserBuilder to = UserBuilder.getBuilder().constructUser(3L);

        List<org.palax.entity.Test> expectedTests = new ArrayList<>();
        expectedTests.add(TestBuilder.getBuilder().constructTest(1L, true, to,
                CategoryBuilder.getBuilder().constructCategory(1L)).build());
        expectedTests.add(TestBuilder.getBuilder().constructTest(2L, true, to,
                CategoryBuilder.getBuilder().constructCategory(2L)).build());
        expectedTests.add(TestBuilder.getBuilder().constructTest(3L, false, to,
                CategoryBuilder.getBuilder().constructCategory(1L)).build());

        assertTrue(testDao.changeTutorInAllTest(from.build(), to.build()));

        List<org.palax.entity.Test> actualTests = testDao.findAllByTutor(to.build(), 0, 10);

        assertEquals(expectedTests, actualTests);
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testChangeTutorInAllTest() throws SQLException {
        TestDao testDao = new MySQLTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        UserBuilder from = UserBuilder.getBuilder().constructUser(1L);
        UserBuilder to = UserBuilder.getBuilder().constructUser(3L);

        testDao.changeTutorInAllTest(from.build(), to.build());

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestUpdate() {
        org.palax.entity.Test test = TestBuilder.getBuilder().constructTest(1L, true,
                UserBuilder.getBuilder().constructUser(1L),
                CategoryBuilder.getBuilder().constructCategory(1L)).build();
        test.setName("update");

        assertTrue(testDao.update(test));
        assertEquals(test, testDao.findById(test.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testUpdate() throws SQLException {
        TestDao testDao = new MySQLTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        org.palax.entity.Test test = TestBuilder.getBuilder().constructTest(1L, true,
                UserBuilder.getBuilder().constructUser(1L, RoleBuilder.getBuilder().constructRole(Role.ADMIN)),
                CategoryBuilder.getBuilder().constructCategory(1L)).build();

        testDao.update(test);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestInsert() {
        org.palax.entity.Test test = TestBuilder.getBuilder().constructTest(4L, true,
                UserBuilder.getBuilder().constructUser(1L),
                CategoryBuilder.getBuilder().constructCategory(1L)).build();

        assertTrue(testDao.insert(test));
        assertEquals(test, testDao.findById(test.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testInsert() throws SQLException {
        TestDao testDao = new MySQLTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        org.palax.entity.Test test = TestBuilder.getBuilder().constructTest(4L, true,
                UserBuilder.getBuilder().constructUser(1L, RoleBuilder.getBuilder().constructRole(Role.ADMIN)),
                CategoryBuilder.getBuilder().constructCategory(1L)).build();

        testDao.insert(test);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestDelete() {
        org.palax.entity.Test test = TestBuilder.getBuilder().constructTest(1L, true,
                UserBuilder.getBuilder().constructUser(1L, RoleBuilder.getBuilder().constructRole(Role.ADMIN)),
                CategoryBuilder.getBuilder().constructCategory(1L)).build();

        assertTrue(testDao.delete(test));
        assertNull(testDao.findById(test.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testDelete() throws SQLException {
        TestDao testDao = new MySQLTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        org.palax.entity.Test test = TestBuilder.getBuilder().constructTest(1L, true,
                UserBuilder.getBuilder().constructUser(1L, RoleBuilder.getBuilder().constructRole(Role.ADMIN)),
                CategoryBuilder.getBuilder().constructCategory(1L)).build();

        testDao.delete(test);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }
}
