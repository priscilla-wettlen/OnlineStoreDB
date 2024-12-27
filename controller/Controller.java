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
        panel = new MainPanel(this, 1000, 500);
        frame = new MainFrame(1000, 500, this, panel);

        dbConnection = new DBConnection();
        conn = dbConnection.createConnection();

        frame.setUpFrame(dbConnection.readProduct(this.conn));
        displayProductTable();
    }

    public void displayProductTable(){
        // Redundant?
        ArrayList<Product> products = dbConnection.readProduct(this.conn);
        for(Product product : products){
            System.out.println(product.getName());
        }

        panel.setupProductTable(products);

    }

}
