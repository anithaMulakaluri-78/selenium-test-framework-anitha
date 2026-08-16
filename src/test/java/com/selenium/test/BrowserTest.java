package com.selenium.test;



import org.openqa.selenium.WebDriver;

import com.selenium.test.DriverFactory;

public class BrowserTest {

    public static void main(String[] args) {

        WebDriver driver =
                DriverFactory.createDriver("chrome");
        DriverManager.setDriver(driver);
       DriverManager.getDriver().get("https://www.google.com");

        System.out.println( DriverManager.getDriver().getTitle());

        DriverManager.getDriver().quit();
    }
}
