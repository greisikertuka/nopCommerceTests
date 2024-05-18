package com.example.Projekti.page;

import com.example.Projekti.utils.Gender;
import com.example.Projekti.utils.User;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class RegisterPage {

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

    @FindBy(css = "select[name=DateOfBirthYear]")
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

    public RegisterPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void fillRegisterForm(User user) {
        if (user.getGender().equals(Gender.MALE)) {
            genderMale.click();
        } else {
            genderFemale.click();
        }
        firstName.sendKeys(user.getName());
        lastName.sendKeys(user.getLastName());
        Select daySelect = new Select(date);
        daySelect.selectByVisibleText(user.getBirthDay());
        Select monthSelect = new Select(month);
        monthSelect.selectByVisibleText(user.getBirthMonth());
        Select yearSelect = new Select(year);
        yearSelect.selectByVisibleText(user.getBirthYear());
        email.sendKeys(user.getEmail());
        company.sendKeys(user.getCompany());
        newsLetter.click();
        password.sendKeys(user.getPassword());
        confirmPassword.sendKeys(user.getPassword());
        registerButton.click();
    }
}
