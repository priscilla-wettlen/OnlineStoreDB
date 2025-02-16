package program;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public void updateProductAmount(int productCode, int newAmount) {
        String checkProductQuery = "SELECT 1 FROM product WHERE p_code = ?";
        String updateAmountQuery = "UPDATE product SET p_amount = ? WHERE p_code = ?";

        try (PreparedStatement checkProd = conn.prepareStatement(checkProductQuery)) {
            checkProd.setInt(1, productCode);
            try (ResultSet rs = checkProd.executeQuery()) {
                if (rs.next()) {
                    try (PreparedStatement ps = conn.prepareStatement(updateAmountQuery)) {
                        ps.setInt(1, newAmount);
                        ps.setInt(2, productCode);

                        int rowsAffected = ps.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Updated product amount successfully. New amount: " + newAmount);
                        } else {
                            System.out.println("Error: Could not update product amount.");
                        }
                    }
                } else {
                    System.out.println("Error: Product with code " + productCode + " does not exist.");
                }
            }
        } catch (SQLException e) {
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

                if (!rs.isBeforeFirst()) {
                    System.out.println("No product found with code: " + code);
                    System.out.println();
                    return;
                }


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

                if (!rs.isBeforeFirst()) {
                    System.out.println("No product found with name: " + productName);
                    System.out.println();
                    return;
                }

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

    public void findProductByPrice(double productPrice) {
        String pNameQuery = "SELECT * FROM product WHERE p_price = ?";

        try (PreparedStatement ps = conn.prepareStatement(pNameQuery)) {
            ps.setDouble(1, productPrice);
            try (ResultSet rs = ps.executeQuery()) {

                if (!rs.isBeforeFirst()) {
                    System.out.println("No product found with price: " + productPrice);
                    System.out.println();
                    return;
                }


                System.out.printf("%-10s %-20s %-20s %-10s %-20s%n", "Code", "Product", "Available Units", "Price", "Supplier");
                System.out.println("-------------------------------------------------------------"
                        + "--------------------------------------------------------");

                while (rs.next()) {
                    int code = rs.getInt("p_code");
                    String name = rs.getString("p_name");
                    int amount = rs.getInt("p_amount");
                    productPrice = rs.getDouble("p_price");
                    int supplier = rs.getInt("p_supplier");

                    System.out.printf("%-10d %-20s %-20d %-10.2f %-20d%n", code, name, amount, productPrice, supplier);
                }
            }
        } catch (SQLException e) {
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

                if (!rs.isBeforeFirst()) {
                    System.out.println("No product found with supplier code: " + supplierCode);
                    System.out.println();
                    return;
                }

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

    public void addNewDiscount(String discountCode, double discountAmount, int category_id, String startDate, String endDate, int productCode) {
        String query = "INSERT INTO discount (d_discount_code, d_amount, d_category_id, d_date_start, d_date_end, d_product_code) VALUES (?, ?, ?, ?, ?, ?)";
        String checkProductQuery = "SELECT p_price FROM product WHERE p_code = ?";
        String updatePriceQuery = "UPDATE product SET p_price = ? WHERE p_code = ?";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date start = sdf.parse(startDate);
            Date end = sdf.parse(endDate);
            Date today = new Date();

            try (PreparedStatement checkProd = conn.prepareStatement(checkProductQuery)) {
                checkProd.setInt(1, productCode);
                try (ResultSet rs = checkProd.executeQuery()) {
                    if (rs.next()) {
                        double currentPrice = rs.getDouble("p_price");

                        try (PreparedStatement ps = conn.prepareStatement(query)) {
                            ps.setString(1, discountCode);
                            ps.setDouble(2, discountAmount);
                            ps.setInt(3, category_id);
                            ps.setDate(4, new java.sql.Date(start.getTime()));
                            ps.setDate(5, new java.sql.Date(end.getTime()));
                            ps.setInt(6, productCode);

                            int rowsAffected = ps.executeUpdate();
                            System.out.println("Inserted " + rowsAffected + " row(s) into discount successfully.");
                        }

                        if (!today.before(start) && !today.after(end)) {
                            double newPrice = currentPrice * (1 - (discountAmount / 100));

                            try (PreparedStatement updatePrice = conn.prepareStatement(updatePriceQuery)) {
                                updatePrice.setDouble(1, newPrice);
                                updatePrice.setInt(2, productCode);
                                updatePrice.executeUpdate();
                                System.out.println("Updated product price for product code " + productCode + " to " + newPrice);
                            }
                        } else {
                            System.out.println("Discount for product code " + productCode + " is not yet active.");
                        }
                    } else {
                        System.out.println("Product with code " + productCode + " does not exist.");
                    }
                }
            }
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showDiscountCategoryTable() {
        String query = "Select * FROM discount_category";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

            System.out.printf("%-10s %-30s%n", "ID", "Category name");
            System.out.println( "--------------------------------------------------------");

            while (resultSet.next()) {
                int id = resultSet.getInt("dc_category_id");
                String categoryName = resultSet.getString("dc_category_name");

                System.out.printf("%-10d %-30s%n", id, categoryName);
            }
        } catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
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

    public void showDiscountedProducts() {
        String query = "SELECT p.p_code, p.p_name, p.p_price, d.d_discount_code, d.d_amount, d.d_date_start, d.d_date_end " +
                "FROM product p " +
                "JOIN discount d ON p.p_code = d.d_product_code " +
                "WHERE CURRENT_DATE BETWEEN d.d_date_start AND d.d_date_end";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            System.out.printf("%-10s %-20s %-10s %-15s %-10s %-12s %-12s%n",
                    "Code", "Name", "Price", "Discount Code", "Amount", "Start Date", "End Date");
            System.out.println("---------------------------------------------------------------------------------------");

            while (rs.next()) {
                int code = rs.getInt("p_code");
                String name = rs.getString("p_name");
                double price = rs.getDouble("p_price");
                String discountCode = rs.getString("d_discount_code");
                double discountAmount = rs.getDouble("d_amount");
                Date startDate = rs.getDate("d_date_start");
                Date endDate = rs.getDate("d_date_end");

                System.out.printf("%-10d %-20s %-10.2f %-15s %-10.2f %-12s %-12s%n",
                        code, name, price, discountCode, discountAmount, startDate, endDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void viewCurrentShipment(int currID){
        String query = "SELECT p_name, si_amount, p_price FROM shipment_item INNER JOIN product ON product.p_code = shipment_item.si_product WHERE si_shipmentid = " + currID;

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

            System.out.printf("%-10s %-20s %-30s%n", "Product", "Ordered units", "Price");
            System.out.println("------------------------------------------------------------");
            
            double totalPrice = 0;

            while (resultSet.next()) {
                String name = resultSet.getString("p_name");
                int amount = resultSet.getInt("si_amount");
                double price = resultSet.getDouble("p_price");

                totalPrice = totalPrice + (amount * price);

                System.out.printf("%-10s %-20s %-30s%n", name, amount, price);
                System.out.println();
            }

            System.out.println("Total price of order is: " + totalPrice);

        } catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int nextShipmentID()
    {
        int temp = 1;

        String query = "SELECT MAX(s_id) FROM shipment";

        try (PreparedStatement ps = conn.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery()) {
            resultSet.next();
            temp = resultSet.getInt("max");
            temp++;

            //System.out.println(temp);
            /* 
            while (resultSet.next()) {
                int test = resultSet.getInt("s_id");
                if(test >= temp)
                {
                    temp = test;
                    temp++;
                }
            }
            */

        } catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }

        return temp;
    }

    public boolean validateProductStock(String name, int amountNeeded)
    {
        String query = "SELECT p_amount FROM product WHERE(p_name = '" + name + "')";
        try (PreparedStatement ps = conn.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery()) {
            
            resultSet.next();
            int amount = resultSet.getInt("p_amount");
            if(amount >= amountNeeded)
            {
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean createShipment(Customer customer)
    {
        String query = "INSERT INTO shipment (s_customer, s_confirmed) VALUES (" + customer.getUserID() + ", false)";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            int rowsAffected = preparedStatement.executeUpdate();
            return true;
            //System.out.println("Inserted " + rowsAffected + " row(s) into customer successfully.");

        } catch (SQLException e) {
            System.out.println("Error during insert operation: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public void addItemToShipment(int cartID, int id, int amount)
    {
        String query = "INSERT INTO shipment_item OVERRIDING SYSTEM VALUE VALUES (" + id + ", " + amount + ", " + cartID + ")";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println("Item added successfully to shipment.");

        } catch (SQLException e) {
            System.out.println("Error during insert operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeStock(int id, int amount)
    {
        String query = "UPDATE product SET p_amount = p_amount - " + amount + " WHERE p_code = " + id;

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            int rowsAffected = preparedStatement.executeUpdate();
            //System.out.println("Inserted " + rowsAffected + " row(s) into customer successfully.");

        } catch (SQLException e) {
            System.out.println("Error during insert operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int findProductID(String name)
    {
        String query = "SELECT p_code FROM product WHERE(p_name = '" + name + "')";
        try (PreparedStatement ps = conn.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery()) {
            
            resultSet.next();
            return resultSet.getInt("p_code");

        } catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }

    public void showAllShipments(Customer customer) {
        String query = "SELECT * FROM shipment WHERE s_customer = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, customer.userID);

            try (ResultSet resultSet = ps.executeQuery()) {

                if (!resultSet.isBeforeFirst()) {
                    System.out.println("No previous shipments found.");
                    return;
                }

                while (resultSet.next()) {
                    System.out.println();
                    System.out.println("Order ID: " + resultSet.getInt("s_id") + " Order Confirmed: " + resultSet.getBoolean("s_confirmed"));

                    viewCurrentShipment(resultSet.getInt("s_id"));

                    System.out.println();
                }

            }
        } catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public boolean deleteShipment(int shipmentToDelete) {
        String checkShipmentQuery = "SELECT s_confirmed FROM shipment WHERE s_id = ?";
        String selectItemsQuery = "SELECT si_product, si_amount FROM shipment_item WHERE si_shipmentid = ?";
        String deleteItemQuery = "DELETE FROM shipment_item WHERE si_shipmentid = ? AND si_product = ?";
        String deleteShipmentQuery = "DELETE FROM shipment WHERE s_id = ?";

        try (PreparedStatement checkShipment = conn.prepareStatement(checkShipmentQuery)) {
            checkShipment.setInt(1, shipmentToDelete);
            try (ResultSet rs = checkShipment.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Error: Shipment not found.");
                    return false;
                }
                if (rs.getBoolean("s_confirmed")) {
                    System.out.println("ERROR: Cannot delete order because it has already been confirmed by store admin.");
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking shipment status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

        try {
            conn.setAutoCommit(false);
            try (PreparedStatement selectItems = conn.prepareStatement(selectItemsQuery)) {
                selectItems.setInt(1, shipmentToDelete);
                try (ResultSet rs2 = selectItems.executeQuery()) {
                    while (rs2.next()) {
                        int productId = rs2.getInt("si_product");
                        int amount = rs2.getInt("si_amount");

                        removeStock(productId, -amount);
                        try (PreparedStatement deleteItem = conn.prepareStatement(deleteItemQuery)) {
                            deleteItem.setInt(1, shipmentToDelete);
                            deleteItem.setInt(2, productId);
                            deleteItem.executeUpdate();
                        }
                    }
                }
            }

            try (PreparedStatement deleteShipment = conn.prepareStatement(deleteShipmentQuery)) {
                deleteShipment.setInt(1, shipmentToDelete);
                int rowsAffected = deleteShipment.executeUpdate();
                if (rowsAffected > 0) {
                    conn.commit();
                    conn.setAutoCommit(true);
                    System.out.println("Shipment deleted successfully.");
                    return true;
                }
            }

            conn.rollback();
        } catch (SQLException e) {
            System.out.println("Error during delete operation: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }



    public void viewOrdersToBeConfirmed() {
        String query = "SELECT * FROM shipment WHERE s_confirmed = false";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet resultSet = ps.executeQuery()) {

                if (!resultSet.isBeforeFirst()) {
                    System.out.println("No orders needing confirmation found.");
                    return;
                }

                while (resultSet.next()) {
                    System.out.println();
                    System.out.println("Order ID: " + resultSet.getInt("s_id") + " Order Confirmed: " + resultSet.getBoolean("s_confirmed"));
                    System.out.println();
                }

        } catch (SQLException e) {
            System.out.println("Error during select operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void confirmShipment(int shipmentID) {
        String query = "UPDATE shipment SET s_confirmed = TRUE WHERE s_id = ?";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, shipmentID);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Shipment with ID " + shipmentID + " has been confirmed.");
            } else {
                System.out.println("No shipment found with ID " + shipmentID + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error updating shipment confirmation: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
