package com.example.Projekti.utils;

public class Constants {

    public final static String edgeDriverKey = "webdriver.edge.driver";
    public final static String registerButtonXpath = "//button[@class='button-1 register-button']";
    public final static String titleXpath = "//form[@action='/register?returnurl=%2F']";
    public final static String successfulRegistrationXpath = "//*[contains(text(),'Your registration completed')]";
    public final static String existingUserXpath = "//*[contains(text(),'The specified email already exists')]";
    public final static String welcomeMessageXpath = "//*[contains(text(),'Welcome to our store')]";
    public final static String computersXpath = "//ul[@class='top-menu notmobile']/li[1]/a[1]";
    public final static String notebooksXpath = "//ul[@class='top-menu notmobile']/li[1]/a[1]/following-sibling::ul/li[2]/a";

    public final static String emptyShoppingCartCSS = "div #flyout-cart";
    public final static String shoppingCartButtonsXpath = "//*[text()='Continue shopping' or text()=' Estimate shipping '] ";
    public final static String openShoppingCartButtonXpath = "//button[text()='Go to cart']";
    public final static String edgeDriverValue = "driver\\edge-driver.exe";
    public final static String homePageUrl = "https://demo.nopcommerce.com/";
    public final static User user = new User("Adam", "Smith", Gender.MALE, "gdsadsatuka@gmail.com", "TestNopCommerce123", "Lufthansa Industry Solutions", "7", "June", "1996", 7, 6, 1999);

}
