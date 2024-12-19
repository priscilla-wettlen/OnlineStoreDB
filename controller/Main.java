package controller;

import model.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main{
    public static void main(String[] args) throws SQLException {
        System.out.println("Starting the application...");
        DBConnection db = new DBConnection();
        Connection conn = db.testConnection();
        db.insertCustomer(conn, "customer", 2, "Priscilla", "Wettlen", "priscilla@email.com", "Huvudgatan", "Malmö","Sverige","666","abc123" );
        System.out.println("Application finished.");
    }

}