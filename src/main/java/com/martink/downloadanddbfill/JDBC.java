package com.martink.downloadanddbfill;

import java.sql.*;

public class JDBC {

    private Connection connection;
    private Statement statement;
    private String[] columns = {"KodObce", "NazevObce", "NazevCastiObce", "KodCastiObce"};

    public void connection()  {

        String url = "jdbc:mysql://sql11.freesqldatabase.com:3306/sql11663535";
        String username = "sql11663535";
        String password = "s1rNpZ5unl";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);

            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from Obec");

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void databaseInsert(String val1,String val2, String val3) throws SQLException {

        statement.executeUpdate("insert into Obec (" + columns[0] + "," + columns[1] + ") values ('val1',val2)");
    }

    public void closeDbConnection() throws SQLException {
        connection.close();
    }
}
