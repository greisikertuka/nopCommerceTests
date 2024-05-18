package com.example.Projekti.page;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;


public class NoteBooksPage {
    WebDriver driver;
    WebDriverWait wait;
    String currentURL = "https://demo.nopcommerce.com/notebooks";
    String GB16 = "//label[text()=' 16 GB ']/preceding-sibling::input";
    String wishListxPath = "//p[text()='The product has been added to your ']/a[text()='wishlist']";
    String shoppingCartxPath = "//p[text()='The product has been added to your ']/a[text()='shopping cart']";
    String closexPath = "//p[text()='The product has been added to your ']/following-sibling::span";
    String DisplayedElements = "//div[@class='item-grid']/div";
    ArrayList<WebElement> elementsList;

    public NoteBooksPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(4));
        PageFactory.initElements(driver, this);
    }

    public void checkURL(String url) {
        Assertions.assertEquals(currentURL, url);
    }

    public void selectNumberOfProductsDisplayed(int n) throws InterruptedException {
        Select select = new Select(driver.findElement(By.xpath("//select[@aria-label='Select number of products per page']")));
        select.selectByValue("" + n);
        Thread.sleep(2000);
    }

    public void checkItemsCountAndClickCheckbox(int expectedItemsCount) throws InterruptedException {
        Assertions.assertEquals(expectedItemsCount, displayedElementsCount());
        click16GBCheckbox();
    }

    public void click16GBCheckbox() throws InterruptedException {
        driver.findElement(By.xpath(GB16)).click();
        Thread.sleep(2000);
    }

    public void checkWishList(int n) {
        String wishList = driver.findElement(By.className("wishlist-qty")).getText();
        String s = "(" + n + ")";
        Assertions.assertEquals(wishList, s);
    }

    public void checkShoppingCart(int n) {
        String wishList = driver.findElement(By.className("cart-qty")).getText();
        String s = "(" + n + ")";
        Assertions.assertEquals(wishList, s);
    }

    public int displayedElementsCount() {
        elementsList = new ArrayList<>(driver.findElements(By.xpath(DisplayedElements)));
        return elementsList.size();
    }

    public void addItemsToWishlistById(int[] idList) {
        for (int id : idList) {
            String xPath = "//div[@data-productid='" + (id + 3) + "']//*[@class='buttons']/button[3]";
            addToWishList(xPath, wishListxPath);
        }
    }

    private void addToWishList(String xPath, String wishListxPath) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath)));
        element.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(wishListxPath)));
        driver.findElement(By.xpath(closexPath)).click();
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath(wishListxPath))));
    }

    public void addItemsToCartbyId(int[] idList) {
        for (int id : idList) {
            String xPath = "//div[@data-productid='" + (id + 3) + "']//*[@class='buttons']/button[1]";
            addToWishList(xPath, shoppingCartxPath);
        }
    }
}
