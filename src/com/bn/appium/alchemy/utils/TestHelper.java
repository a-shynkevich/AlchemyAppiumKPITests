package com.bn.appium.alchemy.utils;

import com.bn.appium.alchemy.manager.TestManager;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by ashynkevich on 9/14/14.
 */
public class TestHelper {

    public TestHelper (AppiumDriver driver) {
        this.driver = driver;

    }

    protected AppiumDriver driver;
    private ConfigManager configManager;
    private Process process;
    private TestManager testManager;

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




    public WebElement findElement (By byElement, boolean isElementDisplayed){
        WebElement element = null;
        try {
            element = driver.findElement(byElement);
        }catch (Exception e) {
            return null;
        }
        if(!element.isEnabled()){
            return null;
        }
        if(isElementDisplayed) {
            if (!element.isDisplayed()) {
                return null;
            }
        }
        return element;
    }

    public boolean isElementPresent(By by){
        try {
            WebElement element = driver.findElement(by);
            if(element.isEnabled()) {
                return true;
            }
        }catch (Exception e){
            log("element by" + by.toString() + "is not found.");
            return false;
        }
        return false;
    }

    public boolean waitWhileElementExist(By by, long waitTimeMs){

        long waiterStartTime = System.currentTimeMillis();
        long startTime = 0;

        while (findElement(by, true) != null){
            if (System.currentTimeMillis() - startTime > 100) {
                log("wait while element by "+by.toString() + " is present.");
                startTime = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - waiterStartTime >= waitTimeMs){
                return false;
            }
        }
        return true;
    }

    protected WebElement waitForElement(final By by, long timeMs, boolean isElementDisplayed) {
        long waiterStartTime = System.currentTimeMillis();
        long startTime = 0;

        WebElement element = findElement(by, isElementDisplayed);
        while (element == null){
            if (System.currentTimeMillis() - startTime > 1000) {
                log("wait element by: \"" + by.toString() + "\".");
                startTime = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - waiterStartTime >= timeMs){
                log("element by: \"" + by.toString() + "\" was not found.");
                return null;
            }
            element = findElement(by, isElementDisplayed);
        }

        return element;
    }

    protected WebElement waitForElement(final By firstBy, final By secondBy, long timeMs, boolean isElementDisplayed) {
        long waiterStartTime = System.currentTimeMillis();
        long startTime = 0;

        WebElement element = findElement(firstBy, isElementDisplayed);
        WebElement element1 = findElement(secondBy, isElementDisplayed);

        while (element == null && element1 == null){
            if (System.currentTimeMillis() - startTime > 1000) {
                log("wait elements by: \"" + firstBy.toString() + "\" or +" +secondBy.toString() +".");
                startTime = System.currentTimeMillis();
            }
            if (System.currentTimeMillis() - waiterStartTime >= timeMs){
                log("elements by: \"" + firstBy.toString() + "\"" + secondBy.toString() + "was not found.");
                return null;
            }
            element = findElement(firstBy, isElementDisplayed);
            element1 = findElement(secondBy, isElementDisplayed);
        }
        if(element != null){
            return element;
        }else
            if(element1 != null){
                return element1;
            }
        return null;
    }

    protected WebElement waitForElement(final By by, int index, long timeMs) {
        WebDriverWait wait = new WebDriverWait(driver, timeMs/1000);
        WebElement element;
        try {
            List <WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
            element = elements.get(index);
            if(element.isDisplayed()){
                return element;
            }
        }catch (Exception e){
            log("element by: \"" + by.toString() + "\" was not found.");
            return null;
        }return null;
    }

    protected WebElement getWebElement(By by){
        WebElement webElement = null;
        try {
            webElement = driver.findElement(by);
        } catch (Exception ex) {
            webElement = null;
        }
        return webElement;
    }

    protected void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void log(String message) {
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
