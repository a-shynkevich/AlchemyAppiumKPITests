package com.bn.appium.alchemy;

import com.bn.appium.alchemy.ios.TestAllKpiBooks;
import com.bn.appium.alchemy.ios.TestOpenProduct;
import com.bn.appium.alchemy.ios.TestOobe;
import com.bn.appium.alchemy.ios.TestSearch;
import com.bn.appium.alchemy.manager.TestManager;
import com.bn.appium.alchemy.utils.ConfigurationParametersEnum;
import com.bn.appium.alchemy.utils.InstallerTestParams;
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


        DesiredCapabilities capabilities = new DesiredCapabilities();
        File appDir = new File(TestManager.configManager.getProperty(ConfigurationParametersEnum.BUILD_DIR.name()));
        File app = new File(appDir, TestManager.getBuildName());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformVersion", TestManager.getOsDevice());
        capabilities.setCapability("platformName", TestManager.getOs_system());
        String deviceName = TestManager.getHwDevice();
        capabilities.setCapability("deviceName", deviceName);
        if(!deviceName.toLowerCase().contains("simulator")){
            capabilities.setCapability("udid", TestManager.getDeviceId());
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

            testManager =TestManager.getInstance(args);

        setUp();
        MainConstants.TIME_START_TEST = System.currentTimeMillis();
        switch (TestManager.detectTestType(MainConstants.TEST_NAME)) {
            case MainConstants.TestType.Kpi.ALL_KPI_TESTS:
//                (new TestAllKpiBooks(driver)).runAllKpiTests();
                break;
            case MainConstants.TestType.Kpi.TEST_OOBE:
                (new TestOobe(driver)).login();
                break;
            case MainConstants.TestType.Kpi.TEST_SEARCH:
                (new TestSearch(driver)).searchBook(TestManager.configManager.getProperty(ConfigurationParametersEnum.SEARCH_BOOK_NAME.name()));
                (new TestSearch(driver)).returnDefaultState();
                break;
            case MainConstants.TestType.Kpi.TEST_OPEN_BOOK:
                InstallerTestParams bookInstaller = new InstallerTestParams();
                bookInstaller.setParametersOpenBook();
                TestOpenProduct testOpenBook = new TestOpenProduct(driver, bookInstaller.getTestParameter());
                testOpenBook.start();
                break;
            case MainConstants.TestType.Kpi.TEST_OPEN_COMICS:
                InstallerTestParams comicsInstaller = new InstallerTestParams();
                comicsInstaller.setParametersOpenComics();
                TestOpenProduct testOpenComics = new TestOpenProduct(driver, comicsInstaller.getTestParameter());
                testOpenComics.start();
                break;
            case MainConstants.TestType.Kpi.TEST_OPEN_MAGAZINES:
                InstallerTestParams magazineInstaller = new InstallerTestParams();
                magazineInstaller.setParametersOpenMagazines();
                TestOpenProduct testOpenMagazine = new TestOpenProduct(driver, magazineInstaller.getTestParameter());
                testOpenMagazine.start();
                break;
            case MainConstants.TestType.Kpi.TEST_OPEN_WOODWIN:
                InstallerTestParams woodwinInstaller = new InstallerTestParams();
                woodwinInstaller.setParametersOpenWoodwin();
                TestOpenProduct testOpenWoodwin = new TestOpenProduct(driver, woodwinInstaller.getTestParameter());
                testOpenWoodwin.start();
                break;
            case MainConstants.TestType.Kpi.TEST_OPEN_FAVA:
                break;
            case MainConstants.TestType.Kpi.TEST_OPEN_PDF:
                InstallerTestParams pdfInstaller = new InstallerTestParams();
                pdfInstaller.setParametersOpenPdf();
                TestOpenProduct testOpenPdf = new TestOpenProduct(driver, pdfInstaller.getTestParameter());
                testOpenPdf.start();
                break;
            case MainConstants.TestType.Kpi.TEST_OPEN_NEWSPAPER:
                break;
            case MainConstants.TestType.Kpi.TEST_OPEN_CATALOG:
                break;
            case MainConstants.TestType.Kpi.TEST_ALL_KPI_BOOKS:
                new TestAllKpiBooks(driver).runAllKpiTests();
                break;
        }

        tearDown();
    }
}
