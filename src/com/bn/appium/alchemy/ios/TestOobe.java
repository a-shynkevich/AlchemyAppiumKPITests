package com.bn.appium.alchemy.ios;
import com.bn.appium.alchemy.utils.*;
import com.bn.appium.alchemy.manager.TestManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import java.util.Date;


public class TestOobe extends TestHelper {
    private Process process;
    int maxLoadTime = Integer.parseInt(TestManager.configManager.getProperty(ConfigurationParametersEnum.MAX_LOAD_TIME.name().toString()));

    public TestOobe(AppiumDriver driver) {
        super(driver);
    }

    public void logOut() {
        WebElement webElement;
        By menuBtn = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAToolbar[1]/UIAButton[1]");
        webElement = waitForElement(menuBtn, 2000, true);
        if (webElement != null) {
            log("click \"com.bn hub hamburger menu\" button");
            webElement.click();
        }

        webElement = waitForElement(By.name("SETTINGS"), 3000, true);
        if (webElement != null) {
            log("click \"SETTINGS\" button");
            webElement.click();
        }

        webElement = waitForElement(By.name("Logout"), 3000, true);
        if (webElement != null) {
            log("click \"Logout\" button");
            webElement.click();
        }

        webElement = waitForElement(By.name("OK"), 3000, true);
        if (webElement != null) {
            log("click \"OK\" button");
            webElement.click();
        }
    }

    public void login() {
        MainConstants.TEST_NAME = MainConstants.TestType.testOobe;
        log("start testOobe");

        WebElement signInButton;
        if (TestManager.getHwDevice().toLowerCase().contains("ipad")) {
            signInButton = waitForElement(By.name("explore the app"), 10000, true);
        }else {
            signInButton = waitForElement(By.name("signIn"), 10000, true);
        }
        if (signInButton != null) {
            log("click sign in button");
            signInButton.click();
        }

        WebElement editText = waitForElement(By.className("UIATextField"), 3000, true);
        if (editText != null) {
            log("input login: " + TestManager.configManager.getProperty(ConfigurationParametersEnum.LOGIN.name()));
            editText.clear();
            editText.sendKeys(TestManager.configManager.getProperty(ConfigurationParametersEnum.LOGIN.name()));
        }

        WebElement secureEditText = waitForElement(By.className("UIASecureTextField"), 3000, true);
        if (secureEditText != null) {
            log("input pasword: *****");
            secureEditText.clear();
            secureEditText.sendKeys(TestManager.configManager.getProperty(ConfigurationParametersEnum.PASSWORD.name()) + "\n");
        }
        if (TestManager.getHwDevice().toLowerCase().contains("iphone")) {
            WebElement signInBtn = waitForElement(By.name("Sign In"), 5000, false);
            if (signInBtn != null) {
                signInBtn.click();
            }
        }
        log("press sign in");

        TestManager.startTimer();

        boolean isFirstSyncPassed = false;
        By byParent = By.className("UIACollectionView");
        WebElement uiCollectionView = waitForElement(byParent, 15000, false);
        if (uiCollectionView != null) {
            By byChild = By.className("UIACollectionCell");
            if (waitChildOfElement(byParent, byChild, TestManager.getTimeout())) {
                TestManager.stopTimer(false);
                TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FIRST_SYNC, "Sign in", true));
                isFirstSyncPassed = true;
            }
        }

        if (isFirstSyncPassed != true) {
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FIRST_SYNC, "Sign in", false));
        }

        By networkStatusBar = By.name("Network connection in progress");
        boolean isExceedWaitingOfElement = false;

        TestManager.stopTimer(false);
        while (waitForElement(networkStatusBar, 5000, true) != null) {
            if (waitWhileElementExist(networkStatusBar, 120000)) {
                TestManager.stopTimer(false);
            } else {
                isExceedWaitingOfElement = true;
                TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FULL_SYNC, "Sign in", false));
                break;
            }
        }

        if (!isExceedWaitingOfElement)
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FULL_SYNC, "Sign in", true));
    }
}
