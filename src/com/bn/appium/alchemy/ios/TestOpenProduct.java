package com.bn.appium.alchemy.ios;

import com.bn.appium.alchemy.manager.TestManager;
import com.bn.appium.alchemy.utils.ConfigurationParametersEnum;
import com.bn.appium.alchemy.utils.MainConstants;
import com.bn.appium.alchemy.utils.TestHelper;
import com.bn.appium.alchemy.utils.TestParameter;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.*;

import java.util.Date;
import java.util.List;

/**
 * Created by ashynkevich on 9/15/14.
 */
public class TestOpenProduct extends TestHelper {
    TestParameter testParameter = null;
    int maxLoadTime = Integer.parseInt(TestManager.configManager.getProperty(ConfigurationParametersEnum.MAX_LOAD_TIME.name().toString()));

    public TestOpenProduct(AppiumDriver driver, TestParameter testParameter) {
        super(driver);
        this.testParameter = testParameter;
    }

    public static class Constants {
        public static class Reader {
            public static final int EUPB = 0;
            public static final int DRP = 1;
            public static final int VIDEO = 2;
        }
    }

    TestSearch testSearch = new TestSearch(driver);

    public void start() {
        if (testSearch.searchBook(testParameter.getProductName()) == false){
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.DOWNLOAD, testParameter.getProductSize(), false));
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.COLD_OPEN, testParameter.getProductSize(), false));
        }else {
            if (download() == false){
                TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.COLD_OPEN, testParameter.getProductSize(), false));
                returnDefaultFromManegerBook();
            }else {
                coldOpen();
                returnDefaultState();
            }
        }
    }

    private boolean download() {
        MainConstants.TEST_NAME = MainConstants.TestType.testOpenBook;
        log("start testOpenProduct");
        WebElement webElement;

        driver.hideKeyboard();

        By allListOfBooks = By.className("UIACollectionCell");
        webElement = waitForElement(allListOfBooks, 0, 10000, true);
        if (webElement != null) {
            log("long press on the first element.");
            new TouchAction(driver).longPress(webElement).perform();
        }

        webElement = waitForElement(By.name("Download"), 6000, true);
        if (webElement != null) {
            log("start book loading");
            webElement.click();
            TestManager.startTimer();
        }else {
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.DOWNLOAD, testParameter.getProductSize(), false));
            return false;
        }

        webElement = waitForElement(By.name("Read"), maxLoadTime, true);
        if (webElement != null) {
            TestManager.stopTimer(false);
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.DOWNLOAD, testParameter.getProductSize(), true));
        } else {
            TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.DOWNLOAD, testParameter.getProductSize(), false));
            return false;
        }
        return true;
    }


    private void coldOpen() {
        MainConstants.TEST_NAME = MainConstants.TestType.testOpenBook;

        WebElement webElement = null;
        webElement = waitForElement(By.name("Read"), 5000, true);
        if (webElement != null) {
            log("click on the \"Read\" button.");
            webElement.click();
            TestManager.startTimer();
        }
        switch (testParameter.getReaderType()) {
            case Constants.Reader.EUPB: {
                webElement = waitForElement(By.name("Back to library"), By.className("UIAAlert"), maxLoadTime, true);
                if (webElement != null) {
                    TestManager.stopTimer(false);
                    TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.COLD_OPEN, testParameter.getProductSize(), true));
                } else {
                    TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.COLD_OPEN, testParameter.getProductSize(), false));
                }
                break;
            }
            case Constants.Reader.DRP: {
                webElement = waitForElement(By.name("pageSlider"), maxLoadTime, true);
                if (webElement != null) {
                    TestManager.stopTimer(false);
                    TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.COLD_OPEN, testParameter.getProductSize(), true));
                } else {
                    TestManager.write(TestManager.addLogParams(new Date(), MainConstants.TestType.Kpi.TestAction.COLD_OPEN, testParameter.getProductSize(), false));
                }
                break;
            }
        }
    }

    private void returnDefaultState() {

        WebElement webElement = null;
        switch (testParameter.getReaderType()) {
            case (Constants.Reader.EUPB): {
                if ((webElement = waitForElement(By.className("UIAAlert"), 5000, true)) != null) {
                    webElement = waitForElement(By.name("OK"), 5000, true);
                    if (webElement != null) {
                        log("click OK button.");
                        webElement.click();
                    }
                }
                webElement = waitForElement(By.name("Back to library"), 5000, true);
                if (webElement != null) {
                    webElement.click();
                }
                break;
            }
            case (Constants.Reader.DRP): {
                sleep(3000);
                WebElement window = waitForElement(By.className("UIAWindow"), 5000, false);

                Point point = getCenter(window);
                new TouchAction(driver).tap(point.getX(), point.getY()).perform();
                By byChild = By.className("UIAButton");
                if(TestManager.getHwDevice().toLowerCase().contains("ipad")) {
                    webElement = waitElementByLabel(byChild, "library", 15000);
                    if (webElement != null) {
                        log("click \"Back\" button");
                        webElement.click();
                    }
                }else {

                    List<WebElement> elements = findElements(By.name("library"));
                    for (int i = 0; i<elements.size(); i++){
                        log(i+" element"+elements.get(i).getText());
                    }
                    webElement = waitForElement(By.name("library"), 5000, true);
                    if (webElement != null) {
                        log("click \"Back\" button");
                        webElement.click();
                    }
                }
                break;
            }
        }

        if (TestManager.getHwDevice().toLowerCase().contains("ipad")) {
            By searchField = By.className("UIASearchBar");
            webElement = waitForElement(searchField, 10000, true);
            if (webElement != null) {
                log("CLear search.");
                webElement.clear();
            }
        } else {
            webElement = waitForElement(By.name("Cancel"), 5000, true);
            if (webElement != null) {
                log("Close search.");
                webElement.click();
            }
        }
    }
    private void returnDefaultFromManegerBook() {

        WebElement webElement = null;

        webElement = waitForElement(By.name("Close"), 6000, true);
        if(webElement != null){
            log("Click Close.");
            webElement.click();
        }

        if (TestManager.getHwDevice().toLowerCase().contains("ipad")) {
            By searchField = By.className("UIASearchBar");
            webElement = waitForElement(searchField, 3, 6000, true);
            if (webElement != null) {
                log("CLear search.");
                webElement.clear();
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


