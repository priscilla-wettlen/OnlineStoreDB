package program;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DBConnection {
    private static final String URL = "jdbc:postgresql://pgserver.mau.se/onlinestoreaj6817";
    private static final String USER = "aj6817";
    private static final String PASSWORD = "ywv0moz1";
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

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Disconnected from the database.");
            } catch (SQLException e) {
                System.err.println("Error while disconnecting: " + e.getMessage());
            }
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

    public void addNewProduct(String name, int amount, double price, int supplier){
        String query = "INSERT INTO product" + " (p_name, p_amount, p_price, p_supplier) " + "VALUES (?, ?, ?, ?)";
        String checkSupplierQuery = "SELECT 1 FROM supplier WHERE s_code = ?";

        try (PreparedStatement checkSup = conn.prepareStatement(checkSupplierQuery)) {
            checkSup.setInt(1, supplier);
            try (ResultSet rs = checkSup.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement ps = conn.prepareStatement(query)) {
                        ps.setString(1, name);
                        ps.setInt(2, amount);
                        ps.setDouble(3, price);
                        ps.setInt(4, supplier);

                        int rowsAffected = ps.executeUpdate();
                        System.out.println("Inserted " + rowsAffected + " row(s) into product successfully.");
                    }
                } else {
                    System.out.println("Error: Supplier with s_code " + supplier + " does not exist.");
                }
            }

        }catch (SQLException e) {
            System.out.println("Error during operation: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void viewProductList(){
        String query = "SELECT * FROM product";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

            System.out.printf("%-10s %-20s %-20s %-10s %-20s%n", "Code", "Product", "Available Units", "Price", "Supplier");
            System.out.println("-------------------------------------------------------------"
                    + "--------------------------------------------------------");

            while (resultSet.next()) {
                int code = resultSet.getInt("p_code");
                String name = resultSet.getString("p_name");
                int amount = resultSet.getInt("p_amount");
                double price = resultSet.getDouble("p_price");
                String supplier = resultSet.getString("p_supplier");

                System.out.printf("%-10d %-20s %-20d %-10.2f %-20s%n", code, name, amount, price, supplier);
            }
        } catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void findProductByCode(int code){
        String pCodeQuery = "SELECT * FROM product WHERE p_code = ?";

        try (PreparedStatement ps = conn.prepareStatement(pCodeQuery)) {
            ps.setInt(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.printf("%-10s %-20s %-20s %-10s %-20s%n", "Code", "Product", "Available Units", "Price", "Supplier");
                System.out.println("-------------------------------------------------------------"
                        + "--------------------------------------------------------");

                while (rs.next()) {
                    code = rs.getInt("p_code");
                    String name = rs.getString("p_name");
                    int amount = rs.getInt("p_amount");
                    double price = rs.getDouble("p_price");
                    int supplier = rs.getInt("p_supplier");

                    System.out.printf("%-10d %-20s %-20d %-10.2f %-20s%n", code, name, amount, price, supplier);
                }
            }



        }catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void findProductByName(String productName){
        String pNameQuery = "SELECT * FROM product WHERE p_name = ?";

        try (PreparedStatement ps = conn.prepareStatement(pNameQuery)) {
            ps.setString(1, productName);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.printf("%-10s %-20s %-20s %-10s %-20s%n", "Code", "Product", "Available Units", "Price", "Supplier");
                System.out.println("-------------------------------------------------------------"
                        + "--------------------------------------------------------");

                while (rs.next()) {
                    int code = rs.getInt("p_code");
                    productName = rs.getString("p_name");
                    int amount = rs.getInt("p_amount");
                    double price = rs.getDouble("p_price");
                    int supplier = rs.getInt("p_supplier");

                    System.out.printf("%-10d %-20s %-20d %-10.2f %-20s%n", code, productName, amount, price, supplier);
                }
            }



        }catch (SQLException e) {
            System.out.println("Error during operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void findProductBySupplier(int supplierCode){
        String getSupplierName = "SELECT pr.p_code, pr.p_name, pr.p_amount, pr.p_price, sup.s_name " +
                "FROM product pr " +
                "JOIN supplier sup ON pr.p_supplier = sup.s_code " +
                "WHERE sup.s_code = ?";

        try (PreparedStatement ps = conn.prepareStatement(getSupplierName)) {
            ps.setInt(1, supplierCode);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.printf("%-10s %-20s %-20s %-10s %-20s%n", "Code", "Product", "Available Units", "Price", "Supplier");
                System.out.println("-------------------------------------------------------------"
                        + "--------------------------------------------------------");
                while (rs.next()) {
                    int productCode = rs.getInt("p_code");
                    String productName = rs.getString("p_name");
                    int amount = rs.getInt("p_amount");
                    double productPrice = rs.getDouble("p_price");
                    String supplierName = rs.getString("s_name");

                    System.out.printf("%-10d %-20s %-20d %-10.2f %-20s%n", productCode, productName, amount, productPrice, supplierName);
                }
            }
        }catch (SQLException e) {
            System.out.println("Error during operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteProduct(int code){
        String query = "DELETE FROM product WHERE p_code = ?";
        String checkProductCode = "SELECT 1 FROM product WHERE p_code = ?";

        try (PreparedStatement checkCode = conn.prepareStatement(checkProductCode)) {
            checkCode.setInt(1, code);
            try (ResultSet rs = checkCode.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement ps = conn.prepareStatement(query)) {
                        ps.setInt(1, code);
                        int rowsAffected = ps.executeUpdate();
                        System.out.println("Deleted " + rowsAffected + " row from product successfully.");
                    }
                } else {
                    System.out.println("Error: Product with p_code " + code + " does not exist.");
                }
            }

        }catch(SQLException e){
            System.out.println("Error during operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void addNewDiscount(String discountCode, double discountAmount, String startDate, String endDate, int productCode) {
        String query = "INSERT INTO discount (d_discount_code, d_amount, d_date_start, d_date_end, d_product_code) VALUES (?, ?, ?, ?, ?)";
        String checkProductQuery = "SELECT 1 FROM product WHERE p_code = ?";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try (PreparedStatement checkProd = conn.prepareStatement(checkProductQuery)) {
            checkProd.setInt(1, productCode);
            try (ResultSet rs = checkProd.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement ps = conn.prepareStatement(query)) {
                        ps.setString(1, discountCode);
                        ps.setDouble(2, discountAmount);
                        ps.setDate(3, new Date(sdf.parse(startDate).getTime()));
                        ps.setDate(4, new Date(sdf.parse(endDate).getTime()));
                        ps.setInt(5, productCode);

                        int rowsAffected = ps.executeUpdate();
                        System.out.println("Inserted " + rowsAffected + " row(s) into discount successfully.");
                    }
                } else {
                    System.out.println("Product with code " + productCode + " does not exist.");
                }
            }
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-----CUSTOMER ACTIONS-----//
    public boolean validateCustomer(String email, String password, Customer customer) {
        String query = "SELECT * FROM customer WHERE c_email = ? AND c_password = ?";
        boolean isValid = false;

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next())
                {
                    isValid = true;
                    customer.setUserID(rs.getInt("c_id"));
                    customer.setfName(rs.getString("c_first_name"));
                    customer.setlName(rs.getString("c_last_name"));
                }
            }

        }catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }
        return isValid;
    }

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
