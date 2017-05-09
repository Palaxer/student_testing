package org.palax.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.palax.dao.data.RoleBuilder;
import org.palax.dao.data.UserBuilder;
import org.palax.dao.mysql.MySQLUserDao;
import org.palax.entity.Role;
import org.palax.entity.User;
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
public class TestUserDao {

    private static UserDao userDao;

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
        userDao = new MySQLUserDao(new TestDataSourceManager());
        TestDBManager.setUpTestDML();
    }

    @Test
    public void integrityTestFindAll() throws SQLException {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build());
        expectedUsers.add(UserBuilder.getBuilder().constructUser(2L,
                RoleBuilder.getBuilder().constructRole(Role.TUTOR)).build());
        expectedUsers.add(UserBuilder.getBuilder().constructUser(3L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build());
        expectedUsers.add(UserBuilder.getBuilder().constructUser(4L,
                RoleBuilder.getBuilder().constructRole(Role.STUDENT)).build());

        List<User> actualUsers =  userDao.findAll(0, 10);

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testFindAll() throws SQLException {
        UserDao userDao = new MySQLUserDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        userDao.findAll(0, 10);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestFindAllByRole() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build());
        expectedUsers.add(UserBuilder.getBuilder().constructUser(3L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build());

        List<User> actualUsers =  userDao.findAllByRole(Role.ADMIN,0, 10);

        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testFindAllByRole() throws SQLException {
        UserDao userDao = new MySQLUserDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        userDao.findAllByRole(Role.ADMIN, 0, 10);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestCount() {
        long expectedCount = 4;

        long actualCount = userDao.count();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCount() throws SQLException {
        UserDao userDao = new MySQLUserDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.createStatement()).thenReturn(stm);
        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        userDao.count();

        verify(con, times(1)).close();
        verify(stm, times(1)).close();
    }

    @Test
    public void integrityTestCountByRole() {
        long expectedCount = 2;

        long actualCount = userDao.countByRole(Role.ADMIN);

        assertEquals(expectedCount, actualCount);

        expectedCount = 1;

        actualCount = userDao.countByRole(Role.TUTOR);

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountByRole() throws SQLException {
        UserDao userDao = new MySQLUserDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.createStatement()).thenReturn(stm);
        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        userDao.countByRole(Role.TUTOR);

        verify(con, times(1)).close();
        verify(stm, times(1)).close();
    }

    @Test
    public void integrityTestFindById() {
        User expectedUser = UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build();

        User actualUser =  userDao.findById(expectedUser.getId());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testFindById() throws SQLException {
        UserDao userDao = new MySQLUserDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        userDao.findById(1L);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestFindByLogin() {
        User expectedUser = UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build();

        User actualUser =  userDao.findByLogin(expectedUser.getLogin());

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testFindByLogin() throws SQLException {
        UserDao userDao = new MySQLUserDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        userDao.findByLogin("login");

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestUpdate() {
        User user = UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build();
        user.setName("update");

        assertTrue(userDao.update(user));
        assertEquals(user, userDao.findById(user.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testUpdate() throws SQLException {
        UserDao userDao = new MySQLUserDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        userDao.update(UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build());

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestInsert() {
        User user = UserBuilder.getBuilder().constructUser(5L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build();

        assertTrue(userDao.insert(user));
        assertEquals(user, userDao.findById(user.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testInsert() throws SQLException {
        UserDao userDao = new MySQLUserDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        userDao.insert(UserBuilder.getBuilder().constructUser(5L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build());

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestDelete() {
        User user = UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build();

        assertTrue(userDao.delete(user));
        assertNull(userDao.findById(user.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testDelete() throws SQLException {
        UserDao userDao = new MySQLUserDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        userDao.delete(UserBuilder.getBuilder().constructUser(1L,
                RoleBuilder.getBuilder().constructRole(Role.ADMIN)).build());

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }
}
