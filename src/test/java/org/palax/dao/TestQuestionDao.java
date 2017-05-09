package org.palax.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.palax.dao.data.QuestionBuilder;
import org.palax.dao.data.TestBuilder;
import org.palax.dao.mysql.MySQLQuestionDao;
import org.palax.entity.Question;
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
public class TestQuestionDao {

    private static QuestionDao questionDao;

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
        questionDao = new MySQLQuestionDao(new TestDataSourceManager());
        TestDBManager.setUpTestDML();
    }

    @Test
    public void integrityTestFindAllByTest() {
        TestBuilder testBuilder = TestBuilder.getBuilder().constructTest(1L);
        List<Question> expectedQuestions = new ArrayList<>();
        expectedQuestions.add(QuestionBuilder.getBuilder().constructQuestion(1L, testBuilder).build());
        expectedQuestions.add(QuestionBuilder.getBuilder().constructQuestion(3L, testBuilder).build());

        List<Question> actualQuestions =  questionDao.findAllByTest(testBuilder.build());

        assertEquals(expectedQuestions, actualQuestions);
    }

    @Test
    public void testFindAllByTest() throws SQLException {
        QuestionDao questionDao = new MySQLQuestionDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        TestBuilder testBuilder = TestBuilder.getBuilder().constructTest(1L);

        questionDao.findAllByTest(testBuilder.build());

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestCountByTest() {
        long expectedCount = 2;

        long actualCount = questionDao.countByTest(TestBuilder.getBuilder().constructTest(1L).build());

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testCountByTest() throws SQLException {
        QuestionDao questionDao = new MySQLQuestionDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.createStatement()).thenReturn(stm);
        when(stm.executeQuery(anyString())).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        questionDao.countByTest(TestBuilder.getBuilder().constructTest(1L).build());

        verify(con, times(1)).close();
        verify(stm, times(1)).close();
    }

    @Test
    public void integrityTestFindById() {
        Question expectedQuestion = QuestionBuilder.getBuilder().constructQuestion(2L,
                TestBuilder.getBuilder().constructTest(2L)).build();

        Question actualQuestion = questionDao.findById(expectedQuestion.getId());

        assertEquals(expectedQuestion, actualQuestion);
    }

    @Test
    public void testFindById() throws SQLException {
        QuestionDao questionDao = new MySQLQuestionDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        questionDao.findById(2L);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestUpdate() {
        Question question = QuestionBuilder.getBuilder().constructQuestion(2L,
                TestBuilder.getBuilder().constructTest(2L)).build();
        question.setText("update");

        assertTrue(questionDao.update(question));
        assertEquals(question, questionDao.findById(question.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testUpdate() throws SQLException {
        QuestionDao questionDao = new MySQLQuestionDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        Question question = QuestionBuilder.getBuilder().constructQuestion(2L,
                TestBuilder.getBuilder().constructTest(2L)).build();

        questionDao.update(question);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestInsert() {
        Question question = QuestionBuilder.getBuilder().constructQuestion(4L,
                TestBuilder.getBuilder().constructTest(2L)).build();

        assertTrue(questionDao.insert(question));
        assertEquals(question, questionDao.findById(question.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testInsert() throws SQLException {
        QuestionDao questionDao = new MySQLQuestionDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        Question question = QuestionBuilder.getBuilder().constructQuestion(4L,
                TestBuilder.getBuilder().constructTest(2L)).build();

        questionDao.insert(question);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestDelete() {
        Question question = QuestionBuilder.getBuilder().constructQuestion(1L,
                TestBuilder.getBuilder().constructTest(2L)).build();

        assertTrue(questionDao.delete(question));
        assertNull(questionDao.findById(question.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testDelete() throws SQLException {
        QuestionDao questionDao = new MySQLQuestionDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        Question question = QuestionBuilder.getBuilder().constructQuestion(1L,
                TestBuilder.getBuilder().constructTest(2L)).build();

        questionDao.delete(question);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }
}
