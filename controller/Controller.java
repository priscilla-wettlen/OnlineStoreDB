package controller;

import java.sql.*;

public class Controller{

    String url = "jdbc:postgresql://pgserver.mau.se/";
    String name = "xx1234";
    String password = "PASSWORD"; // System.getenv("PSQLPWD");
    try (Connection conn = DriverManager.getConnection(url, name, password);
    Statement stmt = conn.createStatement()) {
        ResultSet rs = stmt.executeQuery("SELECT * FROM department");
        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }
    }
