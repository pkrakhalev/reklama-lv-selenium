package com.reklama.lv.pages;

import com.reklama.lv.utils.SeleniumHelper;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {

    @FindBy(xpath = "//span[not(contains(@class,'onlymobile'))]//h3")
    private List<WebElement> sections;

    @FindBy(id = "search_value")
    private WebElement searchInput;

    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        SeleniumHelper.waitPageLoading(driver);
    }

    public boolean verifySectionsAreDisplayed() {
        return sections.parallelStream().allMatch(WebElement::isDisplayed);
    }

    public CategoriesPage clickOnItemInCategory(int row) {
        sections.get(row).click();
        return new CategoriesPage(driver);
    }

    public AdsPage search(String word) {
        searchInput.sendKeys(word);
        searchInput.sendKeys(Keys.ENTER);
        return new AdsPage(driver);
    }
}
