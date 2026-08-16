package com.selenium.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.selenium.test.DriverManager;
import com.selenium.utils.AlertsUtils;
import com.selenium.utils.ElementUtils;
import com.selenium.utils.WaitUtils;

public class ProductsPages {
	private WebDriver driver;
	private WaitUtils wait;
	private AlertsUtils alert;
	private ElementUtils utils;
	
	private By sortDropDown=By.xpath("//select[@class='product_sort_container']");
	private By productPrices = By.className("inventory_item_price");
	private By facebook=By.xpath("//li[@class='social_facebook']");
	
	public ProductsPages(WebDriver driver)
	{
		this.driver=driver;
		this.wait = new WaitUtils(driver);
		this.alert=new AlertsUtils(driver, wait);
		this.utils=new ElementUtils(driver, wait);
	}
	
	public void sortByLowtoHighTest()
	{
		
		ElementUtils utils =new ElementUtils(driver, wait);
		utils.selectByText(sortDropDown, "Price (low to high)");	
		
	}

	public boolean isPriceSortedLowToHigh() {

		List<WebElement> elements = driver.findElements(productPrices);
		wait.waitForVisibility(productPrices);

		List<Integer> actualPrices = new ArrayList<>();

		for (WebElement element : elements) {

			String priceText = element.getText();

			int price = (int) Double.parseDouble(priceText.replace("$", ""));

			actualPrices.add(price);
		}

		List<Integer> sortedPrices = new ArrayList<>(actualPrices);

		Collections.sort(sortedPrices);
		return actualPrices.equals(sortedPrices);
		
	}

	public void scrollToFacebook()
	{
		utils.scrollToElement(facebook);
	}
}
