package program;

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
        String query = "SELECT * FROM store_admin WHERE a_email = ? AND a_password = ?";
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

    public void addNewSupplier(String name, String address, String city, String phonenumber){
        String query = "INSERT INTO supplier" + " (s_name, s_address, s_city, s_phonenumber) " + "VALUES (?, ?, ?, ?)";

        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, city);
            ps.setString(4, phonenumber);

            int rowsAffected = ps.executeUpdate();
            System.out.println("Inserted " + rowsAffected + " row(s) into supplier successfully.");

        }catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void viewListOfSuppliers() {
        String query = "SELECT * FROM supplier";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

            System.out.printf("%-10s %-20s %-30s %-20s %-15s%n", "Code", "Name", "Address", "City", "Phone Number");
            System.out.println("--------------------------------------------------------------------------------------");

            while (resultSet.next()) {
                int code = resultSet.getInt("s_code");
                String name = resultSet.getString("s_name");
                String address = resultSet.getString("s_address");
                String city = resultSet.getString("s_city");
                String phoneNumber = resultSet.getString("s_phonenumber");

                System.out.printf("%-10d %-20s %-30s %-20s %-15s%n", code, name, address, city, phoneNumber);
            }

        } catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addNewProduct(String name, int amount, double price, int supplier ){
        String query = "INSERT INTO product" + " (p_name, p_amount, p_price, p_supplier) " + "VALUES (?, ?, ?, ?)";
        String checkSupplierQuery = "SELECT 1 FROM supplier WHERE s_code = ?";

        try (PreparedStatement checkSup = conn.prepareStatement(checkSupplierQuery)) {
            checkSup.setInt(1, supplier);
            try (ResultSet rs = checkSup.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement insertStmt = conn.prepareStatement(query)) {
                        insertStmt.setString(1, name);
                        insertStmt.setInt(2, amount);
                        insertStmt.setDouble(3, price);
                        insertStmt.setInt(4, supplier);

                        int rowsAffected = insertStmt.executeUpdate();
                        System.out.println("Inserted " + rowsAffected + " row(s) into product successfully.");
                    }
                } else {
                    System.out.println("Error: Supplier with s_code " + supplier + " does not exist.");
                }
            }

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

    public void viewStoreCatalogue(){
        String query = "SELECT * FROM product";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

            System.out.printf("%-10s %-20s %-30s%n", "Product", "Available units", "Price");
            System.out.println("------------------------------------------------------------");

            while (resultSet.next()) {
                String name = resultSet.getString("p_name");
                int amount = resultSet.getInt("p_amount");
                double price = resultSet.getDouble("p_price");

                System.out.printf("%-10s %-20s %-30s%n", name, amount, price);
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
