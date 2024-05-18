package com.example.Projekti;

import com.example.Projekti.page.*;
import com.example.Projekti.utils.Constants;
import com.example.Projekti.utils.User;
import com.example.Projekti.utils.Utils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainPageTest {
    private User user;
    private WebDriver driver;
    private Actions actions;
    private MainPage mainPage;
    private RegisterPage registerPage;
    private LoginPage loginPage;
    private MyAccountPage myAccountPage;
    private NoteBooksPage notebooksPage;
    private ShoppingCartPage shoppingCartPage;
    private Utils utils;
    private static final Logger logger = LoggerFactory.getLogger(MainPageTest.class);


    @BeforeAll
    public void setUp() {
        _setUpDriver();
        _initializePages();
        user = Constants.user;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        actions = new Actions(driver);
        utils = new Utils(driver, actions, wait, logger);
    }

    @AfterAll
    public void tearUp() {
        driver.quit();
    }

    @Order(1)
    @Test
    public void registerTest() {
        mainPage.loginButton.click();
        utils.findAndClick(By.xpath(Constants.registerButtonXpath));
        utils.findAndWaitUntilVisible(By.xpath(Constants.titleXpath), "Web page title is: " + driver.getTitle());
        registerPage.fillRegisterForm(user);

        try {
            utils.findAndWaitUntilVisible(By.xpath(Constants.successfulRegistrationXpath), "Registration was successful!");
            utils.clickWhenVisible(mainPage.logoutButton);
        } catch (Exception e) {
            try {
                utils.findAndWaitUntilVisible(By.xpath(Constants.existingUserXpath), "There is an existing account with this email!");
            } catch (Exception ex) {
                logger.error("There was an error while registering!");
            }
        }
    }

    @Order(2)
    @Test
    public void loginTest() {
        _login();
        try {
            utils.findAndWaitUntilVisible(By.xpath(Constants.welcomeMessageXpath), "");
            utils.waitUntilVisible(mainPage.logoutButton, "Login was successful!");
            mainPage.logoutButton.click();
        } catch (Exception exception) {
            logger.info(exception.getMessage(), exception);
        }
    }

    @Order(3)
    @Test
    public void myAccountTest() {
        _login();
        mainPage.myAccount.click();
        myAccountPage.checkProfileData(user);
        mainPage.logoutButton.click();
    }

    @Order(4)
    @Test
    public void dashboardTest() {
        _testNoteBooksPage();
        int[] wishlistItems = {2, 3};
        notebooksPage.addItemsToWishlistById(wishlistItems);
        notebooksPage.checkWishList(2);
        notebooksPage.checkShoppingCart(3);

    }

    @Order(5)
    @Test
    public void shoppingCartTest() {
        _testNoteBooksPage();
        utils.hoverElement(mainPage.shoppingCart);
        //mainPage.hover(mainPage.shoppingCart);
        utils.findAndWaitUntilVisible(By.cssSelector(Constants.emptyShoppingCartCSS), "");
        utils.findAndClickWhenVisible(By.xpath(Constants.openShoppingCartButtonXpath));

        shoppingCartPage.checkURL(driver.getCurrentUrl());
        shoppingCartPage.checkButtons(Constants.shoppingCartButtonsXpath);
        shoppingCartPage.checkPrice();
    }

    @Order(6)
    @Test
    public void emptyShoppingCartTest() throws InterruptedException {
        shoppingCartPage.goToShoppingCart();
        while (shoppingCartPage.elementsDisplayed() != 0) shoppingCartPage.deleteFirstElement();
        shoppingCartPage.shoppingCartEmptyCheck();
    }


    private void _setUpDriver() {
        System.setProperty(Constants.edgeDriverKey, Constants.edgeDriverValue);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(Constants.homePageUrl);
    }

    private void _initializePages() {
        mainPage = new MainPage();
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        myAccountPage = new MyAccountPage(driver);
        notebooksPage = new NoteBooksPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
    }

    private void _login() {
        mainPage.loginButton.click();
        loginPage.fillLoginForm(user.getEmail(), user.getPassword());
    }

    private void _testNoteBooksPage() {
        driver.get(Constants.homePageUrl);
        utils.hover(By.xpath(Constants.computersXpath));

        WebElement notebooksClick = utils.findWhenVisible(By.xpath(Constants.notebooksXpath));
        actions.click(notebooksClick).perform();
        notebooksPage.checkURL(driver.getCurrentUrl());

        try {
            notebooksPage.selectNumberOfProductsDisplayed(9);
            notebooksPage.checkItemsCountAndClickCheckbox(6);
            notebooksPage.checkItemsCountAndClickCheckbox(1);
        } catch (InterruptedException exception) {
            logger.error("There was an error while displaying notebooks!");
        }

        Assertions.assertEquals(6, notebooksPage.displayedElementsCount());
        int[] shopcartItems = {4, 5, 6};
        notebooksPage.addItemsToCartbyId(shopcartItems);
    }
}
