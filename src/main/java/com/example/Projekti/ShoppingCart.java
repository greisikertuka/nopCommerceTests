package com.example.Projekti;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Locale;
import java.util.NoSuchElementException;


public class ShoppingCart {
        WebDriver driver;
        WebDriverWait wait;
        String currentURL = "https://demo.nopcommerce.com/cart";
        String displayedElements = "//table[@class='cart']/tbody/tr";
        String itemPriceXPath = "//table[@class='cart']//tbody/tr/td[@class='unit-price']//span";
        String totalPrice="//tr[@class='order-total']/td[@class=\"cart-total-right\"]//strong";
        String firstElement = "(//table[@class='cart']//tbody/tr/td[1])[1]/span";
        String deleteFirstButton = "(//button[@class='remove-btn'])[1]";
        String shoppingCartEmpty = ".no-data";
        ArrayList<WebElement> arr;

    public ShoppingCart(WebDriver driver) {
        this.driver=driver;
        wait = new WebDriverWait(this.driver, 4);
    }

    public void goToShoppingCart(){
        driver.get(currentURL);
    }

    public void checkURL(String url){
        Assertions.assertEquals(currentURL,url);
    }

    public void checkButtons(String xPath){
        arr = new ArrayList<>(driver.findElements(By.xpath(xPath)));
        for(WebElement button:arr){
            Assertions.assertEquals(true,button.isDisplayed());
        }
    }

    public int elementsDisplayed(){
        arr=new ArrayList<>(driver.findElements(By.xpath(displayedElements)));
        return arr.size();
    }


    public void checkPrice(){
        arr=new ArrayList<>(driver.findElements(By.xpath(itemPriceXPath)));
        String s;
        double sum=0.0;
        for(int i=0;i<arr.size();i++){
            s=arr.get(i).getText();
            s=s.substring(1,s.length());
            s=s.replace(",","");
            sum+=Double.parseDouble(s);
        }
        String totalExpectedPrice = driver.findElement(By.xpath(totalPrice)).getText();
        totalExpectedPrice = totalExpectedPrice.substring(1,totalExpectedPrice.length());
        totalExpectedPrice = totalExpectedPrice.replace(",","");
        double expectedPrice = Double.parseDouble(totalExpectedPrice);
        Assertions.assertEquals(sum,expectedPrice);
    }

    public void shoppingCartEmptyCheck(){
        WebElement empty=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(shoppingCartEmpty)));
    }

    public void deleteFirstElement() throws InterruptedException {
        int previousNumber = elementsDisplayed();
        String s=driver.findElement(By.xpath(firstElement)).getText();
        driver.findElement(By.xpath(deleteFirstButton)).click();
        Thread.sleep(1000);
        Assertions.assertEquals(false,inHTML(s));
        int followingNumber = elementsDisplayed();
        Assertions.assertEquals(followingNumber,previousNumber-1);
    }

    public boolean inHTML(String text){
        try{
            driver.findElement(By.xpath("//*[text()='"+text+"']"));
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

 }
