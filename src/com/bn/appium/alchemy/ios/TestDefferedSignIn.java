package com.bn.appium.alchemy.ios;

import com.bn.appium.alchemy.manager.TestManager;
import com.bn.appium.alchemy.utils.ConfigurationParametersEnum;
import com.bn.appium.alchemy.utils.MainConstants;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Date;

/**
 * Created by ashynkevich on 10/9/14.
 */
public class TestDefferedSignIn extends TestOobe {
    TestSearch testSearch;

    public TestDefferedSignIn(AppiumDriver driver) {
        super(driver);
        testSearch = new TestSearch(driver);
    }

    public void start(){
        login();
        openCollections();
    }
    @Override
        public void login() {
        MainConstants.TEST_NAME = MainConstants.TestType.testDefferedSignIn;
        log("start testDefferedSignIn");

        WebElement exploreAppBtn;
        if (TestManager.getHwDevice().toLowerCase().contains("ipad")) {
            exploreAppBtn = waitForElement(By.name("explore the app"), 1, 10000, true);
        } else {
            exploreAppBtn = waitForElement(By.name("explore the app"), 10000, true);
        }
        if (exploreAppBtn != null) {
            log("click \"explore the app\"");
            exploreAppBtn.click();
        }
    }

    public void openCollections(){
        testSearch.openMenu();
        testSearch.openMenuTab("Collections");
    }
}
