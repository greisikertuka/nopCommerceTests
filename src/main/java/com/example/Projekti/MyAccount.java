package com.example.Projekti;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class MyAccount {

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

    public MyAccount(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void checkData(char gender,String emri,String mbiemri,String emaili,String kompania,int d,int m,int y){
        switch (gender){
            case 'm':   Assertions.assertEquals(true,genderMale.isSelected());
                        break;
            case 'f':   Assertions.assertEquals(true,genderFemale.isSelected());
                        break;
        }
        Assertions.assertEquals(firstName.getAttribute("value"),emri);
        Assertions.assertEquals(lastName.getAttribute("value"),mbiemri);
        Assertions.assertEquals(email.getAttribute("value"),emaili);
        Assertions.assertEquals(company.getAttribute("value"),kompania);

        Select selectDay = new Select(date);
        Assertions.assertEquals(selectDay.getFirstSelectedOption().getText(),"7");

        Select selectMonth = new Select(month);
        Assertions.assertEquals(selectMonth.getFirstSelectedOption().getText(),"June");

        Select selectYear = new Select(year);
        Assertions.assertEquals(selectYear.getFirstSelectedOption().getText(),"1980");
    }
}
