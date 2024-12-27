package view;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainPanel extends JPanel {
    private Controller controller;
    private int width;
    private int height;
    private JTable productTable;
    private DefaultTableModel tableModel;

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JLabel lblFirstName;
    private JLabel lblLastName;

    public MainPanel(Controller controller, int width, int height)
    {
        this.controller = controller;
        this.width = width;
        this.height = height;
        setLayout(new BorderLayout());
        //setupTable();
        setupPanel();
    }

//    private void setupTable(){
//        String[] columns = {"p_code", "p_name", "p_amount", "p_price", "p_supplier"};
//
//        tableModel = new DefaultTableModel(columns, 0);
//        productTable = new JTable(tableModel);
//        JScrollPane scrollPane = new JScrollPane(productTable);
//        add(scrollPane, BorderLayout.CENTER);
//    }

    private void setupPanel() {
        setLayout(new GridLayout(2, 2, 10, 10));

        JLabel lblFirstName = new JLabel("First Name:");
        txtFirstName = new JTextField(15);
        JLabel lblLastName = new JLabel("Last Name:");
        txtLastName = new JTextField(15);


        add(lblFirstName);
        add(txtFirstName);
        add(lblLastName);
        add(txtLastName);
    }
}
