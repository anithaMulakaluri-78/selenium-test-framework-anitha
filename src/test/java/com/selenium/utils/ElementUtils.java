package com.selenium.utils;

import java.io.File;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementUtils {

	private WebDriver driver;
	private WaitUtils wait;
	private Actions actions;
	private JavascriptExecutor js;

	public ElementUtils(WebDriver driver, WaitUtils wait) {
		this.driver = driver;
		this.wait = wait;
		this.actions = new Actions(driver);
		this.js=(JavascriptExecutor)driver;
	}

	public void click(By locator) {
		wait.waitForClickable(locator).click();
	}

	public void type(By locator, String text) {
		wait.waitForVisibility(locator).sendKeys(text);
	}

	public String getText(By locator) {
		return wait.waitForVisibility(locator).getText();
	}

	public void clear(By locator) {
		wait.waitForVisibility(locator).clear();
	}

	public boolean isDisplayed(By locator) {
		return wait.waitForVisibility(locator).isDisplayed();
	}

	public void selectByText(By locator, String visibleText) {
		Select sel = new Select(wait.waitForVisibility(locator));
		sel.selectByVisibleText(visibleText);
	}

	public void selectByIndex(By locator, int index) {
		Select sel = new Select(wait.waitForVisibility(locator));
		sel.selectByIndex(index);
	}

	public void selectByValue(By locator, String value) {
		Select sel = new Select(wait.waitForVisibility(locator));
		sel.selectByValue(value);
	}
	// =========================
	// MOUSE ACTIONS
	// =========================

	// Hover
	public void hover(By locator) {
		WebElement element = wait.waitForVisibility(locator);
		actions.moveToElement(element).perform();
	}

	// Double click
	public void doubleClick(By locator) {
		WebElement element = wait.waitForClickable(locator);
		actions.doubleClick(element).perform();
	}

	// Right click
	public void rightClick(By locator) {
		WebElement element = wait.waitForClickable(locator);
		actions.contextClick(element).perform();
	}

	// Click and hold
	public void clickAndHold(By locator) {

		WebElement element = wait.waitForVisibility(locator);
		actions.clickAndHold(element).perform();
	}

	// Release mouse
	public void release() {
		actions.release().perform();
	}

	// Drag and drop
	public void dragAndDrop(By source, By target) {
		WebElement sourceElement = wait.waitForVisibility(source);
		WebElement targetElement = wait.waitForVisibility(target);
		actions.dragAndDrop(sourceElement, targetElement).perform();
	}

	// =========================
	// KEYBOARD ACTIONS
	// =========================

	public void pressEnter(By locator) {

		WebElement element = wait.waitForVisibility(locator);

		element.sendKeys(Keys.ENTER);
	}

	public void pressEscape(By locator) {

		WebElement element = wait.waitForVisibility(locator);

		element.sendKeys(Keys.ESCAPE);
	}

	public void controlA(By locator) {

		WebElement element = wait.waitForVisibility(locator);

		element.sendKeys(Keys.CONTROL, "a");
	}

	public void copy(By locator) {

		WebElement element = wait.waitForVisibility(locator);
		element.sendKeys(Keys.CONTROL, "c");
	}

	public void paste(By locator) {

		WebElement element = wait.waitForVisibility(locator);
		element.sendKeys(Keys.CONTROL, "v");
	}

	// =========================
	// ADVANCED ACTIONS
	// =========================

	public void moveAndClick(By locator) {

		WebElement element = wait.waitForClickable(locator);
		actions.moveToElement(element).click().perform();
	}

	public void moveAndDoubleClick(By locator) {

		WebElement element = wait.waitForClickable(locator);
		actions.moveToElement(element).doubleClick().perform();
	}

	public void moveAndRightClick(By locator) {

		WebElement element = wait.waitForClickable(locator);

		actions.moveToElement(element).contextClick().perform();
	}

	public void switchToFrame(int index) {
		driver.switchTo().frame(index);
	}

	public void switchToFrame(String nameOrId) {
		driver.switchTo().frame(nameOrId);

	}

	public void switchToFrame(By locator) {

		WebElement frame = wait.waitForPresence(locator);

		driver.switchTo().frame(frame);
	}

	public void switchToParentFrame() {
		driver.switchTo().parentFrame();
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}

	public String getCurrentWindowHandle() {
		return driver.getWindowHandle();
	}

	public Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	public void switchToWindow(String windowHandle) {
		driver.switchTo().window(windowHandle);
	}

	public void switchToChildWindow(String parentWindow, int expectedWindowCount) {

		wait.waitForNumberOfWindows(expectedWindowCount);

		for (String window : driver.getWindowHandles()) {

			if (!window.equals(parentWindow)) {
				driver.switchTo().window(window);
				return;
			}
		}

		throw new RuntimeException("Child window was not found");
	}

	public void switchToParentWindow(String parentWindow) {
		driver.switchTo().window(parentWindow);
	}

	public void closeCurrentWindowAndSwitchToParent(
	        String parentWindow) {

	    driver.close();

	    driver.switchTo().window(parentWindow);
	}
	//JavascriptExecutor to scroll to element
	public void scrollToElement(By locator)
	{
		WebElement element=wait.waitForPresence(locator);
		js.executeScript("arguments[0].scrollIntoView(true)", element);
	}
	public void scrollBy(int x, int y) {

	    js.executeScript(
	            "window.scrollBy(arguments[0], arguments[1]);",
	            x,
	            y
	    );
	}
	public void scrollToTop()
	{
		js.executeScript("window.scrollTo(0,0)");
		
	}
	public void scrollToBottom() {

	    js.executeScript(
	            "window.scrollTo(0, document.body.scrollHeight);"
	    );
	}
	public void uploadFile(By locator, String filePath) {

	    WebElement element =
	            wait.waitForPresence(locator);

	    element.sendKeys(filePath);
	}
	public boolean isFileDownloaded(
	        String downloadPath,
	        String fileName) {

	    File file =
	        new File(downloadPath, fileName);

	    return file.exists();
	}
}