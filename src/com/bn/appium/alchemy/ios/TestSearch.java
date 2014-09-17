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
public class TestSearch extends TestHelper {
    public TestSearch(AppiumDriver driver) {
        super(driver);
    }

    public void searchBook() {
        MainConstants.TEST_NAME = MainConstants.TestType.testSearch;
        log("start testSearch");

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
                webElement.sendKeys(TestManager.configManager.getProperty(ConfigurationParametersEnum.BOOK_NAME.name()));
            }
        } else {
            webElement = waitForElement(By.name("Search"), 10000, true);
            if (webElement != null) {
                webElement.click();
                webElement = waitForElement(searchField, 10000, true);
                if (webElement != null) {
                    log("enter book name into \"Search\" field.");
                    webElement.clear();
                    webElement.sendKeys(TestManager.configManager.getProperty(ConfigurationParametersEnum.BOOK_NAME.name()));
                }
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

        boolean isExceedWaitingOfElement = false;
        By networkStatusBar = By.name("Network connection in progress");

        TestManager.stopTimer(false);
        while (waitForElement(networkStatusBar, 5000, true) != null) {
            if (waitWhileElementExist(networkStatusBar, 120000)) {
                TestManager.stopTimer(false);
            } else {
                isExceedWaitingOfElement = true;
                TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FULL_SYNC, Constant.Account.ACCOUNT, false));
                break;
            }
        }

        if (!isExceedWaitingOfElement) {
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.SEARCH, Constant.Account.ACCOUNT, true));
        }

    }

    public void goToHome() {
        WebElement webElement = null;

        if (TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE.name().toLowerCase()).contains("ipad")) {
            webElement = waitForElement(By.className("UIAButton"), 7, 5000);
            if (webElement != null) {
                log("Close search.");
                webElement.click();
            }
        } else {
            webElement = waitForElement(By.name("Cancel"), 1, 5000);
            if (webElement != null) {
                log("Close search.");
                webElement.click();
            }
        }

        By menuBtn = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAToolbar[2]/UIAButton[1]");
        webElement = waitForElement(menuBtn, 2000, true);
        if (webElement != null) {
            log("click \"com.bn hub hamburger menu\" button");
            webElement.click();
        }

        webElement = waitForElement(By.name("Home"), 5000, true);
        if (webElement != null) {
            log("open Home screen");
            webElement.click();
        }
    }
}
