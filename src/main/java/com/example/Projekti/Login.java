package com.example.Projekti;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {

    @FindBy(id = "Email")
    public WebElement email;

    @FindBy(id = "Password")
    public WebElement password;

    @FindBy(id = "RememberMe")
    public WebElement rememberMe;

    @FindBy(xpath = "//a[text()='Forgot password?']")
    public WebElement forgotPassword;

    @FindBy(xpath = "//button[text()='Log in']")
    public WebElement loginButton;

    public void fillLoginForm(String email,String password){
        this.email.sendKeys(email);
        this.password.sendKeys(password);
        this.rememberMe.click();
        this.loginButton.click();
    }

    public Login(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
