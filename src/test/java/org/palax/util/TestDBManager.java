package org.palax.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class TestDBManager {

    private static final DataSourceManager ds = new TestDataSourceManager();
    private static final String DB_CATALOG = "`student_testing_db_test`";
    private static final String DDL_SQL_FILE_NAME = "student_testing_db_test_DDL.sql";
    private static final String DML_SQL_FILE_NAME = "student_testing_db_test_DML.sql";

    public static void setUpTestDDL() {
        try(Connection conn = ds.getConnection();
            Statement stm = conn.createStatement()) {

            stm.execute("DROP DATABASE IF EXISTS " + DB_CATALOG);

            Path path = Paths.get(ClassLoader.getSystemResource(DDL_SQL_FILE_NAME).toURI());
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

            stm.addBatch("DELETE FROM " + DB_CATALOG + ".`complete_test`");
            stm.addBatch("DELETE FROM " + DB_CATALOG + ".`answer`");
            stm.addBatch("DELETE FROM " + DB_CATALOG + ".`question`");
            stm.addBatch("DELETE FROM " + DB_CATALOG + ".`test`");
            stm.addBatch("DELETE FROM " + DB_CATALOG + ".`user`");
            stm.addBatch("DELETE FROM " + DB_CATALOG + ".`category`");
            stm.addBatch("DELETE FROM " + DB_CATALOG + ".`role`");

            Path path = Paths.get(ClassLoader.getSystemResource(DML_SQL_FILE_NAME).toURI());
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
