package com.example.Projekti.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public Utils(WebDriver driver, Actions actions, WebDriverWait wait, Logger logger) {
        this.driver = driver;
        this.actions = actions;
        this.wait = wait;
        this.logger = logger;
    }

    private final WebDriver driver;
    private final Actions actions;
    private final WebDriverWait wait;
    private final Logger logger;

    public void findAndClick(By by) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
        element.click();
    }

    public void clickWhenVisible(WebElement element) {
        element = wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
    }

    public void findAndClickWhenVisible(By by) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        element.click();
    }

    public void findAndWaitUntilVisible(By by, String message) {
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
        logger.info(message);
    }

    public void waitUntilVisible(WebElement element, String message) {
        wait.until(ExpectedConditions.visibilityOf(element));
        logger.info(message);
    }

    public WebElement findWhenVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOf(driver.findElement(by)));
    }

    public void hover(By by) {
        WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        actions.moveToElement(webElement).perform();
    }
}
