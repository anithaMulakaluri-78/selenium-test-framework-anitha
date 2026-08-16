package com.selenium.listners;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.selenium.test.DriverManager;
import com.selenium.utils.ScreenshotUtils;

public class TestListener implements ITestListener {

    private static final Logger logger =
            LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {

        logger.info(
                "Test started: {}",
                result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        logger.info(
                "Test passed: {}",
                result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {

    	String testName =
                result.getName();

        String className =
                result.getTestClass().getName();

        Throwable error =
                result.getThrowable();

        logger.error(
                "Test failed. Class: {}, Test: {}",
                className,
                testName,
                error);

        ScreenshotUtils screenshotUtils =
                new ScreenshotUtils(
                        DriverManager.getDriver());

        screenshotUtils.captureScreenshot(
                testName);
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        logger.warn(
                "Test skipped: {}",
                result.getName());
    }
    
    @Override
    public void onFinish(ITestContext context) {

        int total =
                context.getAllTestMethods().length;

        int passed =
                context.getPassedTests().size();

        int failed =
                context.getFailedTests().size();

        int skipped =
                context.getSkippedTests().size();

        logger.info(
                "Test execution completed. Total: {}, Passed: {}, Failed: {}, Skipped: {}",
                total,
                passed,
                failed,
                skipped);
        HTMLReportGenerator reportGenerator =
                new HTMLReportGenerator();

        reportGenerator.generateReport(context);
    }
}

