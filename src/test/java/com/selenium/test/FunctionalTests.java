package com.selenium.test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.selenium.pages.LoginPages;
import com.selenium.pages.ProductsPages;

public class FunctionalTests extends BaseTest{
	private LoginPages login;
    private ProductsPages products;
    WebDriver driver;
    
    @BeforeMethod
	public void setupPOM() {
    	driver=DriverManager.getDriver();
        login = new LoginPages(driver);
        products = new ProductsPages(driver);
        login.login("standard_user", "secret_sauce");
	}
    @Test
    public void testAlert()
    {
    	
    	
    }
    @Test
    public void testScroll() throws InterruptedException
    {
    	products.scrollToFacebook();
    	
    	
    }
	
	
	

}
