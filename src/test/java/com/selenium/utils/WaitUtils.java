package com.selenium.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

    private WebDriver driver;
    private WebDriverWait wait;
    private FluentWait<WebDriver> fluentWait;
   

    String environment = System.getProperty("environment");

    ConfigReader config = new ConfigReader(environment);

    int timeout = Integer.parseInt(
            config.getProperty("explicitWait")
    );

    public WaitUtils(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(timeout)
        );
        this.fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeout)).pollingEvery(Duration.ofMillis(500)).ignoreAll(
                        List.of(StaleElementReferenceException.class)
                        );
    }

    // =========================================================
    // PRIVATE CUSTOM WAIT
    // =========================================================

    private WebDriverWait getCustomWait(int seconds) {

        return new WebDriverWait(
                driver,
                Duration.ofSeconds(seconds)
        );
    }


    // =========================================================
    // VISIBILITY
    // =========================================================

    // Uses default explicitWait from properties
    public WebElement waitForVisibility(By locator) {
        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(locator)
        );
    }

    // Uses specific timeout
    public WebElement waitForVisibility(
            By locator,
            int seconds) {

        return getCustomWait(seconds).until(
                ExpectedConditions
                        .visibilityOfElementLocated(locator)
        );
    }


    // =========================================================
    // CLICKABLE
    // =========================================================

    public WebElement waitForClickable(By locator) {

        return wait.until(
                ExpectedConditions
                        .elementToBeClickable(locator)
        );
    }

    public WebElement waitForClickable(
            By locator,
            int seconds) {

        return getCustomWait(seconds).until(
                ExpectedConditions
                        .elementToBeClickable(locator)
        );
    }


    // =========================================================
    // PRESENCE
    // =========================================================

    public WebElement waitForPresence(By locator) {

        return wait.until(
                ExpectedConditions
                        .presenceOfElementLocated(locator)
        );
    }

    public WebElement waitForPresence(
            By locator,
            int seconds) {

        return getCustomWait(seconds).until(
                ExpectedConditions
                        .presenceOfElementLocated(locator)
        );
    }


    // =========================================================
    // INVISIBILITY
    // =========================================================

    public boolean waitForInvisibility(By locator) {

        return wait.until(
                ExpectedConditions
                        .invisibilityOfElementLocated(locator)
        );
    }

    public boolean waitForInvisibility(
            By locator,
            int seconds) {

        return getCustomWait(seconds).until(
                ExpectedConditions
                        .invisibilityOfElementLocated(locator)
        );
    }


    // =========================================================
    // TITLE
    // =========================================================

    public boolean waitForTitle(String title) {

        return wait.until(
                ExpectedConditions
                        .titleIs(title)
        );
    }

    public boolean waitForTitle(
            String title,
            int seconds) {

        return getCustomWait(seconds).until(
                ExpectedConditions
                        .titleIs(title)
        );
    }


    // =========================================================
    // URL
    // =========================================================

    public boolean waitForUrl(String url) {

        return wait.until(
                ExpectedConditions
                        .urlToBe(url)
        );
    }

    public boolean waitForUrl(
            String url,
            int seconds) {

        return getCustomWait(seconds).until(
                ExpectedConditions
                        .urlToBe(url)
        );
    }


    // =========================================================
    // ALERT
    // =========================================================

    public Alert waitForAlert() {

        return wait.until(
                ExpectedConditions
                        .alertIsPresent()
        );
    }

    public Alert waitForAlert(int seconds) {

        return getCustomWait(seconds).until(
                ExpectedConditions
                        .alertIsPresent()
        );
    }
    public boolean waitForNumberOfWindows(
            int expectedCount) {

        return wait.until(
                ExpectedConditions
                        .numberOfWindowsToBe(
                                expectedCount));
    }public WebElement waitForStableElement(By locator) {

        return fluentWait.until(driver -> {

            WebElement element =
                    driver.findElement(locator);

            if (element.isDisplayed() &&
                element.isEnabled()) {

                return element;
            }

            return null;
        });
    }
    
}