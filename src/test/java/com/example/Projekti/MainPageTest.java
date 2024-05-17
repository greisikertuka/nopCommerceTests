package com.example.Projekti;

import com.example.Projekti.utils.Constants;
import com.example.Projekti.utils.User;
import com.example.Projekti.utils.Utils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
    private WebDriverWait wait;
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
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        actions = new Actions(driver);
        utils = new Utils(driver, actions, wait);
    }

    void _setUpDriver() {
        System.setProperty(Constants.edgeDriverKey, Constants.edgeDriverValue);
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.get(Constants.pageURL);
    }

    void _initializePages() {
        mainPage = new MainPage(driver);
        registerPage = new RegisterPage(driver);
        loginPage = new LoginPage(driver);
        myAccountPage = new MyAccountPage(driver);
        notebooksPage = new NoteBooksPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
    }

    @AfterAll
    public void tearUp() {
        driver.quit();
    }

    @Order(1)
    @Test
    public void register() {
        mainPage.loginButton.click();
        utils.findAndClick(By.xpath(Constants.registerButtonXpath));
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Constants.registerButtonXpath)));
        registerButton.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(Constants.titleXpath))));
        logger.info("Web page title is: " + driver.getTitle());
        registerPage.fillRegisterForm(user);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(Constants.successfulRegistrationXpath))));
            logger.info("Registration was successful!");
            WebElement logoutButton = wait.until(ExpectedConditions.visibilityOf(mainPage.logoutButton));
            logoutButton.click();
        } catch (Exception e) {
            try {
                wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(Constants.existingUserXpath))));
                logger.warn("There is an existing account with this email!");
            } catch (Exception ex) {
                logger.error("There was an error while registering!");
            }
        }
    }

    @Order(2)
    @Test
    public void login() {
        mainPage.loginButton.click();
        loginPage.fillLoginForm(user.getEmail(), user.getPassword());
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath(Constants.welcomeMessageXpath))));
            wait.until(ExpectedConditions.visibilityOf(mainPage.logoutButton));
            logger.info("Login was successful!");
            mainPage.logoutButton.click();
        } catch (Exception exception) {
            logger.info(exception.getMessage(), exception);
        }
    }

    @Order(3)
    @Test
    public void myAccountTest() {
        mainPage.loginButton.click();
        loginPage.fillLoginForm(user.getEmail(), user.getPassword());
        mainPage.myAccount.click();

        myAccountPage.checkData(user);
        mainPage.logoutButton.click();
    }

    @Order(4)
    @Test
    public void dashboard() throws InterruptedException {
        driver.get(Constants.pageURL);
        mainPage.hover(Constants.computersXpath);
        WebElement notebooksClick = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(Constants.notebooksXpath))));
        actions.click(notebooksClick).perform();

        notebooksPage.checkURL(driver.getCurrentUrl());

        notebooksPage.selectNumberOfProductsDisplayed(9);
        Assertions.assertEquals(6, notebooksPage.numberOfElementsDisplayed());

        notebooksPage.check16GB();
        Assertions.assertEquals(1, notebooksPage.numberOfElementsDisplayed());

        notebooksPage.check16GB();
        Assertions.assertEquals(6, notebooksPage.numberOfElementsDisplayed());


        notebooksPage.addToWishListById(2);
        notebooksPage.addToWishListById(3);

        notebooksPage.addToCartById(4);
        notebooksPage.addToCartById(5);
        notebooksPage.addToCartById(6);

        notebooksPage.checkWishList(2);

        notebooksPage.checkShoppingCart(3);

    }

    @Order(5)
    @Test
    public void shoppingCart() {
        driver.get(Constants.pageURL);
        mainPage.hover(mainPage.shoppingCart);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div #flyout-cart"))));
        WebElement goToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Go to cart']")));
        goToCartButton.click();

        shoppingCartPage.checkURL(driver.getCurrentUrl());

        shoppingCartPage.checkButtons("//*[text()='Update shopping cart' or text()='Continue shopping' or text()=' Estimate shipping '] ");

        shoppingCartPage.checkPrice();
    }

    @Order(6)
    @Test
    public void emptyShoppingCart() throws InterruptedException {
        shoppingCartPage.goToShoppingCart();
        while (shoppingCartPage.elementsDisplayed() != 0)
            shoppingCartPage.deleteFirstElement();
        shoppingCartPage.shoppingCartEmptyCheck();
    }
}
