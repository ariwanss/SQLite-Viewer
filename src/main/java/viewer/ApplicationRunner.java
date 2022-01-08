package viewer;

import java.sql.*;

public class ApplicationRunner {

    private static String url = "jdbc:sqlite:D:/portfolio/SQLite Viewer/src/main/java/databases/";

    public static void main(String[] args) {
        /*createDatabase("firstDatabase.db");
        createDatabase("secondDatabase.db");
        createContactTable("firstDatabase.db");
        createGroupTable("firstDatabase.db");
        createProjectTable("secondDatabase.db");*/
        new SQLiteViewer();

    }

    public static void createDatabase(String dbName) {
        try (Connection conn = DriverManager.getConnection(url + dbName)) {
            if (conn != null) {
                DatabaseMetaData metaData = conn.getMetaData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection connect(String dbName) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url + dbName);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void createTable(String dbName, String tableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "ID INT PRIMARY KEY," +
                "NAME VARCHAR" +
                ");";

        try (
                Connection conn = connect(dbName);
                Statement statement = conn.createStatement()
                ) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createContactTable(String dbName) {
        String sql = "CREATE TABLE IF NOT EXISTS contacts(tcontact_id INTEGER PRIMARY KEY," +
                "tfirst_name TEXT NOT NULL," +
                "tlast_name TEXT NOT NULL," +
                "temail TEXT NOT NULL UNIQUE," +
                "tphone TEXT NOT NULL UNIQUE);";

        try (
                Connection conn = connect(dbName);
                Statement statement = conn.createStatement()
        ) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createGroupTable(String dbName) {
        String sql = "CREATE TABLE IF NOT EXISTS groups(group_id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL);";

        try (
                Connection conn = connect(dbName);
                Statement statement = conn.createStatement()
        ) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createProjectTable(String dbName) {
        String sql = "CREATE TABLE IF NOT EXISTS projects(tid integer PRIMARY KEY," +
                "tname text NOT NULL," +
                "tbegin_date text," +
                "tend_date text);";

        try (
                Connection conn = connect(dbName);
                Statement statement = conn.createStatement()
        ) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
