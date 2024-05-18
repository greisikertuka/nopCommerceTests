package com.example.Projekti.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage {

    @FindBy(css = ".ico-login")
    public WebElement loginButton;

    @FindBy(css = ".ico-logout")
    public WebElement logoutButton;

    @FindBy(css = ".ico-cart")
    public WebElement shoppingCart;

    @FindBy(css = ".ico-account")
    public WebElement myAccount;

    public MainPage() {
    }
}
