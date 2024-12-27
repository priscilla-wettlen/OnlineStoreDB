package view;
import controller.Controller;
import model.Customer;
import model.Product;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private Controller controller;
    private int width;
    private int height;
    private JTable productTable;

    //Customer registration
    private JLabel lblFirstName;
    private JLabel lblLastName;
    private JLabel lblEmail;
    private JLabel lblAddress;
    private JLabel lblCity;
    private JLabel lblCountry;
    private JLabel lblPhoneNbr;
    private JLabel lblPassword;

    private JTextField firstName;
    private JTextField lastName;
    private JTextField email;
    private JTextField address;
    private JTextField city;
    private JTextField country;
    private JTextField phoneNbr;
    private JTextField password;


    public MainPanel(Controller controller, int width, int height)
    {
        this.controller = controller;
        this.width = width;
        this.height = height;
        setLayout(new BorderLayout());

        //setupPanel();
    }

//    public JPanel setupNewCustomerPanel(Customer customer){
//
//    }

    public JTable setupProductTable(ArrayList<Product> products){
        String[] columns = {"p_code", "p_name", "p_amount", "p_price", "p_supplier"};

        //add(scrollPane, BorderLayout.CENTER);

        String[][] productData = new String[products.size()][columns.length];

        for(int i = 0; i < products.size(); i++){
            productData[i] = products.get(i).toStringArray();
        }

        productTable = new JTable(productData, columns);
        productTable.setBounds(30, 40,200,300);
        return productTable;
    }

//    private void setupPanel() {
//        setLayout(new GridLayout(2, 2, 10, 10));
//
//        JLabel lblFirstName = new JLabel("First Name:");
//        txtFirstName = new JTextField(15);
//        JLabel lblLastName = new JLabel("Last Name:");
//        txtLastName = new JTextField(15);
//
//
//        add(lblFirstName);
//        add(txtFirstName);
//        add(lblLastName);
//        add(txtLastName);
//    }
}
