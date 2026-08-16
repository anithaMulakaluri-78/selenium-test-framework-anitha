package com.selenium.test;

import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;

import com.selenium.listners.TestListener;
import com.selenium.utils.ConfigReader;
import com.selenium.utils.ScreenshotUtils;

@Listeners(TestListener.class)
public class BaseTest{

	protected Logger logger =
	        LogManager.getLogger(BaseTest.class);
	@Parameters("browser")
	
	@BeforeMethod
	public void setup()
	{
		String environment = System.getProperty("environment", "qa");
		
		ConfigReader config=new ConfigReader(environment);
		String browser=config.getProperty("browser");
		String url=config.getProperty("baseUrl");
		 int implicitWait =
		            Integer.parseInt(
		                config.getProperty("implicitWait"));
		WebDriver driver =
                DriverFactory.createDriver(browser);

        DriverManager.setDriver(driver);

        DriverManager.getDriver()
                .manage()
                .window()
                .maximize();
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        DriverManager.getDriver()
        .get(url);
        logger.info("opened url");
     
	}


		@AfterMethod
		public void tearDown(ITestResult result) {

			/*
			 * if (result.getStatus() == ITestResult.FAILURE) {
			 * 
			 * ScreenshotUtils screenshotUtils=new
			 * ScreenshotUtils(DriverManager.getDriver());
			 * screenshotUtils.captureScreenshot( result.getName()); }
			 */
		    DriverManager.quitDriver();
		}
		 
	
}
