package com.reklama.lv.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumHelper {

    private final static int WAIT_TIMEOUT = 60;

    public static void waitPageLoading(WebDriver driver) {
        new WebDriverWait(driver, WAIT_TIMEOUT)
                .until((ExpectedCondition<Object>) webDriver ->
                        ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public static void moveToElement(WebDriver driver, WebElement webElement) {
        scrollToElement(driver, webElement);
        new Actions(driver).moveToElement(webElement).build().perform();
    }

    public static void waitElementToBeClickable(WebDriver driver, WebElement webElement) {
        new WebDriverWait(driver, WAIT_TIMEOUT)
                .until((ExpectedConditions.elementToBeClickable(webElement)));
    }

    public static void scrollToElement(WebDriver driver, WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); window.scrollBy(0,-100);", element);
    }
}
