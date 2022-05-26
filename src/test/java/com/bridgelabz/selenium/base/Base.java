package com.bridgelabz.selenium.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Base {
    public static Connection con;
    public static WebDriver driver = null;

    @BeforeTest
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        //Driver loaded
        Class.forName("com.mysql.cj.jdbc.Driver");
        //Connection created
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/uploadcsv", "root", "root");
        //Statement created
        return con;
    }

    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
        driver = new ChromeDriver();
        WebDriver driver1 = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.flipkart.com/");
        driver1.get("https://www.amazon.com");
        Thread.sleep(1000);
    }

    @AfterTest
    public void tearDown() throws SQLException {
        con.close();
    }
}
