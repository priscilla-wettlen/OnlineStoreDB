package model;

import org.w3c.dom.ls.LSOutput;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://pgserver.mau.se/onlinestoreaj6817";
    private static final String USER = "aj6817";
    private static final String PASSWORD = System.getenv("CRED");


    public static void main(String[] args) {
        Connection connection = null;

        try {

            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully.");


            //Test query
            String query = "SELECT c_first_name FROM customer";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                //Test query result
                while (resultSet.next()) {
                    String name = resultSet.getString("c_first_name");
                    System.out.println("Name: " + name);
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found. Add it to your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection to the database failed!");
            e.printStackTrace();
        } finally {
            // Close the connection
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}