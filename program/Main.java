package program;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);
    static DBConnection conn = new DBConnection();
    static Customer currCustomer = new Customer("placeHolder", "placeHolder", -1);
    static CustomerMenu customerMenu;

    //sad@sds.com
    //asddsds

    //-----MAIN MENU-----//
    public static void PrintLoginMenu(){
        System.out.println("Welcome to the Electronics Store! " +
                "What would you like to do?:\n ");
        System.out.println("1. I'm a returning customer and want to login.");
        System.out.println("2. I don't have an account and I want to register.");
        System.out.println("3. I just want to browse your catalogue.");
        System.out.println("4. I want to login as an admin");
        System.out.println("5. I changed my mind. Hejdå!");
    }

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

    public static void mainMenuLoop(){
        boolean isOn = false;
        do{
            PrintLoginMenu();

            int option = readMenuChoice();

            switch(option){
                case 1:
                    boolean validCredentials = false;

                    while (!validCredentials) {
                        System.out.println("Enter your email address: ");
                        String email = input.next();
                     
                        System.out.println("Enter your password: ");
                        String password = input.next();
                     
                        if (conn.validateCustomer(email, password, currCustomer)) 
                        {

                            System.out.println("Login successful. Accessing customer view...");
                            validCredentials = true;
                            
                            //CUSTOMER MENU CALL HERE
                            isOn = false;
                        } 
                        else 
                        {
                            System.out.println("Invalid credentials. Please try again.");
                        }
                    }
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
                    conn.insertCustomer(firstName, lastName, emailAddress, address, city, country, phoneNbr, newPassword);
                    isOn = true;
                    break;
                case 3:
                    conn.viewStoreCatalogue();
                    isOn = true;
                    break;
                case 4:
//                    boolean validCredentials = false;
//
//                    while (!validCredentials) {
//                        System.out.println("Enter your admin email address: ");
//                        String adminEmail = input.next();
//
//                        System.out.println("Enter your password: ");
//                        String adminPassword = input.next();
//
//                        if (conn.validateAdmin(adminEmail, adminPassword)) {
//                            System.out.println("Login successful. Accessing admin actions...");
//                            validCredentials = true;
//                            adminActionsLoop();
//                            isOn = false;
//                        } else {
//                            System.out.println("Invalid credentials. Please try again.");
//                        }
//                    }
                    adminActionsLoop();
                    isOn = true;
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

    //-----ADMIN MENU-----//

    public static void printAdminMenu(){
        System.out.println("Welcome Admin! "  +
                "What would you like to do?:\n ");
        System.out.println("1. Add a new supplier");
        System.out.println("2. View list of suppliers");
        System.out.println("3. Add a new product");
        System.out.println("4. Delete a product");
        System.out.println("5. Add a new discount");
        System.out.println("6. Exit");
    }

    public static void adminActionsLoop(){
        boolean isOn = false;
        do{
            printAdminMenu();
            int option = readMenuChoice();
            switch (option) {
                case 1:
                    System.out.println("Enter supplier's name:");
                    String sName = input.next();
                    System.out.println("Enter supplier's address:");
                    String sAddress = input.next();
                    System.out.println("Enter supplier's city:");
                    String sCity = input.next();
                    System.out.println("Enter supplier's phone number:");
                    String sPhoneNumber = input.next();
                    conn.addNewSupplier(sName, sAddress, sCity, sPhoneNumber);
                    isOn = true;
                    break;
                case 2:
                    conn.viewListOfSuppliers();
                    isOn = true;
                    break;
                case 3:
                    System.out.println("Enter product name:");
                    String pName = input.next();
                    System.out.println("Enter amount:");
                    int pAmount = input.nextInt();
                    System.out.println("Enter price:");
                    double pPrice = input.nextDouble();
                    System.out.println("Enter supplier code:");
                    int pCode = input.nextInt();
                    conn.addNewProduct(pName,pAmount,pPrice, pCode);
                    isOn = true;
                    break;
                case 6:
                    System.out.println("You have existed the admin menu. You will now be redirected to main menu.");
                    System.out.println();
                    mainMenuLoop();
                    break;
            }
        }while(isOn);
    }



    public static void main(String[] args) throws SQLException {
        System.out.println("Starting the application...");
        mainMenuLoop();
        System.out.println("Application finished.");
    }

}