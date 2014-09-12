package com.bn.appium.alchemy.ios;


import com.sun.scenario.animation.shared.CurrentTime;
import io.appium.java_client.AppiumDriver;
import com.bn.appium.alchemy.manager.TestManager;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.bn.appium.alchemy.utils.ConfigManager;
import com.bn.appium.alchemy.utils.ConfigurationParametersEnum;
import com.bn.appium.alchemy.utils.MainConstants;
import com.bn.appium.alchemy.utils.Timer;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IOsKpiTests {
    private AppiumDriver driver;
    private ConfigManager configManager;
    private Process process;
    private TestManager testManager;

    public void setUp() throws Exception {
        configManager = new ConfigManager();
//        launchServer();
        Thread.sleep(5000);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        File appDir = new File(configManager.getProperty(ConfigurationParametersEnum.IOS_APP_DIR.name()));
        File app = new File(appDir, configManager.getProperty(ConfigurationParametersEnum.IOS_APP.name()));
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformVersion", configManager.getProperty(ConfigurationParametersEnum.IOS_PLATFORM_VERSION.name()));
        capabilities.setCapability("platformName", configManager.getProperty(ConfigurationParametersEnum.IOS_PLATFORM_NAME.name()));
        String deviceName = configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE.name());
        capabilities.setCapability("deviceName",deviceName);
        if(!deviceName.toLowerCase().contains("simulator")){
            capabilities.setCapability("udid", configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE_ID.name()));
        }
//        capabilities.setCapability("bundleid", configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE_ID.name()));
        capabilities.setCapability("app",app.getAbsolutePath());

        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        MainConstants.TIME_START_TEST = System.currentTimeMillis();
        MainConstants.FILE_NAME_LOG_TESTS = "iOs.csv";
        testManager = TestManager.getInstance(driver);
    }

    private void launchServer() {
        new Thread(new Runnable() {
            public void run() {
                String[] commands = new String[]{
                        "/usr/local/bin/appium",
                        "-U",
                        configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE_ID.name())
                };

                try {
                    process = Runtime.getRuntime().exec(commands);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void tearDown() throws Exception {
        driver.quit();
        if(process != null)
            process.destroy();
    }

    private Point getCenter(WebElement element) {

        Point upperLeft = element.getLocation();
        Dimension dimensions = element.getSize();
        return new Point(upperLeft.getX() + dimensions.getWidth()/2, upperLeft.getY() + dimensions.getHeight()/2);
    }

    private WebElement getWebElement(By by){
        WebElement webElement = null;
        try {
            webElement = driver.findElement(by);
        } catch (Exception ex) {
            webElement = null;
        }
        return webElement;
    }

    private void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void logOut() {
        WebElement webElement;
        webElement = getWebElement(By.name("com.bn hub hamburger menu"));
        if(webElement == null)  return;
        log("click \"com.bn hub hamburger menu\" button");
        webElement.click();
        sleep(2000);

        webElement = getWebElement(By.name("SETTINGS"));
        if(webElement == null) return;
        log("click \"SETTINGS\" button");
        webElement.click();
        sleep(2000);

        webElement = getWebElement(By.name("Logout"));
        if(webElement == null) return;
        log("click \"Logout\" button");
        webElement.click();
        sleep(2000);

        webElement = getWebElement(By.name("OK"));
        if(webElement == null) return;
        log("click \"OK\" button");
        webElement.click();

        sleep(10000);

//        long start = System.currentTimeMillis();
//        WebElement signInButton = getWebElement(By.name("signIn"));
//        while (signInButton == null) {
//            if(System.currentTimeMillis() - start > 1000) {
//                log("wait for \"signIn\" button");
//                start = System.currentTimeMillis();
//            }
//            sleep(1000);
//            signInButton = getWebElement(By.name("signIn"));
//        }
    }

    public void login() throws Exception {
        MainConstants.TEST_NAME = MainConstants.Android.testOobe;
        log("start test");
        WebElement signInButton = getWebElement(By.name("signIn"));
        if (signInButton == null) return;

        if (signInButton.isDisplayed()) {
            log("click sign in button");
            signInButton.click();
        }
        Thread.sleep(2000);

        WebElement editText = getWebElement(By.className("UIATextField"));
        log("input login: " + configManager.getProperty(ConfigurationParametersEnum.LOGIN.name()));
        editText.sendKeys(configManager.getProperty(ConfigurationParametersEnum.LOGIN.name()));

        WebElement secureEditText = getWebElement(By.className("UIASecureTextField"));
        log("input pasword: *****");
        secureEditText.sendKeys(configManager.getProperty(ConfigurationParametersEnum.PASSWORD.name()) + "\n");

        if (configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE.name()).toLowerCase().contains("iphone")) {
            WebElement signInBtn = getWebElement(By.name("Sign In"));
            signInBtn.click();
        }
        log("press sign in");

        TestManager.startTimer();

        WebElement webElement = getWebElement(By.name("Free Sample"));

        long startTime = System.currentTimeMillis();
        while (true) {
            if (System.currentTimeMillis() - startTime > 1000) {
                log("wait for \"Free Sample\" button");
                startTime = System.currentTimeMillis();
            }
            if (Timer.getTimeout() <= 0) {
                log("\"Free Sample\" button is not found");
                TestManager.write(TestManager.addLogParams(new Date(), MainConstants.Android.Kpi.TestAction.FIRST_SYNC, Constant.Account.ACCOUNT, false));
                return;
            }
            if (webElement != null) {
                TestManager.stopTimer(false);
                TestManager.write(TestManager.addLogParams(new Date(), MainConstants.Android.Kpi.TestAction.FIRST_SYNC, Constant.Account.ACCOUNT, true));
                break;
            }
            webElement = getWebElement(By.name("Free Sample"));
        }

        TestManager.stopTimer(false);
        while (waitElement(By.name("Network connection in progress"), 5000)){
            webElement = getWebElement(By.name("Network connection in progress"));
            while (webElement != null){
                log("wait for signed in");
                webElement = getWebElement(By.name("Network connection in progress"));
            }
            TestManager.stopTimer(false);
        }


        try {
            webElement = getWebElement(By.name("Network connection in progress"));
        } catch (Exception ex) {
            webElement = null;
        }

        TestManager.write(TestManager.addLogParams(new Date(), MainConstants.Android.Kpi.TestAction.FULL_SYNC, Constant.Account.ACCOUNT, true));
    }

    private boolean waitElement(final By by, long ms){
        WebDriverWait waiter = new WebDriverWait(driver, ms/1000);
        WebElement webElement = null;
        try {
            webElement = waiter.until(ExpectedConditions.visibilityOfElementLocated(by));
            if (webElement.isDisplayed())
                return true;
        }catch (Exception ex){
            return false;
        }
        return false;
    }

    private void log(String message) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:SSS");
        System.out.println(simpleDateFormat.format(System.currentTimeMillis()) + " " + message);
    }
    public static class Constant{
        public static class Account{
            public static String ACCOUNT = "";
            public static String PASSWORD = "";
        }
    }
}
