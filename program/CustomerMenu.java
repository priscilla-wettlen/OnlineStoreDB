package program;
import java.util.Scanner;

import static program.Main.mainMenuLoop;

public class CustomerMenu {
    static Scanner input = new Scanner(System.in);
    Customer customer;
    DBConnection conn;
    int cartID = 0;
    boolean cartIsCreated = false;

    public CustomerMenu(Customer customer, DBConnection conn) {
        this.customer = customer;
        this.conn = conn;

        cartID = conn.nextShipmentID();
    }

    /*
    shipment
    customer id, int
    shipment id, int
    confirmed, bool <-- always false here
    
    item
    product id, int
    amount, int
    shipment id, int
    */

    public void mainCustomerLoop()
    {
        boolean isOn = true;
        printWelcomeMessage();
        while(isOn)
        {
            printCustomerMenu();

            int option = readMenuChoice();

            switch(option){
                case 1:
                    System.out.println("Find: ");
                    System.out.println("1. All products");
                    System.out.println("2. Only discounted products");
                    System.out.println("3. Product by name");
                    System.out.println("4. Product by code");
                    System.out.println("5. Product by price");
                    System.out.println("6. Product by supplier");
                    System.out.println("7. Back to customer menu");
                    int choice = readMenuChoice();
                    switch (choice) {
                        case 1:
                            conn.viewStoreCatalogue();
                            mainCustomerLoop();
                            break;
                        case 2:
                            conn.showDiscountedProducts();
                            mainCustomerLoop();
                            break;
                        case 3:
                            System.out.println("Enter product name:");
                            String searchProductName = input.next();
                            conn.findProductByName(searchProductName);
                            mainCustomerLoop();
                            break;
                        case 4:
                            System.out.println("Enter product code:");
                            int searchProductCode = input.nextInt();
                            conn.findProductByCode(searchProductCode);
                            mainCustomerLoop();
                            break;
                        case 5:
                            System.out.println("Enter the price in this format: 0,00:");
                            double searchPrice = input.nextDouble();
                            conn.findProductByPrice(searchPrice);
                            mainCustomerLoop();
                            break;
                        case 6:
                            System.out.println("Enter supplier's code:");
                            int searchSupplierCode = input.nextInt();
                            conn.findProductBySupplier(searchSupplierCode);
                            mainCustomerLoop();
                            break;
                        case 7:
                            mainCustomerLoop();
                            break;
                    }
                    break;
                case 2:
                    conn.viewStoreCatalogue();
                    addItemToCart();
                    break;
                case 3:
                    if(cartIsCreated)
                    {
                        conn.viewCurrentShipment(cartID);
                    }
                    else
                    {
                        System.out.println("Cart is empty!");
                    }
                    break;
                case 4:
                    conn.viewCurrentShipment(cartID);
                    System.out.println();
                    System.out.println("Accept order?");
                    System.out.println("1. Yes, it looks good");
                    System.out.println("2. No, I'd like to make changes");
                    System.out.println();
                    int orderChoice = readMenuChoice();
                    if(orderChoice == 1)
                        System.out.println("Your order " + cartID + " has been placed!");
                    else if(orderChoice == 2)
                        mainCustomerLoop();
                    break;
                case 5:
                    conn.showAllShipments(customer);
                    break;
                case 6:
                    conn.showAllShipments(customer);
                    System.out.println("Enter which shipment you would like to cancel");
                    int shipment = input.nextInt();
                    conn.deleteShipment(shipment);
                    break; 
                case 7:
                    System.out.println("You have exited the customer menu. You will now be redirected to main menu.");
                    System.out.println();
                    mainMenuLoop();
                    isOn = false;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    public void printWelcomeMessage()
    {
        System.out.println("Welcome " + customer.getfName());
    }

    public void printCustomerMenu(){
        System.out.println("What would you like to do?:\n ");
        System.out.println("1. I want to browse your catalogue.");
        System.out.println("2. I want to add a product to my cart");
        System.out.println("3. I want to see my cart");
        System.out.println("4. I want to place an order");
        System.out.println("5. I want to see my orders");
        System.out.println("6. I want to cancel a order");
        System.out.println("7. I want to to go back");
        
    }

    public void addItemToCart()
    {
        //write methods in conn

        System.out.println("Enter product name (case sensitive)");
        String name = input.next();
        System.out.println("Enter how many you would like");
        int amount = input.nextInt();

//        //check stock
//        if(conn.validateProductStock(name, amount))
//        {
//            //check if order exists
//            if(!cartIsCreated)
//            {
//                cartIsCreated = conn.createShipment(customer);
//            }
//
//            if(cartIsCreated)
//            {
//                int id = conn.findProductID(name);
//
//                //add item to order
//                conn.addItemToShipment(cartID, id, amount);
//
//                //remove amount from stock
//                conn.removeStock(id, amount);
//
//                //print something
//                System.out.println("Item added to your cart!");
//            }
//        }
//        else
//        {
//            System.out.println("Not enough in stock!");
//        }
        //check stock

        if (conn.validateProductStock(name, amount)) {
            // If no shipment exists, create one and store the shipment ID
            if (!cartIsCreated) {
                int newCartID = conn.createShipment(customer);
                if (newCartID == -1) {
                    System.out.println("Error: Could not create shipment.");
                    return;
                }
                cartID = newCartID;
                cartIsCreated = true;
            }

            if (cartIsCreated) {
                int productID = conn.findProductID(name);

                // Add item to shipment using correct shipment ID
                conn.addItemToShipment(cartID, productID, amount);

                // Remove stock
                conn.removeStock(productID, amount);

                System.out.println("Item added to your cart!");
            }
        } else {
            System.out.println("Not enough in stock!");
        }

    }

    public int readMenuChoice() {
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

            if (option >= 0 && option <= 9) {
                isValidInput = true;
            } else {
                System.out.println("WHOOPS! Please choose ONLY a valid number from the options above.");
            }

        }

        return option;

    }
}
