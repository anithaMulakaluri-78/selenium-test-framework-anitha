package com.selenium.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtils {

    private WebDriver driver;

    public ScreenshotUtils(WebDriver driver) {
        this.driver = driver;
    }

    public String captureScreenshot(String testName) {

        TakesScreenshot screenshot =
                (TakesScreenshot) driver;

        File source =
                screenshot.getScreenshotAs(
                        OutputType.FILE);

        String path =
                System.getProperty("user.dir")
                + File.separator
                + "test-output"
                + File.separator
                + "screenshots"
                + File.separator
                + testName
                + ".png";

        try {

            Path destination =
                    Path.of(path);

            Files.createDirectories(
                    destination.getParent());

            Files.copy(
                    source.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING);

            return "screenshots/"
                    + testName
                    + ".png";

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to capture screenshot",
                    e);
        }
    }
}