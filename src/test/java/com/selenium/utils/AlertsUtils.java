package com.selenium.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertsUtils {
	
	private WebDriver driver;
	private WaitUtils wait;
	
	public AlertsUtils(WebDriver driver,WaitUtils wait)
	{
		this.driver=driver;
		this.wait=wait;
	}
	public void acceptAlert()
	{
		wait.waitForAlert().accept();
		
	}
	public void dismissAlert()
	{
		wait.waitForAlert().dismiss();
		
	}
	public String getAlertText()
	{
		String alertText=wait.waitForAlert().getText();
		return alertText;
	}
	public void enterAlertText(String alertText)
	{
		wait.waitForAlert().sendKeys(getAlertText());
		
	}
	 public boolean isAlertPresent() {

	        try {
	            wait.waitForAlert();
	            return true;

	        } catch (Exception e) {
	            return false;
	        }
	
	 }
}
