package com.bn.appium.alchemy.ios;

import com.bn.appium.alchemy.manager.TestManager;
import com.bn.appium.alchemy.utils.ConfigurationParametersEnum;
import com.bn.appium.alchemy.utils.MainConstants;
import com.bn.appium.alchemy.utils.TestHelper;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Date;

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
        webElement = waitForElement(menuBtn,5000);
        if (webElement != null){
            log("click \"com.bn hub hamburger menu\" button");
            webElement.click();
        }

        webElement = waitForElement(By.name("Library"), 5000);
        if(webElement != null) {
            log("open Library screen");
            webElement.click();
        }

        By searchField = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAToolbar[2]/UIASearchBar[1]");
        webElement = getWebElement(searchField);
        if(webElement != null) {
            log("enter book name into \"Search\" field.");
            webElement.sendKeys(TestManager.configManager.getProperty(ConfigurationParametersEnum.BOOK_NAME.name()));
        }

        webElement = waitForElement(By.name("Search"), 5000);
        if(webElement != null) {
            webElement.click();
            TestManager.startTimer();
        }

        By networkStatusBar = By.name("Network connection in progress");
        boolean isExceedWaitingOfElement = false;

        while (waitForElement(networkStatusBar, 5000) != null){
            if (waitWhileElementExist(networkStatusBar, 120)){
                TestManager.stopTimer(false);
            }else {
                isExceedWaitingOfElement = true;
                TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FULL_SYNC, Constant.Account.ACCOUNT, false));
                break;
            }
        }
        if (!isExceedWaitingOfElement)
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.SEARCH, Constant.Account.ACCOUNT, true));
    }

}
