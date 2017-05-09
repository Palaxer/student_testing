package org.palax.dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.palax.dao.data.AnswerBuilder;
import org.palax.dao.data.QuestionBuilder;
import org.palax.dao.mysql.MySQLAnswerDao;
import org.palax.entity.Answer;
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
public class TestAnswerDao {

    private static AnswerDao answerDao;

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
        answerDao = new MySQLAnswerDao(new TestDataSourceManager());
        TestDBManager.setUpTestDML();
    }

    @Test
    public void integrityTestFindAllByQuestion() {
        QuestionBuilder questionBuilder = QuestionBuilder.getBuilder().constructQuestion(1L);
        List<Answer> expectedAnswers = new ArrayList<>();
        expectedAnswers.add(AnswerBuilder.getBuilder().constructAnswer(1L, true, questionBuilder).build());

        List<Answer> actualAnswers =  answerDao.findAllByQuestion(questionBuilder.build());

        assertEquals(expectedAnswers, actualAnswers);
    }

    @Test
    public void testFindAllByQuestion() throws SQLException {
        AnswerDao answerDao = new MySQLAnswerDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        QuestionBuilder questionBuilder = QuestionBuilder.getBuilder().constructQuestion(1L);

        answerDao.findAllByQuestion(questionBuilder.build());

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestFindById() {
        QuestionBuilder questionBuilder = QuestionBuilder.getBuilder().constructQuestion(1L);
        Answer expectedAnswers = AnswerBuilder.getBuilder().constructAnswer(1L, true, questionBuilder).build();

        Answer actualAnswers = answerDao.findById(expectedAnswers.getId());

        assertEquals(expectedAnswers, actualAnswers);
    }

    @Test
    public void testFindById() throws SQLException {
        AnswerDao answerDao = new MySQLAnswerDao(ds);
        when(ds.getConnection()).thenReturn(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        answerDao.findById(1L);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestUpdate() {
        QuestionBuilder questionBuilder = QuestionBuilder.getBuilder().constructQuestion(1L);
        Answer answer = AnswerBuilder.getBuilder().constructAnswer(1L, true, questionBuilder).build();
        answer.setText("update");

        assertTrue(answerDao.update(answer));
        assertEquals(answer, answerDao.findById(answer.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testUpdate() throws SQLException {
        AnswerDao answerDao = new MySQLAnswerDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        QuestionBuilder questionBuilder = QuestionBuilder.getBuilder().constructQuestion(1L);
        Answer answer = AnswerBuilder.getBuilder().constructAnswer(1L, true, questionBuilder).build();

        answerDao.update(answer);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestInsert() {
        QuestionBuilder questionBuilder = QuestionBuilder.getBuilder().constructQuestion(1L);
        Answer answer = AnswerBuilder.getBuilder().constructAnswer(3L, true, questionBuilder).build();

        assertTrue(answerDao.insert(answer));
        assertEquals(answer, answerDao.findById(answer.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testInsert() throws SQLException {
        AnswerDao answerDao = new MySQLAnswerDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString(), anyInt())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        QuestionBuilder questionBuilder = QuestionBuilder.getBuilder().constructQuestion(1L);
        Answer answer = AnswerBuilder.getBuilder().constructAnswer(3L, true, questionBuilder).build();

        answerDao.insert(answer);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }

    @Test
    public void integrityTestDelete() {
        QuestionBuilder questionBuilder = QuestionBuilder.getBuilder().constructQuestion(1L);
        Answer answer = AnswerBuilder.getBuilder().constructAnswer(1L, true, questionBuilder).build();

        assertTrue(answerDao.delete(answer));
        assertNull(answerDao.findById(answer.getId()));
        TestDBManager.setUpTestDML();
    }

    @Test
    public void testDelete() throws SQLException {
        AnswerDao answerDao = new MySQLAnswerDao(ds);
        when(ds.getConnection()).thenReturn(con);
        doAnswer(e -> {((Connection)e.getArguments()[0]).close(); return null;}).when(ds).closeConnection(con);
        when(con.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(0);

        QuestionBuilder questionBuilder = QuestionBuilder.getBuilder().constructQuestion(1L);
        Answer answer = AnswerBuilder.getBuilder().constructAnswer(1L, true, questionBuilder).build();

        answerDao.delete(answer);

        verify(con, times(1)).close();
        verify(ps, times(1)).close();
    }
}
