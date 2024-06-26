package com.example.Projekti.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = "Email")
    public WebElement email;

    @FindBy(id = "Password")
    public WebElement password;

    @FindBy(id = "RememberMe")
    public WebElement rememberMe;

    @FindBy(xpath = "//button[text()='Log in']")
    public WebElement loginButton;

    public void fillLoginForm(String email, String password) {
        this.email.sendKeys(email);
        this.password.sendKeys(password);
        this.rememberMe.click();
        this.loginButton.click();
    }

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
