package program;
import java.util.Scanner;

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
                    conn.viewStoreCatalogue();
                    break;
                case 2:
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

                    conn.showAllShipments(customer);
                    break;
                case 5:
                    System.out.println("Not implemented yet!");
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
        System.out.println("4. I want to see my orders");
        System.out.println("5. I want to cancel a order");
        /* 
        search by code,name,supplier,price
        see current discounts
        
        */
    }

    public void addItemToCart()
    {
        //write methods in conn

        System.out.println("Enter product name (case sensitive)");
        String name = input.next();
        System.out.println("Enter how many you would like");
        int amount = input.nextInt();

        //check stock
        if(conn.validateProductStock(name, amount))
        {
            //check if order exists
            if(!cartIsCreated)
            {
                cartIsCreated = conn.createShipment(customer);
            }

            if(cartIsCreated)
            {
                int id = conn.findProductID(name);

                //add item to order
                conn.addItemtoShipment(cartID, id, amount);

                //remove amount from stock
                conn.removeStock(id, amount);

                //print something
                System.out.println("Item added to your cart!");
            }
        }
        else
        {
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

            if (option >= 0 && option <= 8) {
                isValidInput = true;
            } else {
                System.out.println("WHOOPS! Please choose ONLY a valid number from the options above.");
            }

        }

        return option;

    }
}
