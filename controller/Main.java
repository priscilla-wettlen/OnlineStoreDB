package controller;

import model.DBConnection;
import view.MainFrame;

import java.sql.Connection;
import java.sql.SQLException;

public class Main{
    public static void main(String[] args) throws SQLException {
        System.out.println("Starting the application...");
        DBConnection db = new DBConnection();
        Connection conn = db.createConnection();
        //db.insertCustomer(conn, "customer", 2, "Priscilla", "Wettlen", "priscilla@email.com", "Huvudgatan", "Malmö","Sverige","666","abc123" );
        db.readProduct(conn);

        Controller controller = new Controller();
        System.out.println("Application finished.");
    }

}