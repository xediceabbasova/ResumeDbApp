
package com.company.main;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void foo() throws Exception {

        Class.forName("com.mysql.jdbc.Driver");
        com.mysql.jdbc.Driver
        String url = "jdbc:mysql://localhost:3306";
        String username = "root";
        String password = "";
        Connection c = DriverManager.getConnection(url, username, password);

    }
}
