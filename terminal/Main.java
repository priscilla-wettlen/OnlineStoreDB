package terminal;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import terminal.DBConnection;

public class Main {
    static Scanner input = new Scanner(System.in);
    static DBConnection db = new DBConnection();


    public static void insertCustomer(int id, String firstName, String lastName,
                               String email, String address, String city, String country, String phoneNumber, String password) {
        String query = "INSERT INTO customer" + " (c_id, c_first_name, c_last_name, c_email, c_address, c_city, c_country, c_phonenumber, c_password) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = db.createConnection();

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
            System.out.println("Inserted " + rowsAffected + " row(s) into customer successfully.");

        } catch (SQLException e) {
            System.out.println("Error during insert operation: " + e.getMessage());
            e.printStackTrace();
        }
    }

//    public ArrayList<Product> readProduct(Connection connection) {
//        String query = "SELECT * FROM product";
//        ArrayList<Product> products = new ArrayList<Product>();
//
//        try{
//            Statement statement = connection.createStatement();
//            ResultSet rs = statement.executeQuery(query);
//
//            while(rs.next()){
//                products.add(
//                        new Product(rs.getInt("p_code"),
//                                rs.getString("p_name"),
//                                rs.getInt("p_amount"),
//                                rs.getInt("p_price"),
//                                rs.getInt("p_supplier"))
//                );
//            }
//
//            rs.close();
//            statement.close();
//        }catch (SQLException e){
//            e.printStackTrace();
//            e.getMessage();
//            return products;
//        }
//
//        return products;
//
//    }

    public static int readMenuChoice() {
        int option = -1;
        boolean isValidInput = false;

        while (!isValidInput) {
            System.out.println("What would you like to do?");

            if (!input.hasNextInt()) {
                System.out.println("WHOOPS! Please choose ONLY a valid number from the options above.");
                input.next();
                continue;
            }

            option = input.nextInt();

            if (option >= 0 && option <= 8) {
                isValidInput = true;
            } else {
                System.out.println("WHOOPS! Please choose ONLY a valid number from the options above.");
            }

        }

        return option;

    }

    public static void PrintLoginMenu(){
        System.out.println("Welcome to the Electronics Store! " +
                "What would you like to do?\n: ");
        System.out.println("1. I'm a returning customer and want to login.");
        System.out.println("2. I don't have an account and I want to register.");
        System.out.println("3. I just want to browse your catalogue.");
        System.out.println("4. I want to login as an admin");
        System.out.println("5. I changed my mind. Hejdå!");
    }

    public static void printAdminMenu(){
        System.out.println("Welcome Admin! "  +
                "What would you like to do?\n: ");
        System.out.println("1. Something");
        System.out.println("2. Something else");
        System.out.println("3. Exit");
    }

    public static void adminActionsLoop(){
        boolean isOn = false;
        do{
            printAdminMenu();
            int option = readMenuChoice();
            switch (option) {
                case 1:
                    System.out.println("This is something");
                    isOn = true;
                    break;
                case 2:
                    System.out.println("This is something else");
                    isOn = true;
                    break;
                case 3:
                    System.out.println("You have existed admin menu. You will now be redirected to main menu.");
                    mainMenuLoop();
                    break;
            }
        }while(isOn);
    }

    public static void mainMenuLoop(){
        boolean isOn = false;
        do{
            PrintLoginMenu();

            int option = readMenuChoice();

            switch(option){
                case 1:
                    System.out.println("Enter your email address: ");
                    String email = input.next();
                    System.out.println("Enter your password: ");
                    String password = input.next();
                    isOn = true;
                    break;
                case 2:
                    //TODO Fix the spacing. It needs to be able to take spaces. Same problem i had with U1
                    System.out.println("Enter your first name: ");
                    String firstName =  input.next();
                    System.out.println("Enter your last name: ");
                    String lastName = input.next();
                    System.out.println("Enter your email address: ");
                    String emailAddress = input.next();
                    System.out.println("Enter our address:");
                    String address = input.next();
                    System.out.println("Enter your city: ");
                    String city = input.next();
                    System.out.println("Enter your country: ");
                    String country = input.next();
                    System.out.println("Enter your phone number: ");
                    String phoneNbr = input.next();
                    System.out.println("Choose a password: ");
                    String newPassword = input.next();
                    insertCustomer(3, firstName, lastName, emailAddress, address, city, country, phoneNbr, newPassword);
                    isOn = true;
                    break;
                case 4:
                    System.out.println("Enter your admin email address: ");
                    String adminEmail = input.next();
                    System.out.println("Enter your password: ");
                    String adminPassword = input.next();
                    adminActionsLoop();
                    isOn = false;
                    break;
                case 5:
                    System.out.println("Hejdå!");
                    isOn = false;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }while(isOn);
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("Starting the application...");
        DBConnection db = new DBConnection();
        Connection conn = db.createConnection();
        mainMenuLoop();
        System.out.println("Application finished.");
    }

}