package com.example.Projekti.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".ico-login")
    public WebElement loginButton;

    @FindBy(css = ".ico-logout")
    public WebElement logoutButton;

    @FindBy(css = ".ico-cart")
    public WebElement shoppingCart;

    @FindBy(css = ".ico-account")
    public WebElement myAccount;

    public void hover(WebElement webElement) {
        wait.until(ExpectedConditions.visibilityOf(webElement));
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).perform();
    }

}
