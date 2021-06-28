package com.bridgelabz.selenium.test;

import java.sql.*;

public class SimpleDbTest {
    public static void  main(String[] args) throws  ClassNotFoundException, SQLException {
            //Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
            String dbUrl = "jdbc:mysql://localhost:3306/uploadcsv";

            //Database Username & Password
            String username = "root";
            String password = "root";

            //Query to Execute
            String query = "select *  from discounts;";

            //Load mysql jdbc driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Create Connection to DB
            Connection con = DriverManager.getConnection(dbUrl,username,password);

            //Create Statement Object
            Statement stmt = con.createStatement();

            // Execute the SQL Query. Store results in ResultSet
            ResultSet rs= stmt.executeQuery(query);

            // While Loop to iterate through all data and print results
            while (rs.next()){
                String myName = rs.getString(1);
                String myAge = rs.getString(2);
                System. out.println(myName+"  "+myAge);
            }
            // closing DB Connection
            con.close();
        }
}


