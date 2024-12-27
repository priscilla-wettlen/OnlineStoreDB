package controller;

import model.*;
import view.*;
import javax.swing.*;
import java.awt.*;

public class Controller {
    MainFrame frame;

    Customer customer;
    String firstName;

    public Controller(){
        frame = new MainFrame(1000, 500, this);
    }

//    public void setFirstName(String firstName){
//        frame.setFirstNameText(this.firstName);
//    }

}
