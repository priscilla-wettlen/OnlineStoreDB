package view;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;

public class MainPanel extends JPanel {
    private Controller controller;
    private int width;
    private int height;

    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JLabel lblFirstName;
    private JLabel lblLastName;

    public MainPanel(Controller controller, int width, int height)
    {
        this.controller = controller;
        this.width = width;
        this.height = height;
        setupPanel();
    }

    private void setupPanel() {
        // Set a simple layout for demonstration purposes
        setLayout(new GridLayout(2, 2, 10, 10));

        // Create components
        JLabel lblFirstName = new JLabel("First Name:");
        txtFirstName = new JTextField(15);
        JLabel lblLastName = new JLabel("Last Name:");
        txtLastName = new JTextField(15);

        // Add components to the panel
        add(lblFirstName);
        add(txtFirstName);
        add(lblLastName);
        add(txtLastName);
    }
}
