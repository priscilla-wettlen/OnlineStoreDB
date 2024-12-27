package controller;

import model.*;
import org.postgresql.core.BaseConnection;
import view.*;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

public class Controller {
    MainFrame frame;
    MainPanel panel;
    DBConnection dbConnection;
    Connection conn;

    String firstName;

    public Controller(){
        frame = new MainFrame(1000, 500, this);
        panel = new MainPanel(this, 1000, 500);
        dbConnection = new DBConnection();
        conn = dbConnection.createConnection();

        displayProductTable();
    }

    public void displayProductTable(){
        ArrayList<Product> products = dbConnection.readProduct(this.conn);
        for(Product product : products){
            System.out.println(product.getName());
        }

    }

//    public void setFirstName(String firstName){
//        frame.setFirstNameText(this.firstName);
//    }

}
