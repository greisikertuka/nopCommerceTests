package com.example.Projekti;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainPageTest {
    private WebDriver driver;
    private MainPage mainPage;
    private Register register;
    private Login login;
    private NoteBooks notebooks;
    private MyAccount myAccount;
    private ShoppingCart shoppingCart;
    private String email;
    private String password;
    private WebDriverWait wait;

    @BeforeAll
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","driver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://demo.nopcommerce.com/");
        wait= new WebDriverWait(driver, 4);
        email="greisikertuka@gmail.com";
        password="Lagertha123";
        mainPage = new MainPage(driver);
        login = new Login(driver);
        notebooks=new NoteBooks(driver);
        shoppingCart=new ShoppingCart(driver);
    }

    @AfterAll
    public void tearUp(){
        driver.quit();
    }

    @Order(1)
    @Test
    public void register() throws InterruptedException {
        register = new Register(driver);
        mainPage.loginButton.click();
        WebElement butoniRegjistrimit = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button-1 register-button']")));
        butoniRegjistrimit.click();
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//form[@action='/register?returnurl=%2F']"))));
        System.out.println("Titulli i faqes i kapur nga webdriver eshte: " +
               ""+driver.getTitle());
        register.fillRegisterForm("Ragnar","Lothbrok","7","June","1980",email,password);

        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[contains(text(),'Your registration completed')]"))));
            System.out.println("Regjistrimi u ruajt me sukses!");
            WebElement logoutButton = wait.until(ExpectedConditions.visibilityOf(mainPage.logoutButton));
            logoutButton.click();
        }
        catch(Exception e){
            try {
                WebElement emailExists=wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(text(),'The specified email already exists')]"))));
                System.out.println("Ka nje llogari te lidhur me kete email!");
            }
            catch(Exception ex){
                System.out.println("Regjistrimi ishte i pasuksesshem, pasi ka problem!");
            }
        }
    }

    @Order(2)
    @Test
    public void login(){
        mainPage.loginButton.click();
        login.fillLoginForm(email,password);
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//*[contains(text(),'Welcome to our store')]"))));
            wait.until(ExpectedConditions.visibilityOf(mainPage.logoutButton));
                System.out.println("U loguam me sukses!");
            mainPage.logoutButton.click();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Order(3)
    @Test
    public void myAccountTest(){
        mainPage.loginButton.click();
        login.fillLoginForm(email,password);
        myAccount= new MyAccount(driver);
        mainPage.myAccount.click();

        myAccount.checkData('m',"Ragnar","Lothbrok",email, "" +
                "Lufthansa Industry Solutions",7,6,1980);
        mainPage.logoutButton.click();
    }

    @Order(4)
    @Test
    public void dashboard() throws InterruptedException{
        driver.get("https://demo.nopcommerce.com/");
        Actions actions=new Actions(driver);
        mainPage.hover("//ul[@class='top-menu notmobile']/li[1]/a[1]");
        WebElement notebooksClick = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//ul[@class='top-menu notmobile']/li[1]/a[1]/following-sibling::ul/li[2]/a"))));
        actions.click(notebooksClick).perform();

        notebooks.checkURL(driver.getCurrentUrl());

        notebooks.selectNumberOfProductsDisplayed(9);
        Assertions.assertEquals(6,notebooks.numberOfElementsDisplayed());

        notebooks.check16GB();
        Assertions.assertEquals(1,notebooks.numberOfElementsDisplayed());

        notebooks.check16GB();
        Assertions.assertEquals(6,notebooks.numberOfElementsDisplayed());


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
    public void shoppingCart() throws InterruptedException{
        driver.get("https://demo.nopcommerce.com/");
        Actions actions=new Actions(driver);
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
        while(shoppingCart.elementsDisplayed()!=0)
            shoppingCart.deleteFirstElement();
        shoppingCart.shoppingCartEmptyCheck();
    }
}
