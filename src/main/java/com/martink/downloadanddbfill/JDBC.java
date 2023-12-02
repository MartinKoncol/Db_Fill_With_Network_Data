package com.martink.downloadanddbfill;

import static com.martink.downloadanddbfill.Constants.TAG_NAME_MUNICIPALITY;
import static com.martink.downloadanddbfill.Constants.TAG_NAME_MUNICIPALITY_SECTION;
import static com.martink.downloadanddbfill.Constants.TABLE_MUNICIPALITY;
import static com.martink.downloadanddbfill.Constants.TABLE_MUNICIPALITY_SECTION;
import static com.martink.downloadanddbfill.Constants.MUNICIPALITY_NAME;
import static com.martink.downloadanddbfill.Constants.MUNICIPALITY_CODE;
import static com.martink.downloadanddbfill.Constants.MUNICIPALITY_SECTION_NAME;
import static com.martink.downloadanddbfill.Constants.MUNICIPALITY_SCETION_CODE;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import org.apache.log4j.Logger;


public class JDBC {

    Logger logger = Logger.getLogger(JDBC.class);
    private Connection connection;
    private Statement statement;

    public void connection() {
        String url = "jdbc:mysql://sql11.freesqldatabase.com:3306/sql11663535";
        String username = "sql11663535";
        String password = "s1rNpZ5unl";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            logger.info("Connected to DB: %s under %s".formatted(url, username));
            statement = connection.createStatement();

        } catch (Exception e) {
            logger.error("Unable to connect to DB: " + e);System.exit(-1);
        }

    }

    public void databaseInsert(String tagName, String val1, String val2, String val3) throws SQLException {
        String sqlins = "";
        if (tagName.equals(TAG_NAME_MUNICIPALITY)) {

            sqlins = "INSERT INTO " + TABLE_MUNICIPALITY + "(" + MUNICIPALITY_CODE + "," + MUNICIPALITY_NAME + ") values (" + val1 + ", '" + val2 + "')";

        } else if (tagName.equals(TAG_NAME_MUNICIPALITY_SECTION)) {

            sqlins = "INSERT INTO " + TABLE_MUNICIPALITY_SECTION + "(" + MUNICIPALITY_CODE + "," + MUNICIPALITY_SECTION_NAME + "," + MUNICIPALITY_SCETION_CODE + ") values (" + val1 + ", '" + val2 + "'," + val3 + ")";

        }
        logger.info(sqlins);
        statement.executeUpdate(sqlins);
    }

    public void databaseSelect(String table) throws SQLException {

        ResultSet resultSet = statement.executeQuery("select * from " + table);
        logger.info("TABLE: %s".formatted(table));

        ResultSetMetaData rsmd = resultSet.getMetaData();

        int columnsNumber = rsmd.getColumnCount();

        while (resultSet.next()) {
            for (int i =1;i <= columnsNumber;i++) {
                String columnValue = resultSet.getString(i);
                logger.info(rsmd.getColumnName(i) + " " + columnValue);
            }
            logger.info("*********");
        }
    }

    public void closeDbConnection() throws SQLException {
        connection.close();
        logger.info("DB connection closed");
    }
}
