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

        result.printAll();
        String[] test = result.getRow();

        for(int i = 0; i < test.length; i++)
        {
            System.out.println( test[i]);
        }
        //db.selectData(conn, )

        String[] test1 = new String[1];
        String[] test2 = new String[1];

        test1[0] = "p_amount";
        test2[0] = "60";
        String condition = "p_code = 4";

        db.alterData(conn, "product", test1, test2, condition);
        SQLResult test3 = db.selectData(conn, "product");
        test3.printAll();


        System.out.println("Application finished.");
    }

}