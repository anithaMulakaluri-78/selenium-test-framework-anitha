package com.selenium.test;

import com.selenium.utils.ConfigReader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public class BaseApiTest {

    @BeforeSuite(alwaysRun = true)
    public void setupApi() {
        String environment = System.getProperty("environment", "qa");
        ConfigReader config = new ConfigReader(environment);
        RestAssured.baseURI = config.getProperty("apiBaseUrl");
        System.out.println("API Base URL: " + RestAssured.baseURI);
    }
}