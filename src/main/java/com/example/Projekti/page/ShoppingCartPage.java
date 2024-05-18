package com.example.Projekti.page;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;


public class ShoppingCartPage {
    WebDriver driver;
    WebDriverWait wait;
    String currentURL = "https://demo.nopcommerce.com/cart";
    String displayedElements = "//table[@class='cart']/tbody/tr";
    String itemPriceXPath = "//table[@class='cart']//tbody/tr/td[@class='subtotal']//span";
    String totalPrice = "//tr[@class='order-total']/td[@class=\"cart-total-right\"]//strong";
    String firstElement = "(//table[@class='cart']//tbody/tr/td[1])[1]/span";
    String deleteFirstButton = "(//button[@class='remove-btn'])[1]";
    String shoppingCartEmpty = ".no-data";
    ArrayList<WebElement> elementsList;

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, Duration.ofSeconds(4));
        PageFactory.initElements(driver, this);

    }

    public void goToShoppingCart() {
        driver.get(currentURL);
    }

    public void checkURL(String url) {
        Assertions.assertEquals(currentURL, url);
    }

    public void checkButtons(String xPath) {
        elementsList = new ArrayList<>(driver.findElements(By.xpath(xPath)));
        for (WebElement button : elementsList) {
            Assertions.assertTrue(button.isDisplayed());
        }
    }

    public int elementsDisplayed() {
        elementsList = new ArrayList<>(driver.findElements(By.xpath(displayedElements)));
        return elementsList.size();
    }


    public void checkPrice() {
        elementsList = new ArrayList<>(driver.findElements(By.xpath(itemPriceXPath)));
        String itemSumString;
        double sum = 0.0;
        for (WebElement webElement : elementsList) {
            itemSumString = webElement.getText();
            itemSumString = itemSumString.substring(1);
            itemSumString = itemSumString.replace(",", "");
            sum += Double.parseDouble(itemSumString);
        }
        String totalExpectedPrice = driver.findElement(By.xpath(totalPrice)).getText();
        totalExpectedPrice = totalExpectedPrice.substring(1);
        totalExpectedPrice = totalExpectedPrice.replace(",", "");
        double expectedPrice = Double.parseDouble(totalExpectedPrice);
        Assertions.assertEquals(sum, expectedPrice);
    }

    public void shoppingCartEmptyCheck() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(shoppingCartEmpty)));
    }

    public void deleteFirstElement() throws InterruptedException {
        int previousNumber = elementsDisplayed();
        String firstElementText = driver.findElement(By.xpath(firstElement)).getText();
        driver.findElement(By.xpath(deleteFirstButton)).click();
        Thread.sleep(1000);
        Assertions.assertFalse(inHTML(firstElementText));
        int followingNumber = elementsDisplayed();
        Assertions.assertEquals(followingNumber, previousNumber - 1);
    }

    public boolean inHTML(String text) {
        try {
            driver.findElement(By.xpath("//*[text()='" + text + "']"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
