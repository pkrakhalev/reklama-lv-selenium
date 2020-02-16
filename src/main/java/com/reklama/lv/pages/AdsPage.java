package com.reklama.lv.pages;

import com.reklama.lv.objects.Ad;
import com.reklama.lv.utils.SeleniumHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class AdsPage {

    @FindBy(xpath = "//div[@class='view' and not(contains(@style,'none'))]//tr[contains(@onclick,'openCardLink') and not(.//div[@class='external-source'])]")
    protected List<WebElement> internalAds;

    @FindBy(xpath = "//div[@class='view' and not(contains(@style,'none'))]//tr[contains(@onclick,'openCardLink') and .//div[@class='external-source']]")
    protected List<WebElement> externalAds;

    @FindBy(xpath = "//tr[contains(@class,'over')]//a[@class='fav-add']")
    protected WebElement addToFavoriteIcon;

    @FindBy(xpath = "//tr[contains(@class,'over')]//a[@class='fav-remove']")
    protected WebElement removeFromFavoriteIcon;

    @FindBy(xpath = "//tr[contains(@class,'over')]//a[contains(@class,'fav')]")
    protected List<WebElement> favoriteIcons;

    @FindBy(id = "favorites-link")
    private WebElement favoriteLink;

    @FindBy(id = "favorites_count")
    private WebElement favoriteCount;

    private WebDriver driver;

    public AdsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        SeleniumHelper.waitPageLoading(driver);
    }

    public boolean verifyOrdersAreDisplayed() {
        return internalAds.parallelStream().allMatch(WebElement::isDisplayed);
    }

    public int getFavoriteCount() {
        String count = favoriteCount.getText();
        return count.isEmpty() ? 0 : Integer.parseInt(count);
    }

    public Ad addOrderToFavorite(int row) {
        System.out.println("Add to favorite: " + row);
        WebElement item = internalAds.get(row);
        SeleniumHelper.moveToElement(driver, item);
        addToFavoriteIcon.click();
        SeleniumHelper.waitElementToBeClickable(driver, removeFromFavoriteIcon);

        return new Ad(item);
    }

    public void removeFromFavorite(int row) {
        SeleniumHelper.moveToElement(driver, internalAds.get(row));
        removeFromFavoriteIcon.click();
        SeleniumHelper.waitElementToBeClickable(driver, addToFavoriteIcon);
    }

    public FavoritePage clickOnFavoriteLink() {
        favoriteLink.click();
        return new FavoritePage(driver);
    }

    public int getAdsCount() {
        return internalAds.size();
    }

    public boolean verifyIsAdVisible(Ad ad) {
        return internalAds.parallelStream().map(Ad::new).anyMatch(item -> item.equals(ad));
    }

    public boolean verifyIsExternalAdsDontHaveFavoriteIcon() {
        return externalAds.stream().allMatch(ad -> {
            SeleniumHelper.moveToElement(driver, ad);
            return favoriteIcons.size() == 0;
        });
    }
}
