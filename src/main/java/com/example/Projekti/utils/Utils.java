package com.example.Projekti.utils;

import com.example.Projekti.utils.Constants;
import com.example.Projekti.utils.User;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
public class Utils {

    public Utils(WebDriver driver, Actions actions, WebDriverWait wait) {
        this.driver = driver;
        this.actions = actions;
        this.wait = wait;
    }

    private WebDriver driver;
    private Actions actions;
    private WebDriverWait wait;
    public void findAndClick(By by) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Constants.registerButtonXpath)));
        element.click();
    }
    public static WebElement findAndSendKeys(WebDriver driver, By by, String text) {
        WebElement element = driver.findElement(by);
        element.sendKeys(text);
        return element;
    }

}
