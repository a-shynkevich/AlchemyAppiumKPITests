package com.bn.appium.alchemy.ios;

import com.bn.appium.alchemy.manager.TestManager;
import com.bn.appium.alchemy.utils.ConfigurationParametersEnum;
import com.bn.appium.alchemy.utils.MainConstants;
import com.bn.appium.alchemy.utils.TestHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.json.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.util.Date;
import java.util.List;

/**
 * Created by ashynkevich on 9/15/14.
 */
public class TestSearch extends TestHelper {
    String searchBook;
    By byChild;
    public TestSearch(AppiumDriver driver) {
        super(driver);

    }

    int maxLoadTime = Integer.parseInt(TestManager.configManager.getProperty(ConfigurationParametersEnum.MAX_LOAD_TIME.name().toString()));

    public boolean searchBook(String searchBook) {
        MainConstants.TEST_NAME = MainConstants.TestType.testSearch;
        log("start testSearch");

        openMenu();
        openMenuTab("Library");

        WebElement webElement;

        By searchField = By.className("UIASearchBar");
        if (TestManager.getHwDevice().toLowerCase().contains("ipad")) {
            webElement = waitForElement(searchField, 0, 10000, false);
            if (webElement != null) {
                log("enter book name into \"Search\" field.");
                webElement.clear();
                webElement.sendKeys(searchBook);
            }
        }else {
            webElement = waitForElement(By.name("Search"), 10000, false);
            if (webElement != null) {
                Point point = getCenter(webElement);
                new TouchAction(driver).tap(point.getX(), point.getY()-10).perform();

                webElement = waitForElement(By.className("UIASearchBar"), 0, 5000, false);
                if (webElement != null) {
                    log("enter book name into \"Search\" field.");
                    webElement.clear();
                    webElement.sendKeys(searchBook);
                }
            }
        }

        if (TestManager.getHwDevice().toLowerCase().contains("ipad")) {
            webElement = waitForElement(By.name("Search"), 5000, true);
        }else {
            webElement = waitForElement(By.name("Search"), 1, 5000, true);
        }
        if (webElement != null) {
            log("click Search");
            webElement.click();
            TestManager.startTimer();
        }

        boolean isFirstSyncPassed = false;
        By byParent = By.className("UIACollectionView");
        WebElement uiCollectionView = waitForElement(byParent, 15000, false);
        if (uiCollectionView != null){
             byChild = By.className("UIACollectionCell");
            if(waitChildOfElement(byParent, byChild, 20000)){
                TestManager.stopTimer(false);
                TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FIRTS_SEARCH_SYNC, searchBook, true));
                isFirstSyncPassed = true;
            }
        }

        if (isFirstSyncPassed != true){
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FIRTS_SEARCH_SYNC, searchBook, false));
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FULL_SEARCH_SYNC, searchBook, false));
            returnDefaultState();
            return false;
        }


        boolean isExceedWaitingOfElement = false;
        By networkStatusBar = By.name("Network connection in progress");

        TestManager.stopTimer(false);
        while (waitForElement(networkStatusBar, 5000, true) != null) {
            if (waitWhileElementExist(networkStatusBar, TestManager.getTimeout())) {
                TestManager.stopTimer(false);
            } else {
                isExceedWaitingOfElement = true;
                TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FULL_SEARCH_SYNC, searchBook, false));
                return false;
            }
        }

        if (!isExceedWaitingOfElement) {
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FULL_SEARCH_SYNC, searchBook, true));
        }
        return true;
    }

    public void openMenuTab(String tabName) {
        WebElement webElement;

        if (TestManager.getHwDevice().toLowerCase().contains("ipad")) {
            webElement = waitForElement(By.name(tabName), 5000, true);
        }else {
            webElement = waitForElement(By.name(tabName), 5000, false);

        }
        if (webElement != null) {
            log("open " + tabName + " screen");
            webElement.click();
            sleep(3000);
        }
    }

    public void openMenu() {
        WebElement webElement;
        if (TestManager.getHwDevice().toLowerCase().contains("ipad")) {
            webElement = waitForElement(By.name("com.bn hub hamburger menu"), 0, 15000, true);
            if (webElement == null){
                log("\"com.bn hub hamburger menu\" button == NULL");
            }else {
                log("click \"com.bn hub hamburger menu\" button");
                webElement.click();
            }
        }else{
            webElement = waitForElement(By.name("com.bn hub hamburger menu"), 0, 15000, false);
            Point point = getCenter(webElement);
            new TouchAction(driver).tap(point.getX(), point.getY()).perform();
        }
    }

    public void returnDefaultState() {
        WebElement webElement = null;

        if (TestManager.getHwDevice().toLowerCase().contains("ipad")) {
            webElement = waitForElement(By.className("UIAButton"), 7, 5000, true);
            if (webElement != null) {
                log("Close search.");
                webElement.click();
            }
        } else {
            webElement = waitForElement(By.name("Cancel"), 1, 5000, true);
            if (webElement != null) {
                log("Close search.");
                webElement.click();
            }
        }
    }
}
