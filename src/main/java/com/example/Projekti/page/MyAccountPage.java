package com.example.Projekti.page;

import com.example.Projekti.utils.Gender;
import com.example.Projekti.utils.User;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class MyAccountPage {

    @FindBy(xpath = "//input[@id='gender-male']")
    public WebElement genderMale;

    @FindBy(xpath = "//input[@id='gender-female']")
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

    public MyAccountPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void checkData(User user){
        if (user.getGender().equals(Gender.MALE)) {
            Assertions.assertTrue(genderMale.isSelected());
        } else {
            Assertions.assertTrue(genderFemale.isSelected());
        }
        Assertions.assertEquals(firstName.getAttribute("value"),user.getName());
        Assertions.assertEquals(lastName.getAttribute("value"),user.getLastName());
        Assertions.assertEquals(email.getAttribute("value"),user.getEmail());
        Assertions.assertEquals(company.getAttribute("value"),user.getCompany());

        Select selectDay = new Select(date);
        Assertions.assertEquals(selectDay.getFirstSelectedOption().getText(), user.getBirthDay());

        Select selectMonth = new Select(month);
        Assertions.assertEquals(selectMonth.getFirstSelectedOption().getText(),user.getBirthMonth());

        Select selectYear = new Select(year);
        Assertions.assertEquals(selectYear.getFirstSelectedOption().getText(),user.getBirthYear());
    }
}
