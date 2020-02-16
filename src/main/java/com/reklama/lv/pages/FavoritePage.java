package com.reklama.lv.pages;

import com.reklama.lv.utils.SeleniumHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FavoritePage extends AdsPage {

    @FindBy(xpath = "//*[contains(@class,'back-2')]//a")
    private WebElement backToAdsLink;


    private WebDriver driver;

    public FavoritePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        SeleniumHelper.waitPageLoading(driver);
    }

    public AdsPage clickOnBackLink() {
        backToAdsLink.click();
        return new AdsPage(driver);
    }

    @Override
    public void removeFromFavorite(int row) {
        SeleniumHelper.moveToElement(driver, internalAds.get(row));
        removeFromFavoriteIcon.click();
    }
}
