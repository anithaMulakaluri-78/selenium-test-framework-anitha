package com.selenium.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.selenium.test.BaseTest;
import com.selenium.test.DriverFactory;
import com.selenium.test.DriverManager;
import com.selenium.utils.WaitUtils;

public class LoginPages {
	protected Logger logger;
	private WebDriver driver;
	private By username=By.id("user-name");
	private By password = By.id("password");
	private By loginButton =By.id("login-button");
	private WaitUtils wait;
	//input[@id='user-name']
	//constructor
	public LoginPages(WebDriver driver) {
		
		this.driver = driver;
		this.wait = new WaitUtils(driver);
		this.logger =
		        LogManager.getLogger(LoginPages.class);
	}
	public void enterUserName(String uname) {
		wait.waitForVisibility(username);
		driver.findElement(this.username).sendKeys(uname);
		logger.info("entered user name");
		
	}
	public void enterpassword(String pwd) {
		wait.waitForVisibility(password);
		driver.findElement(this.password).sendKeys(pwd);
		
		
	}
	public void clickButton() {
		wait.waitForClickable(loginButton);
		driver.findElement(this.loginButton).click();		
		
	}
	public void login(String username, String password) {
		enterUserName(username);
		enterpassword(password);
		clickButton();
    }
	
	

}
