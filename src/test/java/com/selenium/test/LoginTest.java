package com.selenium.test;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.selenium.pages.LoginPages;
import com.selenium.pages.ProductsPages;

public class LoginTest extends BaseTest {

	//@Test
	public void loginTest() {

		DriverManager.getDriver().get("https://www.google.com");

		System.out.println(DriverManager.getDriver().getTitle());
	}
   @Parameters("chrome")
	@Test
	public void validLoginTest() {

		LoginPages loginPage = new LoginPages(DriverManager.getDriver());

		loginPage.login("standard_user", "secret_sauce");

		Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains("inventory.html"));
	}
   @Test
   public void sortByPrice() throws InterruptedException {
	   LoginPages loginPage = new LoginPages(DriverManager.getDriver());
	   loginPage.login("standard_user", "secret_sauce");
	   ProductsPages product=new ProductsPages(DriverManager.getDriver());
	   product.sortByLowtoHighTest();
	  Assert.assertFalse(product.isPriceSortedLowToHigh(), "Products are not sorted"); 
	   
   }
   /*
	 * LoginPages login =new LoginPages(driver); login.login("standard_user",
	 * "secret_sauce");
	 * Assert.assertTrue(DriverManager.getDriver().getCurrentUrl().contains(
	 * "inventory.html"));
	 */
	//sort
}
