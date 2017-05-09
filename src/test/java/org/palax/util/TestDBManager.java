package org.palax.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class TestDBManager {

    public static final DataSourceManager ds = new TestDataSourceManager();

    public static void setUpTestDDL() {
        try(Connection conn = ds.getConnection();
            Statement stm = conn.createStatement()) {

            stm.execute("DROP DATABASE IF EXISTS `student_testing_db_test`");

            Path path = Paths.get(ClassLoader.getSystemResource("student_testing_db_test_DDL.sql").toURI());
            List<String> sqlList = Files.readAllLines(path);
            for (String sql : sqlList) {
                stm.addBatch(sql);
            }

            stm.executeBatch();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void setUpTestDML() {
        try(Connection conn = ds.getConnection();
            Statement stm = conn.createStatement()) {

            stm.addBatch("DELETE FROM `student_testing_db_test`.`complete_test`");
            stm.addBatch("DELETE FROM `student_testing_db_test`.`answer`");
            stm.addBatch("DELETE FROM `student_testing_db_test`.`question`");
            stm.addBatch("DELETE FROM `student_testing_db_test`.`test`");
            stm.addBatch("DELETE FROM `student_testing_db_test`.`user`");
            stm.addBatch("DELETE FROM `student_testing_db_test`.`category`");
            stm.addBatch("DELETE FROM `student_testing_db_test`.`role`");

            Path path = Paths.get(ClassLoader.getSystemResource("student_testing_db_test_DML.sql").toURI());
            List<String> sqlList = Files.readAllLines(path);
            for (String sql : sqlList) {
                stm.addBatch(sql);
            }

            stm.executeBatch();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
