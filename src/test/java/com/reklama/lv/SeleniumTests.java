package com.reklama.lv;

import com.reklama.lv.objects.Ad;
import com.reklama.lv.pages.AdsPage;
import com.reklama.lv.pages.CategoriesPage;
import com.reklama.lv.pages.FavoritePage;
import com.reklama.lv.pages.MainPage;
import com.reklama.lv.utils.RandomHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@Execution(ExecutionMode.CONCURRENT)
public class SeleniumTests extends TestBase {

    @Test
    public void givenMainPageWhenSelectCategoryThenAdsAreShown() {
        getDriver().get(properties.getProperty("MAIN_PAGE_URL"));
        MainPage mainPage = new MainPage(getDriver());
        assertTrue(mainPage.verifySectionsAreDisplayed());

        CategoriesPage categoriesPage = mainPage.clickOnItemInCategory(3);
        assertTrue(categoriesPage.verifyCategoriesAreDisplayed());

        AdsPage adsPage = categoriesPage.clickOnItem(1);
        assertTrue(adsPage.verifyOrdersAreDisplayed());
    }

    @Test
    public void givenAdsPageWhenMakeFavoriteThenFavoritesAreUpdated() {
        getDriver().get(properties.getProperty("MAIN_PAGE_URL") + "/job_and_studies/finances/accounting/uslugi/table.html");
        AdsPage adsPage = new AdsPage(getDriver());
        assertTrue(adsPage.verifyOrdersAreDisplayed());

        assertEquals(0, adsPage.getFavoriteCount(), "Favorite count is not zero");

        adsPage.addOrderToFavorite(1);
        assertEquals(1, adsPage.getFavoriteCount(), "Favorite count is not increased");

        adsPage.removeFromFavorite(1);
        assertEquals(0, adsPage.getFavoriteCount(), "Favorite count is not decreased");

        Ad ad1 = adsPage.addOrderToFavorite(2);
        Ad ad2 = adsPage.addOrderToFavorite(3);
        Ad ad3 = adsPage.addOrderToFavorite(4);
        assertEquals(3, adsPage.getFavoriteCount(), "Favorite count is not increased");

        FavoritePage favoritePage = adsPage.clickOnFavoriteLink();
        assertTrue(getDriver().getCurrentUrl().contains("favorites"), "Url doesn't correct: " + getDriver().getCurrentUrl());
        assertEquals(3, favoritePage.getAdsCount());
        assertTrue(favoritePage.verifyIsAdVisible(ad1));
        assertTrue(favoritePage.verifyIsAdVisible(ad2));
        assertTrue(favoritePage.verifyIsAdVisible(ad3));

        favoritePage.removeFromFavorite(0);
        favoritePage.removeFromFavorite(1);
        //if remove all from favorites, then back to internalAds link will be broken
        //favoritePage.removeFromFavorite(2);

        adsPage = favoritePage.clickOnBackLink();
        assertFalse(getDriver().getCurrentUrl().contains("favorites"), "Url doesn't correct: " + getDriver().getCurrentUrl());
        assertEquals(1, adsPage.getFavoriteCount(), "Favorite count is not decreased");
    }

    @Test
    public void givenSearchResultsWhenAddToFavoriteRandomAdThenFavoriteContainsOrders() {
        getDriver().get(properties.getProperty("MAIN_PAGE_URL"));
        MainPage mainPage = new MainPage(getDriver());
        AdsPage adsPage = mainPage.search("house");
        assertTrue(adsPage.verifyOrdersAreDisplayed());

        int adsCount = adsPage.getAdsCount();

        Iterator<Integer> integers = RandomHelper.generateUniqueRandomInt(2, adsCount).iterator();
        Ad ad1 = adsPage.addOrderToFavorite(integers.next());
        Ad ad2 = adsPage.addOrderToFavorite(integers.next());
        assertEquals(2, adsPage.getFavoriteCount(), "Favorite count is not increased");

        FavoritePage favoritePage = adsPage.clickOnFavoriteLink();
        assertTrue(getDriver().getCurrentUrl().contains("favorites"), "Url doesn't correct: " + getDriver().getCurrentUrl());
        assertEquals(2, favoritePage.getAdsCount());
        assertTrue(favoritePage.verifyIsAdVisible(ad1));
        assertTrue(favoritePage.verifyIsAdVisible(ad2));
    }

    @Test
    public void givenSearchResultsWhenExternalAdThenUnableAddToFavorite() {
        getDriver().get(properties.getProperty("MAIN_PAGE_URL"));
        MainPage mainPage = new MainPage(getDriver());
        AdsPage adsPage = mainPage.search("house");
        assertTrue(adsPage.verifyOrdersAreDisplayed());

        assertTrue(adsPage.verifyIsExternalAdsDontHaveFavoriteIcon(), "Favorite icon is visible for external ad");
    }

}
