package program;
import java.util.Scanner;

public class CustomerMenu {
    static Scanner input = new Scanner(System.in);
    Customer customer;
    DBConnection conn;

    public CustomerMenu(Customer customer, DBConnection conn) {
        this.customer = customer;
        this.conn = conn;
    }

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
                    System.out.println("Not implemented yet!");
                    break;
                case 3:
                    System.out.println("Not implemented yet!");
                    break;
                case 4:
                    System.out.println("Not implemented yet!");
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
