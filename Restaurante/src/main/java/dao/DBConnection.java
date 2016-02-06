package dao;

import java.sql.*;

public class DBConnection {
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost/restaurant?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";
    private static Connection connection;


    public static void connect() {
        try
        {
            Class.forName(JDBC_DRIVER);
            System.out.println("Driver created");
            connection = DriverManager.getConnection (JDBC_URL,JDBC_USERNAME, JDBC_PASSWORD);
            System.out.println("Connection stablished");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(7);
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

}
