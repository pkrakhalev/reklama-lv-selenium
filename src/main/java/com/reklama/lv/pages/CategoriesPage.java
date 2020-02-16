package com.reklama.lv.pages;

import com.reklama.lv.utils.SeleniumHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CategoriesPage {

    @FindBy(xpath = "//fieldset[@class='cats']//h2//a")
    private List<WebElement> categories;

    private WebDriver driver;

    public CategoriesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        SeleniumHelper.waitPageLoading(driver);
    }

    public boolean verifyCategoriesAreDisplayed() {
        return categories.parallelStream().allMatch(WebElement::isDisplayed);
    }

    public AdsPage clickOnItem(int row) {
        categories.get(row).click();
        return new AdsPage(driver);
    }
}
