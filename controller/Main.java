package controller;

import model.DBConnection;
import model.SQLResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main{
    public static void main(String[] args) throws SQLException {
        System.out.println("Starting the application...");
        DBConnection db = new DBConnection();
        Connection conn = db.testConnection();
        //db.insertCustomer(conn, "customer", 2, "Priscilla", "Wettlen", "priscilla@email.com", "Huvudgatan", "Malmö","Sverige","666","abc123" );
        SQLResult result = db.selectData(conn, "customer");

        String[] test = new String[5];
        test[0] = "4";
        test[1] = "'ett tanbentbord'";
        test[2] = "100";
        test[3] = "400";
        test[4] = "1";

        db.insertData(conn,"product", test);


        System.out.println("Application finished.");
    }

}