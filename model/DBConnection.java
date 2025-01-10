package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://pgserver.mau.se/onlinestoreaj6817";
    private static final String USER = "aj6817";
    private static final String PASSWORD = "ywv0moz1";

    public Connection testConnection() {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database successfully.");

            // Test query
            String query = "SELECT c_first_name FROM customer";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

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
        }

        return connection;
    }

    public boolean insertData(Connection connection, String table, String[] data)
    {
        String query = "INSERT INTO " + table + " VALUES (" + data[0];
        for(int i = 1; i < data.length; i++)
        {
            query = query + ", " + data[i];
        }
        query = query + ")";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            

            System.out.println(query);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) into " + table + " successfully.");

        } catch (SQLException e) {
            System.out.println("Error during insert operation: " + e.getMessage());
            e.printStackTrace();
        }


        return false;
    }

    public SQLResult selectData(Connection connection, String table, String condition) {
        String query = "SELECT * FROM " + table;

        if (condition != null) {
            query += " WHERE " + condition;
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                List<String[]> data = new ArrayList<>();
                while (rs.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 0; i < columnCount; i++) {
                        row[i] = rs.getString(i + 1);
                    }
                    data.add(row);
                }

                String[][] tableData = new String[data.size()][columnCount];
                data.toArray(tableData);
                return new SQLResult(tableData);
            }
        } catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public SQLResult selectData(Connection connection, String table) {
        return selectData(connection, table, null);
    }


    public void insertCustomer(Connection connection, String tableName, int id, String firstName, String lastName,
                               String email, String address, String city, String country, String phoneNumber, String password) {
        String query = "INSERT INTO " + tableName + " (c_id, c_first_name, c_last_name, c_email, c_address, c_city, c_country, c_phonenumber, c_password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, address);
            preparedStatement.setString(6, city);
            preparedStatement.setString(7, country);
            preparedStatement.setString(8, phoneNumber);
            preparedStatement.setString(9, password);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) into " + tableName + " successfully.");

        } catch (SQLException e) {
            System.out.println("Error during insert operation: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
