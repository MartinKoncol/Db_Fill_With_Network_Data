package com.martink.downloadanddbfill;

import java.sql.*;

public class JDBC {

    private Connection connection;
    private Statement statement;
    private String[] tables = {"Obec", "Cast_Obce"};
    private String[] columns = {"KodObce", "NazevObce", "NazevCastiObce", "KodCastiObce"};

    public void connection() {

        String url = "jdbc:mysql://sql11.freesqldatabase.com:3306/sql11663535";
        String username = "sql11663535";
        String password = "s1rNpZ5unl";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to DB: " + url + " under " + username);
            statement = connection.createStatement();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void databaseInsert(String tagName, String val1, String val2, String val3) throws SQLException {
        String sqlins = "";
        if (tagName.equals("vf:Obec")) {

            sqlins = "INSERT INTO " + tables[0] + "(" + columns[0] + "," + columns[1] + ") values (" + val1 + ", '" + val2 + "')";

        } else if (tagName.equals("vf:CastObce")) {

            sqlins = "INSERT INTO " + tables[1] + "(" + columns[0] + "," + columns[2] + "," + columns[3] + ") values (" + val1 + ", '" + val2 + "'," + val3 + ")";

        }
        System.out.println(sqlins);
        statement.executeUpdate(sqlins);
    }

    public void databaseSelect(String table) throws SQLException {

        ResultSet resultSet = statement.executeQuery("select * from " + table);
        System.out.println("TABLE: " + table + "\n===========================");

        ResultSetMetaData rsmd = resultSet.getMetaData();

        int columnsNumber = rsmd.getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++)
                System.out.print(resultSet.getString(i) + " ");
            System.out.println();
        }
    }

    public void closeDbConnection() throws SQLException {
        connection.close();
        System.out.println("===========================\nDB connection closed");
    }
}
