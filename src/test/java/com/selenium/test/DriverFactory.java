package com.selenium.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {

        if (browser.equalsIgnoreCase("chrome")) {
            return new ChromeDriver();
        }

        if (browser.equalsIgnoreCase("firefox")) {
            return new FirefoxDriver();
        }

        if (browser.equalsIgnoreCase("edge")) {
            return new EdgeDriver();
        }

        throw new IllegalArgumentException(
                "Unsupported browser: " + browser);
    }
}