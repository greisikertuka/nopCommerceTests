package com.example.Projekti;

import com.example.Projekti.utils.Constants;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private Login login;
    private NoteBooks notebooks;
    private ShoppingCart shoppingCart;
    private WebDriverWait wait;

    @BeforeAll
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "driver\\edge-driver.exe");
        driver = new EdgeDriver();
         /*
         For Google Chrome it would be:
         System.setProperty("webdriver.chrome.driver","driver\\chromedriver.exe");
         driver = new ChromeDriver();
         */
        driver.manage().window().maximize();
        driver.get(Constants.pageURL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        mainPage = new MainPage(driver);
        login = new Login(driver);
        notebooks = new NoteBooks(driver);
        shoppingCart = new ShoppingCart(driver);
    }

    @AfterAll
    public void tearUp() {
        driver.quit();
    }

    @Order(1)
    @Test
    public void register() {
        Register register = new Register(driver);
        mainPage.loginButton.click();
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button-1 register-button']")));
        registerButton.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//form[@action='/register?returnurl=%2F']"))));
        System.out.println("Web page title is: " + driver.getTitle());
        register.fillRegisterForm(Constants.name, Constants.lastName, Constants.birthDay, Constants.birthMonth, Constants.birthYear, Constants.email, Constants.password);

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[contains(text(),'Your registration completed')]"))));
            System.out.println("Registration was successful!");
            WebElement logoutButton = wait.until(ExpectedConditions.visibilityOf(mainPage.logoutButton));
            logoutButton.click();
        } catch (Exception e) {
            try {
                WebElement emailExists = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(text(),'The specified email already exists')]"))));
                System.out.println("There is an existing account with this email!");
            } catch (Exception ex) {
                System.out.println("There was an error while registering!");
            }
        }
    }

    @Order(2)
    @Test
    public void login() {
        mainPage.loginButton.click();
        login.fillLoginForm(Constants.email, Constants.password);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[contains(text(),'Welcome to our store')]"))));
            wait.until(ExpectedConditions.visibilityOf(mainPage.logoutButton));
            System.out.println("Login was successful!");
            mainPage.logoutButton.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Order(3)
    @Test
    public void myAccountTest() {
        mainPage.loginButton.click();
        login.fillLoginForm(Constants.email, Constants.password);
        MyAccount myAccount = new MyAccount(driver);
        mainPage.myAccount.click();

        myAccount.checkData(true, Constants.name, Constants.lastName, Constants.email, Constants.company, Constants.birthDayInt, Constants.birthMonthInt, Constants.birthYearInt);
        mainPage.logoutButton.click();
    }

    @Order(4)
    @Test
    public void dashboard() throws InterruptedException {
        driver.get(Constants.pageURL);
        Actions actions = new Actions(driver);
        mainPage.hover("//ul[@class='top-menu notmobile']/li[1]/a[1]");
        WebElement notebooksClick = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[@class='top-menu notmobile']/li[1]/a[1]/following-sibling::ul/li[2]/a"))));
        actions.click(notebooksClick).perform();

        notebooks.checkURL(driver.getCurrentUrl());

        notebooks.selectNumberOfProductsDisplayed(9);
        Assertions.assertEquals(6, notebooks.numberOfElementsDisplayed());

        notebooks.check16GB();
        Assertions.assertEquals(1, notebooks.numberOfElementsDisplayed());

        notebooks.check16GB();
        Assertions.assertEquals(6, notebooks.numberOfElementsDisplayed());


        notebooks.addToWishListById(2);
        notebooks.addToWishListById(3);

        notebooks.addToCartById(4);
        notebooks.addToCartById(5);
        notebooks.addToCartById(6);

        notebooks.checkWishList(2);

        notebooks.checkShoppingCart(3);

    }

    @Order(5)
    @Test
    public void shoppingCart() throws InterruptedException {
        driver.get(Constants.pageURL);
        Actions actions = new Actions(driver);
        mainPage.hover(mainPage.shoppingCart);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div #flyout-cart"))));
        WebElement goToCartButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Go to cart']")));
        goToCartButton.click();

        shoppingCart.checkURL(driver.getCurrentUrl());

        shoppingCart.checkButtons("//*[text()='Update shopping cart' or text()='Continue shopping' or text()=' Estimate shipping '] ");

        shoppingCart.checkPrice();
    }

    @Order(6)
    @Test
    public void emptyShoppingCart() throws InterruptedException {
        shoppingCart.goToShoppingCart();
        while (shoppingCart.elementsDisplayed() != 0)
            shoppingCart.deleteFirstElement();
        shoppingCart.shoppingCartEmptyCheck();
    }
}
