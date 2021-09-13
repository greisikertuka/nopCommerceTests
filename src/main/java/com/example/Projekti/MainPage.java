package com.example.Projekti;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriverWait wait;
    private WebDriver driver;

    @FindBy(css = ".ico-login")
    public WebElement loginButton;

    @FindBy(css = ".ico-logout")
    public WebElement logoutButton;

    @FindBy(css = ".ico-wishlist")
    public WebElement wishList;

    @FindBy(css = ".ico-cart")
    public WebElement shoppingCart;

    @FindBy(css = ".ico-register")
    public WebElement registerButton;

    @FindBy(css = ".ico-account")
    public WebElement myAccount;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        wait=new WebDriverWait(driver,4);
    }

    public void hover(String xPath){
        WebElement webElement=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
        Actions actions=new Actions(driver);
        actions.moveToElement(webElement).perform();
    }

    public void hover(WebElement webElement){
        wait.until(ExpectedConditions.visibilityOf(webElement));
        Actions actions=new Actions(driver);
        actions.moveToElement(webElement).perform();
    }
}
