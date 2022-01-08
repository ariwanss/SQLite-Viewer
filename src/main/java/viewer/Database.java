package viewer;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String path;
    private DefaultTableModel tableModel;

    public Database(String path) {
        this.path = "jdbc:sqlite:" + path;
    }

    public Connection connect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(path);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public List<String> listTables() {
        String sql = "SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';;";
        List<String> tables = new ArrayList<>();

        try (
                Connection conn = connect();
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                ) {
            while (resultSet.next()) {
                tables.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tables;
    }

    public DefaultTableModel executeQuery(String sql) {
        try (
                Connection conn = connect();
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                ) {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnCount = rsmd.getColumnCount();
            List<String> columnNames = new ArrayList<>();

            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }

            List<List<Object>> dataList = new ArrayList<>();

            while (resultSet.next()) {
                List<Object> row = new ArrayList<>();

                for (String column : columnNames) {
                    row.add(resultSet.getObject(column));
                }

                dataList.add(row);
            }

            String[] columns = columnNames.toArray(String[]::new);
            Object[][] data = dataList.stream().map(x -> x.toArray(Object[]::new)).toArray(Object[][]::new);

            //table.setColumns(columns);
            //table.setData(data);

            //System.out.println(columnNames);
            //dataList.forEach(System.out::println);

            tableModel = new DefaultTableModel(data, columns);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableModel;
    }
}
