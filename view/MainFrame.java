package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import controller.Controller;
import model.Product;

public class MainFrame extends JFrame {
    private int width;
    private int height;
    private Controller controller;
    private MainPanel mainPanel;
    private JFrame frame = new JFrame();


    public MainFrame(int width, int height, Controller controller, MainPanel mainPanel) {
        this.controller = controller;
        this.mainPanel = mainPanel;
    }

    public void setUpFrame(ArrayList<Product> products) {
        final int offsetX = width/ 5;
        final int offsetY = height / 5;

        setSize(width, height);
        setTitle("Online Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(offsetX, offsetY);

        mainPanel = new MainPanel(controller, width, height);
        setContentPane(mainPanel);
        setResizable(false);
        pack();
        setVisible(true);

        JTable productTable = mainPanel.setupProductTable(products);
        JScrollPane scrollPane = new JScrollPane(productTable);
        frame.add(scrollPane);
        frame.setSize(500, 200);
        frame.setVisible(true);


    }



}
