package view;

import javax.swing.*;
import java.awt.*;
import controller.Controller;

public class MainFrame extends JFrame {
    private int width;
    private int height;
    private Controller controller;
    private MainPanel mainPanel;



    public MainFrame(int width, int height, Controller controller) {
        this.controller = controller;
        this.mainPanel = new MainPanel(controller, width, height);
        setUpFrame();
    }

    public void setUpFrame() {
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

//        customerTable = new CustomerTable(controller, width, height);
//        setContentPane(customerTable);
//        setResizable(false);
//        pack();
//        setVisible(true);
    }



}
