package com.bridgelabz.selenium.test;

import com.bridgelabz.selenium.base.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbTesting extends Base {

    public static int count;

    @Test(priority = 0)
    public void get_table_data() throws ClassNotFoundException, SQLException {
        con = this.getConnection();

        //Creating Query to the Database using the Statement Object.
        Statement smt = con.createStatement();

        //Query Executed
        ResultSet rs = smt.executeQuery("select * from user");
        System.out.println("/*************Records*************/");
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String userType = rs.getString(3);
            System.out.println(id + " " + name + " " + userType);
            count++;
        }
    }

    @Test(priority = 1)
    public void insert_values_into_table() throws ClassNotFoundException, SQLException {
        con = this.getConnection();
        PreparedStatement pst=con.prepareStatement("insert into user values(?,?,?)");

        pst.setInt(1,count+1);
        pst.setString(2,"Dianne");
        pst.setString(3,"prime");
        pst.executeUpdate();
        get_table_data();
    }

    @Test(priority = 2)
    public void update_values_into_table() throws ClassNotFoundException, SQLException {
        con = this.getConnection();
        PreparedStatement pst=con.prepareStatement("UPDATE user SET user_name = ? WHERE user_id = ?");

        pst.setString(1,"Test_BL");
        pst.setInt(2,3);
        pst.executeUpdate();
        get_table_data();
    }

    @Test(priority = 3)
    public void delete_row_from_table() throws ClassNotFoundException, SQLException {
        con = this.getConnection();
        PreparedStatement pst=con.prepareStatement("DELETE FROM user WHERE user_id = ?");
        pst.setInt(1,3);
        pst.executeUpdate();
        get_table_data();
    }

    @Test(priority = 4)
    public void db_Selenium() throws SQLException, InterruptedException, ClassNotFoundException {
        setUp();
        ResultSet rs;
        WebElement username = driver.findElement(By.xpath("//input[@class='_2IX_2- VJZDxU']"));
        WebElement password = driver.findElement(By.xpath("//input[@class='_2IX_2- _3mctLh VJZDxU']"));
        WebElement loginBtn = driver.findElement(By.xpath("//button[@class='_2KpZ6l _2HKlqd _3AWRsL']"));
        con = this.getConnection();
        Statement smt = con.createStatement();

        rs = smt.executeQuery("select * from user LIMIT 1");
        while (rs.next()) {
            username.sendKeys(rs.getString(2));
            Thread.sleep(200);
        }
        password.sendKeys("30Login@123");
        loginBtn.click();
        Thread.sleep(1000);
        driver.close();
    }
}


