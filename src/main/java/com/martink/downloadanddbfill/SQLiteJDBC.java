package com.martink.downloadanddbfill;

import org.apache.log4j.BasicConfigurator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import static com.martink.downloadanddbfill.Constants.LOGGER;
import static com.martink.downloadanddbfill.Constants.MUNICIPALITY_CODE;
import static com.martink.downloadanddbfill.Constants.MUNICIPALITY_NAME;
import static com.martink.downloadanddbfill.Constants.MUNICIPALITY_SECTION_CODE;
import static com.martink.downloadanddbfill.Constants.MUNICIPALITY_SECTION_NAME;
import static com.martink.downloadanddbfill.Constants.TABLE_MUNICIPALITY;
import static com.martink.downloadanddbfill.Constants.TABLE_MUNICIPALITY_SECTION;
import static com.martink.downloadanddbfill.Constants.TAG_NAME_MUNICIPALITY;
import static com.martink.downloadanddbfill.Constants.TAG_NAME_MUNICIPALITY_SECTION;

public class SQLiteJDBC {

    private Connection con = null;
    Statement stmt = null;

    public static void main(String[] args) {
        SQLiteJDBC delete = new SQLiteJDBC();

        BasicConfigurator.configure();

        delete.connectionDB();
        delete.dropTable(TABLE_MUNICIPALITY);
        delete.dropTable(TABLE_MUNICIPALITY_SECTION);
    }

    public void connectionDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:test.db");
            LOGGER.info("Opened database successfully");
        } catch (Exception e) {
            exception(e);
        }
    }

    public void createTable(String table) {
        try {
            stmt = con.createStatement();

            if (table.equals(TABLE_MUNICIPALITY)) {

                String sql = "CREATE TABLE " + table +
                        "(" + MUNICIPALITY_CODE + " INT PRIMARY KEY NOT NULL," +
                        MUNICIPALITY_NAME + " TEXT NOT NULL )";

                stmt.executeUpdate(sql);

            } else if (table.equals(TABLE_MUNICIPALITY_SECTION)) {

                String sql = "CREATE TABLE " + table +
                        "(" + MUNICIPALITY_CODE + " INT NOT NULL," +
                        MUNICIPALITY_SECTION_NAME + " TEXT NOT NULL," +
                        MUNICIPALITY_SECTION_CODE + " INT )";

                stmt.executeUpdate(sql);

            } else {
                LOGGER.info("Table " + table + " create statement not defined");
                closeConnectionDB();
                System.exit(0);
            }

            LOGGER.info("Table: " + table + " created");
            stmt.close();

        } catch (Exception e) {
            exception(e);
        }

    }

    public void dropTable(String table) {
        try {
            stmt = con.createStatement();
            String sql = "DROP TABLE " + table;
            stmt.executeUpdate(sql);
            LOGGER.info("Table: " + table + " dropped");
            stmt.close();
        } catch (Exception e) {
            exception(e);
        }

    }

    public void databaseInsert(String tagName, String val1, String val2, String val3) {
        String sqlins = "";
        try {
            stmt = con.createStatement();
            if (tagName.equals(TAG_NAME_MUNICIPALITY)) {

                sqlins = "INSERT INTO " + TABLE_MUNICIPALITY +
                        "(" + MUNICIPALITY_CODE + ","
                        + MUNICIPALITY_NAME + ") " +
                        "values (" + val1 + ", '" + val2 + "')";

            } else if (tagName.equals(TAG_NAME_MUNICIPALITY_SECTION)) {

                sqlins = "INSERT INTO " + TABLE_MUNICIPALITY_SECTION +
                        "(" + MUNICIPALITY_CODE + ","
                        + MUNICIPALITY_SECTION_NAME + ","
                        + MUNICIPALITY_SECTION_CODE + ")" +
                        " values (" + val1 + ", '" + val2 + "'," + val3 + ")";

            }
            LOGGER.info(sqlins);
            stmt.executeUpdate(sqlins);
            stmt.close();
        } catch (Exception e) {
            exception(e);
        }
    }

    public void databaseSelect(String table) {
        try {
            stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from " + table);
            LOGGER.info("TABLE: %s".formatted(table));

            ResultSetMetaData rsmd = resultSet.getMetaData();

            int columnsNumber = rsmd.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnValue = resultSet.getString(i);
                    LOGGER.info(rsmd.getColumnName(i) + " " + columnValue);
                }
                LOGGER.info("*********");
            }
        } catch (Exception e) {
            exception(e);
        }
    }

    public void closeConnectionDB() {
        try {
            con.close();
            LOGGER.info("Database connection closed");
        } catch (SQLException e) {
            LOGGER.error(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void exception(Exception e) {
        LOGGER.error(e.getClass().getName() + ": " + e.getMessage());
        closeConnectionDB();
        System.exit(0);
    }

}