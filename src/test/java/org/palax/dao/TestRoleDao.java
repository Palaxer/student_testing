package org.palax.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.palax.dao.data.RoleBuilder;
import org.palax.dao.mysql.MySQLRoleDao;
import org.palax.entity.Role;
import org.palax.util.DataSourceManager;
import org.palax.util.TestDBManager;
import org.palax.util.TestDataSourceManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TestRoleDao {

    private static RoleDao roleDao;

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
        TestDBManager.setUpTestDML();
        roleDao = new MySQLRoleDao(new TestDataSourceManager());
    }

    @Test
    public void integrityTestFindAll() throws SQLException {
        List<Role> expectedRoles = new ArrayList<>();
        expectedRoles.add(RoleBuilder.getBuilder().constructRole(Role.ADMIN).build());
        expectedRoles.add(RoleBuilder.getBuilder().constructRole(Role.STUDENT).build());
        expectedRoles.add(RoleBuilder.getBuilder().constructRole(Role.TUTOR).build());

        List<Role> actualRoles =  roleDao.findAll();

        assertEquals(expectedRoles, actualRoles);
    }

    @Test
    public void testFindAll() throws SQLException {
        RoleDao roleDao = new MySQLRoleDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        roleDao.findAll();

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestFind() {
        Role expectedRole = RoleBuilder.getBuilder().constructRole(Role.ADMIN).build();

        Role actualRole = roleDao.find(Role.ADMIN);

        assertEquals(expectedRole, actualRole);
    }

    @Test
    public void testFind() throws SQLException {
        RoleDao roleDao = new MySQLRoleDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        roleDao.find(Role.ADMIN);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }
}
