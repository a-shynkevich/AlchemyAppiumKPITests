package com.bn.appium.alchemy.ios;

import com.bn.appium.alchemy.manager.TestManager;
import com.bn.appium.alchemy.utils.ConfigurationParametersEnum;
import com.bn.appium.alchemy.utils.MainConstants;
import com.bn.appium.alchemy.utils.TestHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Date;
import java.util.List;

/**
 * Created by ashynkevich on 9/15/14.
 */
public class TestOpenBook extends TestHelper {

    public TestOpenBook(AppiumDriver driver) {
        super(driver);
    }

    public void loadBook() {
        MainConstants.TEST_NAME = MainConstants.TestType.testOpenBook;
        log("start testOpenBook");

        WebElement webElement;

        By menuBtn = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAToolbar[1]/UIAButton[1]");
        webElement = waitForElement(menuBtn, 5000, true);
        if (webElement != null) {
            log("click \"com.bn hub hamburger menu\" button");
            webElement.click();
        }

        webElement = waitForElement(By.name("Library"), 5000, true);
        if (webElement != null) {
            log("open Library screen");
            webElement.click();
        }

        By searchField = By.className("UIASearchBar");
        if (TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE.name().toLowerCase()).contains("ipad")) {
            webElement = waitForElement(searchField, 10000, true);
            if (webElement != null) {
                log("enter book name into \"Search\" field.");
                webElement.clear();
                webElement.sendKeys(TestManager.configManager.getProperty(ConfigurationParametersEnum.LOAD_BOOK_NAME.name()));
            }
        } else {
            webElement = waitForElement(By.name("Search"), 10000, true);
            if (webElement != null) {
                webElement.click();
                webElement = waitForElement(searchField, 10000, true);
                log("enter book name into \"Search\" field.");
                webElement.clear();
                webElement.sendKeys(TestManager.configManager.getProperty(ConfigurationParametersEnum.LOAD_BOOK_NAME.name()));
            }
        }

        if (TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE.name().toLowerCase()).contains("ipad")) {
            webElement = waitForElement(By.name("Search"), 5000, true);
        }else {
            webElement = waitForElement(By.name("Search"), 1, 5000);
        }
        if (webElement != null) {
            log("click Search");
            webElement.click();
            TestManager.startTimer();
        }

        driver.hideKeyboard();

        By allListOfBooks = By.className("UIACollectionCell");
        webElement = waitForElement(allListOfBooks, 0, 10000);
        if (webElement != null) {
            new TouchAction(driver).longPress(webElement).perform();
        }

        webElement = waitForElement(By.name("Download"), 5000, true);
        if (webElement != null) {
            log("start book loading");
            webElement.click();
            TestManager.startTimer();
        }

        webElement = waitForElement(By.name("Read"), 120000, true);
        if (webElement != null) {
            TestManager.stopTimer(false);
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.DOWNLOAD, Constant.Account.ACCOUNT, true));
        } else {
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.DOWNLOAD, Constant.Account.ACCOUNT, false));
        }
    }


    public void readBook() {
        MainConstants.TEST_NAME = MainConstants.TestType.testOpenBook;

        WebElement webElement = null;
        webElement = waitForElement(By.name("Read"), 5000, true);
        if (webElement != null) {
            log("click on the \"Read\" button.");
            webElement.click();
            TestManager.startTimer();
        }

        webElement = waitForElement(By.name("Back to library"), By.className("UIAAlert"), 120000, true);
        if (webElement != null) {
            TestManager.stopTimer(false);
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.READ_BOOK, Constant.Account.ACCOUNT, true));
        } else {
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.READ_BOOK, Constant.Account.ACCOUNT, false));
        }
    }

    public void goToHome() {
        WebElement webElement = null;

        if ((webElement = waitForElement(By.className("UIAAlert"), 5000, true)) != null) {
            webElement = waitForElement(By.name("OK"), 5000, true);
            if (webElement != null) {
                log("click OK button.");
                webElement.click();
            }
        }

        webElement = waitForElement(By.name("Back to library"), 120000, true);
        if (webElement != null) {
            webElement.click();
        }

        sleep(3000);

        if (TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE.name().toLowerCase()).contains("ipad")) {
            webElement = waitForElement(By.className("UIAButton"), 5, 5000);
            if (webElement != null) {
                log("Close search.");
                webElement.click();
            }
        }
        else {
            webElement = waitForElement(By.name("Cancel"), 5000, true);
            if (webElement != null) {
                log("Close search.");
                webElement.click();
            }
        }

        By menuBtn = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAToolbar[2]/UIAButton[1]");
        webElement = waitForElement(menuBtn, 5000, true);
        if (webElement != null) {
            log("click \"com.bn hub hamburger menu\" button");
            webElement.click();
        }

        webElement = waitForElement(By.name("Home"), 5000, true);
        if (webElement != null) {
            log("open Library screen");
            webElement.click();
        }
    }
}



