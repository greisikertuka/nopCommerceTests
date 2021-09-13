package com.example.Projekti;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Register {

    @FindBy(className = "male")
    public WebElement genderMale;

    @FindBy(className = "female")
    public WebElement genderFemale;

    @FindBy(id = "FirstName")
    public WebElement firstName;

    @FindBy(id = "LastName")
    public WebElement lastName;

    @FindBy(css = "select[name=DateOfBirthDay]")
    public WebElement date;

    @FindBy(css = "select[name=DateOfBirthMonth]")
    public WebElement month;

    @FindBy(css=  "select[name=DateOfBirthYear]")
    public WebElement year;

    @FindBy(id = "Email")
    public WebElement email;

    @FindBy(id = "Company")
    public WebElement company;

    @FindBy(css = "input#Newsletter")
    public WebElement newsLetter;

    @FindBy(id = "Password")
    public WebElement password;

    @FindBy(id = "ConfirmPassword")
    public WebElement confirmPassword;

    @FindBy(id = "register-button")
    public WebElement registerButton;

    public Register(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void fillRegisterForm(String emri,String mbiemri,String dita,String muaji,String viti
    ,String emaili,String passwordi){
        genderMale.click();
        firstName.sendKeys(emri);
        lastName.sendKeys(mbiemri);
        Select day= new Select(date);
        day.selectByVisibleText(dita);
        Select muaj= new Select(month);
        muaj.selectByVisibleText(muaji);
        Select vit= new Select(year);
        vit.selectByVisibleText(viti);
        email.sendKeys(emaili);
        company.sendKeys("Lufthansa Industry Solutions");
        newsLetter.click();
        password.sendKeys(passwordi);
        confirmPassword.sendKeys(passwordi);
        registerButton.click();
    }
}
