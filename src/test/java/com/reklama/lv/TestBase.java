package com.reklama.lv;

import com.reklama.lv.utils.WebEventListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class TestBase {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    WebDriver getDriver() {
        return driver.get();
    }

    static Properties properties;
    private static long pageLoadTimeout;
    private static long implicitlyWait;
    private static String browserName;

    @BeforeAll
    static void beforeAll() {
        properties = new Properties();
        try {
            FileInputStream propertyFile = new FileInputStream("src/test/resources/application.properties");
            properties.load(propertyFile);
        } catch (IOException e) {
            System.out.println("Could not load properties: " + e);
        }

        browserName = properties.getProperty("BROWSER");
        pageLoadTimeout = Long.valueOf(properties.getProperty("PAGE_LOAD_TIMEOUT"));
        implicitlyWait = Long.valueOf(properties.getProperty("IMPLICITLY_WAIT"));
    }

    @BeforeEach
    void setDriver() {

        WebDriver newDriver = null;
        if (browserName.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            newDriver = new ChromeDriver();
        } else if (browserName.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            newDriver = new FirefoxDriver();
        }


        EventFiringWebDriver eventDriver = new EventFiringWebDriver(newDriver);
        eventDriver.register(new WebEventListener());
        driver.set(eventDriver);

        WebDriver.Timeouts timeouts = getDriver().manage().timeouts();
        timeouts.pageLoadTimeout(pageLoadTimeout, TimeUnit.SECONDS);
        timeouts.implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
    }


    @AfterEach
    void cleanUp() {
        getDriver().quit();
    }
}
