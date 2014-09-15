package com.bn.appium.alchemy.ios;

import com.bn.appium.alchemy.utils.*;
import com.bn.appium.alchemy.manager.TestManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import java.util.Date;

public class TestOobe extends TestHelper {
    private Process process;

    public TestOobe(AppiumDriver driver) {
        super(driver);
    }

    private Point getCenter(WebElement element) {

        Point upperLeft = element.getLocation();
        Dimension dimensions = element.getSize();
        return new Point(upperLeft.getX() + dimensions.getWidth()/2, upperLeft.getY() + dimensions.getHeight()/2);
    }



    public void logOut() {
        WebElement webElement;
        By menuBtn = By.xpath("//UIAApplication[1]/UIAWindow[1]/UIAToolbar[1]/UIAButton[1]");
        webElement = waitForElement(menuBtn, 2000);
        if(webElement != null) {
            log("click \"com.bn hub hamburger menu\" button");
            webElement.click();
        }

        webElement = waitForElement(By.name("SETTINGS"), 3000);
        if(webElement != null) {
            log("click \"SETTINGS\" button");
            webElement.click();
        }

        webElement = waitForElement(By.name("Logout"), 3000);
        if(webElement != null) {
            log("click \"Logout\" button");
            webElement.click();
        }

        webElement = waitForElement(By.name("OK"), 3000);
        if(webElement != null) {
            log("click \"OK\" button");
            webElement.click();
        }
    }

    public void login() throws Exception {
        MainConstants.TEST_NAME = MainConstants.TestType.testOobe;
        log("start testOobe");

        WebElement signInButton = waitForElement(By.name("signIn"),3000);
        if (signInButton != null){
            log("click sign in button");
            signInButton.click();
        }

        WebElement editText = waitForElement(By.className("UIATextField"), 3000);
        if (editText != null) {
            log("input login: " + TestManager.configManager.getProperty(ConfigurationParametersEnum.LOGIN.name()));
            editText.sendKeys(TestManager.configManager.getProperty(ConfigurationParametersEnum.LOGIN.name()));
        }

        WebElement secureEditText = waitForElement(By.className("UIASecureTextField"), 3000);
        if (secureEditText != null) {
            log("input pasword: *****");
            secureEditText.sendKeys(TestManager.configManager.getProperty(ConfigurationParametersEnum.PASSWORD.name()) + "\n");
        }

        if (TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE.name()).toLowerCase().contains("iphone")) {
            WebElement signInBtn = waitForElement(By.name("Sign In"), 3000);
            if (signInBtn != null) {
                signInBtn.click();
            }
        }
        log("press sign in");

        TestManager.startTimer();

        WebElement freeSample = waitForElement(By.name("Free Sample"), 120000);
        if(freeSample != null){
            TestManager.stopTimer(false);
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FIRST_SYNC, Constant.Account.ACCOUNT, true));
        }else {
            log("\"Free Sample\" button is not found");
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FIRST_SYNC, Constant.Account.ACCOUNT, false));
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
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.FULL_SYNC, Constant.Account.ACCOUNT, true));
    }


}
