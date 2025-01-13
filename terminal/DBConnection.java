package terminal;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://pgserver.mau.se/onlinestoreaj6817";
    private static final String USER = "aj6817";
    private static final String PASSWORD = System.getenv("CRED");
    private Connection conn;

    public DBConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            this.conn = DriverManager.getConnection(URL, USER, PASSWORD);
            //System.out.println("Connected to the database successfully.");

            // Test query
            String query = "SELECT c_first_name FROM customer";
            try (PreparedStatement preparedStatement = this.conn.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String name = resultSet.getString("c_first_name");
                    //System.out.println("Name: " + name);
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found. Add it to your classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection to the database failed!");
            e.printStackTrace();
        }

    }

    //-----ADMIN ACTIONS-----//

    public boolean validateAdmin(String email, String password) {
        String query = "SELECT * FROM store_admin WHERE admin_email = ? AND a_password = ?";
        boolean isValid = false;

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    isValid = true;
                }
            }

        }catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }
        return isValid;
    }

    public void addNewSupplier(){
        String query = "INSERT INTO supplier VALUES (?, ?, ?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(query)){

        }catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }
    }




    //-----CUSTOMER ACTIONS-----//
    public void insertCustomer(String firstName, String lastName,
                                      String email, String address, String city, String country, String phoneNumber, String password) {
        String query = "INSERT INTO customer" + " (c_first_name, c_last_name, c_email, c_address, c_city, c_country, c_phonenumber, c_password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, city);
            preparedStatement.setString(6, country);
            preparedStatement.setString(7, phoneNumber);
            preparedStatement.setString(8, password);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) into customer successfully.");

        } catch (SQLException e) {
            System.out.println("Error during insert operation: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
