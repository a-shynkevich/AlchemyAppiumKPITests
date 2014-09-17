package com.bn.appium.alchemy;

import com.bn.appium.alchemy.ios.AllKPITests;
import com.bn.appium.alchemy.ios.TestOpenBook;
import com.bn.appium.alchemy.ios.TestOobe;
import com.bn.appium.alchemy.ios.TestSearch;
import com.bn.appium.alchemy.manager.TestManager;
import com.bn.appium.alchemy.utils.ConfigurationParametersEnum;
import com.bn.appium.alchemy.utils.MainConstants;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;

/**
 * Created by nikolai on 25.07.2014.
 */
public class Main {

    private static AppiumDriver driver;
    private static TestManager testManager;
    private static void setUp() throws Exception {
        testManager = TestManager.getInstance();

        Thread.sleep(5000);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        File appDir = new File(TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_APP_DIR.name()));
        File app = new File(appDir, TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_APP.name()));
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformVersion", TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_PLATFORM_VERSION.name()));
        capabilities.setCapability("platformName", TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_PLATFORM_NAME.name()));
        String deviceName = TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE.name());
        capabilities.setCapability("deviceName",deviceName);
        if(!deviceName.toLowerCase().contains("simulator")){
            capabilities.setCapability("udid", TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE_ID.name()));
        }
//        capabilities.setCapability("bundleid", configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE_ID.name()));
        capabilities.setCapability("app",app.getAbsolutePath());

        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        MainConstants.TIME_START_TEST = System.currentTimeMillis();
        MainConstants.FILE_NAME_LOG_TESTS = "iOs.csv";
        TestManager.setDriver(driver);
    }

    public static void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }

    }

    public static void main(String[] args) throws Exception {
        setUp();

        String testType = args[0].toString().toLowerCase();

        switch (TestManager.detectTestType(testType)) {
            case MainConstants.TestType.Kpi.ALL_KPI_TESTS:
                (new AllKPITests(driver)).runAllKpiTests();
                break;
            case MainConstants.TestType.Kpi.TEST_OOBE:
                (new TestOobe(driver)).login();
                break;
            case MainConstants.TestType.Kpi.TEST_SEARCH:
                (new TestSearch(driver)).searchBook();
                (new TestSearch(driver)).goToHome();
                break;
            case MainConstants.TestType.Kpi.TEST_OPEN_BOOK:
                (new TestOpenBook(driver)).loadBook();
                (new TestOpenBook(driver)).readBook();
                (new TestOpenBook(driver)).goToHome();
                break;
        }

        tearDown();
    }
}
