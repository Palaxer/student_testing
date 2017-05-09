package org.palax.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.palax.dao.data.CompleteTestBuilder;
import org.palax.dao.data.RoleBuilder;
import org.palax.dao.data.TestBuilder;
import org.palax.dao.data.UserBuilder;
import org.palax.dao.mysql.MySQLCompleteTestDao;
import org.palax.entity.CompleteTest;
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
public class TestCompleteTestDao {

    private static CompleteTestDao completeTestDao;

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
        completeTestDao = new MySQLCompleteTestDao(new TestDataSourceManager());
        TestDBManager.setUpTestDML();
    }

    @Test
    public void integrityTestFindAllByStudent() throws SQLException {
        UserBuilder userBuilder = UserBuilder.getBuilder().constructUser(4L, RoleBuilder.getBuilder().constructRole(Role.STUDENT));
        List<CompleteTest> expectedCompleteTests = new ArrayList<>();
        expectedCompleteTests.add(CompleteTestBuilder.getBuilder().constructCompleteTest(1L, true,
                userBuilder, TestBuilder.getBuilder().constructTest(1L)).build());


        List<CompleteTest> actualCompleteTests =  completeTestDao.findAllByStudent(userBuilder.build(), 0, 10);

        assertEquals(expectedCompleteTests, actualCompleteTests);
    }

    @Test
    public void testFindAllByStudent() throws SQLException {
        CompleteTestDao testDao = new MySQLCompleteTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        UserBuilder userBuilder = UserBuilder.getBuilder().constructUser(4L, RoleBuilder.getBuilder().constructRole(Role.STUDENT));

        testDao.findAllByStudent(userBuilder.build(), 0, 10);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestFindAllByTest() {
        TestBuilder testBuilder = TestBuilder.getBuilder().constructTest(1L);
        List<CompleteTest> expectedCompleteTests = new ArrayList<>();
        expectedCompleteTests.add(CompleteTestBuilder.getBuilder().constructCompleteTest(1L, true,
                UserBuilder.getBuilder().constructUser(4L, RoleBuilder.getBuilder().constructRole(Role.STUDENT)),
                testBuilder).build());
        expectedCompleteTests.add(CompleteTestBuilder.getBuilder().constructCompleteTest(2L, false,
                UserBuilder.getBuilder().constructUser(1L, RoleBuilder.getBuilder().constructRole(Role.ADMIN)),
                testBuilder).build());

        List<CompleteTest> actualCompleteTests =  completeTestDao.findAllByTest(testBuilder.build());

        assertEquals(expectedCompleteTests, actualCompleteTests);
    }

    @Test
    public void testFindAllByTest() throws SQLException {
        CompleteTestDao completeTestDao = new MySQLCompleteTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        TestBuilder testBuilder = TestBuilder.getBuilder().constructTest(1L);

        completeTestDao.findAllByTest(testBuilder.build());

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestCountByUser() {
        long expectedCount = 1;

        long actualCount = completeTestDao.countByUser(UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build());

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountByUser() throws SQLException {
        CompleteTestDao completeTestDao = new MySQLCompleteTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.createStatement()).thenReturn(stm);
        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        completeTestDao.countByUser(UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build());

        verify(con, times(1)).close();
        verify(stm, times(1)).close();
    }

    @Test
    public void integrityTestCountByTest() {
        long expectedCount = 2;

        long actualCount = completeTestDao.countByTest(TestBuilder.getBuilder().constructTest(1L).build());

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountByTest() throws SQLException {
        CompleteTestDao completeTestDao = new MySQLCompleteTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.createStatement()).thenReturn(stm);
        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        completeTestDao.countByTest(TestBuilder.getBuilder().constructTest(1L).build());

        verify(con, times(1)).close();
        verify(stm, times(1)).close();
    }

    @Test
    public void integrityTestCountByTestAndPassed() {
        long expectedCount = 1;

        long actualCount = completeTestDao.countByTestAndPassed(TestBuilder.getBuilder().constructTest(1L).build(), true);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountByTestAndPassed() throws SQLException {
        CompleteTestDao completeTestDao = new MySQLCompleteTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.createStatement()).thenReturn(stm);
        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        completeTestDao.countByTestAndPassed(TestBuilder.getBuilder().constructTest(1L).build(), true);

        verify(con, times(1)).close();
        verify(stm, times(1)).close();
    }

    @Test
    public void integrityTestFindById() {
        CompleteTest expectedCompleteTest = CompleteTestBuilder.getBuilder().constructCompleteTest(1L, true,
                UserBuilder.getBuilder().constructUser(4L, RoleBuilder.getBuilder().constructRole(Role.STUDENT)),
                TestBuilder.getBuilder().constructTest(1L)).build();

        CompleteTest actualCompleteTest = completeTestDao.findById(expectedCompleteTest.getId());

        assertEquals(expectedCompleteTest, actualCompleteTest);
    }

    @Test
    public void testFindById() throws SQLException {
        CompleteTestDao completeTestDao = new MySQLCompleteTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        completeTestDao.findById(1L);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestUpdate() {
        CompleteTest completeTest = CompleteTestBuilder.getBuilder().constructCompleteTest(1L, true,
                UserBuilder.getBuilder().constructUser(4L, RoleBuilder.getBuilder().constructRole(Role.STUDENT)),
                TestBuilder.getBuilder().constructTest(1L)).build();
        completeTest.setScore(20);

        assertTrue(completeTestDao.update(completeTest));
        assertEquals(completeTest, completeTestDao.findById(completeTest.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testUpdate() throws SQLException {
        CompleteTestDao completeTestDao = new MySQLCompleteTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        CompleteTest completeTest = CompleteTestBuilder.getBuilder().constructCompleteTest(1L, true,
                UserBuilder.getBuilder().constructUser(4L, RoleBuilder.getBuilder().constructRole(Role.STUDENT)),
                TestBuilder.getBuilder().constructTest(1L)).build();

        completeTestDao.update(completeTest);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestInsert() {
        CompleteTest completeTest = CompleteTestBuilder.getBuilder().constructCompleteTest(10L, true,
                UserBuilder.getBuilder().constructUser(4L, RoleBuilder.getBuilder().constructRole(Role.STUDENT)),
                TestBuilder.getBuilder().constructTest(1L)).build();

        assertTrue(completeTestDao.insert(completeTest));
        assertEquals(completeTest, completeTestDao.findById(completeTest.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testInsert() throws SQLException {
        CompleteTestDao completeTestDao = new MySQLCompleteTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        CompleteTest completeTest = CompleteTestBuilder.getBuilder().constructCompleteTest(10L, true,
                UserBuilder.getBuilder().constructUser(4L, RoleBuilder.getBuilder().constructRole(Role.STUDENT)),
                TestBuilder.getBuilder().constructTest(1L)).build();

        completeTestDao.insert(completeTest);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestDelete() {
        CompleteTest completeTest = CompleteTestBuilder.getBuilder().constructCompleteTest(1L, true,
                UserBuilder.getBuilder().constructUser(4L, RoleBuilder.getBuilder().constructRole(Role.STUDENT)),
                TestBuilder.getBuilder().constructTest(1L)).build();

        assertTrue(completeTestDao.delete(completeTest));
        assertNull(completeTestDao.findById(completeTest.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testDelete() throws SQLException {
        CompleteTestDao completeTestDao = new MySQLCompleteTestDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        CompleteTest completeTest = CompleteTestBuilder.getBuilder().constructCompleteTest(1L, true,
                UserBuilder.getBuilder().constructUser(4L, RoleBuilder.getBuilder().constructRole(Role.STUDENT)),
                TestBuilder.getBuilder().constructTest(1L)).build();

        completeTestDao.delete(completeTest);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }
}
