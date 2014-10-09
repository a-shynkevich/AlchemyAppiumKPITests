package com.bn.appium.alchemy.manager;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import com.bn.appium.alchemy.utils.*;
import java.io.*;
import java.util.Date;
import java.util.Random;
import static com.bn.appium.alchemy.utils.LoggerUtils.e;
import static com.bn.appium.alchemy.utils.LoggerUtils.i;

/**
 * Created by nikolai on 10.01.14.
 */
public class TestManager {
    private static volatile TestManager instance;

    public static void setDriver(AppiumDriver driver) {
        TestManager.driver = driver;
    }

    private static FileWorker fileWorker;
    private static FileWorker fileLogWorker;
    private static String deviceId = null;
    private static String buildName = null;
    private static String network = null;
    private static String os_system = null;
    private static String hwDevice = null;
    private static String osDevice = null;
    private static String dumpKpiInfo = null;
    private static String account = null;
    private static String password = null;
    private static int timeout = 0;
    private static String[] args = null;
    private static long mLastDumpTime = 0;

    private static long mStartTime = 0;
    private static long mEndTime = 0;
    public static ConfigManager configManager = null;
    private static AppiumDriver driver;

    private TestManager(String[] args) {
        this.args = args;
        configManager = new ConfigManager();
        init();
        fileWorker = new FileWorker();
        fileLogWorker = new FileWorker(MainConstants.FILE_NAME_LOG_TESTS);
    }



    public void init(){
        if (args.length > 0)
            MainConstants.TEST_NAME = args[0].toString();
        if (args.length > 1) {
            dumpKpiInfo = args[1].toString();
        }else {
            dumpKpiInfo = configManager.getProperty(ConfigurationParametersEnum.DUMP_KPI_INFO.name());
        }
        if (args.length > 2){
            deviceId = args[2].toString();
        }else {
            deviceId = configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE_ID.name());
        }
        if (args.length > 3){
            buildName = args[3].toString();
        }else {
            buildName = configManager.getProperty(ConfigurationParametersEnum.BUILD_NAME.name());
        }
        if(args.length > 4){
            hwDevice = args[4].toString();
        }else{
            hwDevice = configManager.getProperty(ConfigurationParametersEnum.IOS_DEVICE.name());
        }
        if (args.length > 5){
            account = args[5].toString();
        }else {
            account = configManager.getProperty(ConfigurationParametersEnum.LOGIN.name());
        }
        if (args.length > 6){
            password = args[6].toString();
        }else{
            password = configManager.getProperty(ConfigurationParametersEnum.PASSWORD.name());
        }
        if (args.length > 7){
            parseTimeout(args[7].toString());
        }else{
            parseTimeout(TestManager.configManager.getProperty(ConfigurationParametersEnum.TIMEOUT.name()));
        }

        network = "";
//        todo write getOsSystem method
        os_system = TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_PLATFORM_NAME.name());
        osDevice = TestManager.configManager.getProperty(ConfigurationParametersEnum.IOS_PLATFORM_VERSION.name());

    }

    private void parseTimeout(String line){
        try{
            timeout = Integer.parseInt(line);
        }catch (Throwable ex){
            e("Used default timeout = " + MainConstants.DEFAULT_TIMEOUT + ex.getMessage());
            timeout = MainConstants.DEFAULT_TIMEOUT;
        }
    }

    public static TestManager getInstance(String[] args){
        if(instance == null)
            synchronized (TestManager.class){
                if(instance == null)
                    instance = new TestManager(args);
            }
        return instance;
    }

    public static TestManager getInstance(){
        return getInstance(args);
    }


    public static ItemLog addLogParams(Date date, String testAction, String testData, boolean testResult){
        ItemLog itemLog = new ItemLog();
        itemLog.setBuild(getBuildName());
        itemLog.setDeviceId(getDeviceId());
        itemLog.setNet(getNetwork());
        itemLog.setHw(getHwDevice());
        itemLog.setOs(getOsDevice());
        itemLog.setSlaveId("");
        itemLog.setDate(date, "");
        itemLog.setTime(date, "");
        itemLog.setStartTime(mStartTime);
        itemLog.setEndTime(mEndTime, mLastDumpTime);
        itemLog.setTestId("AlchemyKpi");
        itemLog.setTestAction(testAction);
        itemLog.setTestData(testData);
        itemLog.setTestResult(testResult);
        return itemLog;
    }

    public static int generateRandom(int n){
        if(n == 0)
            return 0;
        return Math.abs(new Random().nextInt()) % n;
    }

    public static void write(ItemLog itemLog){
        if(TestManager.configManager.getProperty(ConfigurationParametersEnum.DUMP_KPI_INFO.name()).equals("true"))
            fileLogWorker.writeLog(itemLog);
    }

    public static void captureScreenshot(String testName) {
        new File("target/screenshots/").mkdirs();
        String filename = "target/screenshots/" + testName + ".png";
        i("Filename: " + filename);

        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        File destFile = new File(filename);
        try {
            FileUtils.copyFile(scrFile, destFile);
        } catch (IOException e) {
            System.out.println("Error capturing screen shot of " + testName + " test failure.");
            e.printStackTrace();
        }

//        try {
//            WebDriver augmentedDriver = new Augmenter().augment(driver);
//            File scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
//            FileUtils.copyFile(scrFile, new File(filename), true);
//        } catch (Exception e) {
//            System.out.println("Error capturing screen shot of " + testName + " test failure.");
//        }
    }

    private static void timer(boolean isStart){
        long time = System.currentTimeMillis();
        if(isStart)
            mStartTime = time;
        else{
            mEndTime = time;
        }
    }

    public static void startTimer(){
        timer(true);
    }

    public static void stopTimer(boolean accuracy){
        timer(false);
    }

    public static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int detectTestType(String testArg){
        int detectedTestType = 0;

        if (testArg.equals("testAllKpiBooks"))
            detectedTestType = MainConstants.TestType.Kpi.ALL_KPI_TESTS;
        if (testArg.equals("testOobe"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_OOBE;
        if (testArg.equals("testSearch"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_SEARCH;
        if (testArg.equals("testOpenBook"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_OPEN_BOOK;
        if (testArg.equals("testOpenComics"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_OPEN_COMICS;
        if (testArg.equals("testOpenMagazines"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_OPEN_MAGAZINES;
        if (testArg.equals("testOpenWoodwing"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_OPEN_WOODWIN;
        if (testArg.equals("testOpenFava"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_OPEN_FAVA;
        if (testArg.equals("testOpenPdf"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_OPEN_PDF;
        if (testArg.equals("testOpenNewspaper"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_OPEN_NEWSPAPER;
        if (testArg.equals("testOpenCatalog"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_OPEN_CATALOG;
        if (testArg.equals("testAllKpiBooks"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_ALL_KPI_BOOKS;
        if (testArg.equals("testDefferedSignIn"))
            detectedTestType = MainConstants.TestType.Kpi.TEST_DEFFERED_SIGN_IN;


        return detectedTestType;
    }


    @Deprecated
    public static void stopTimer(){
        timer(false);
    }

    public static String getDeviceId() {
        return deviceId;
    }

    public static String getBuildName() {
        return buildName;
    }

    public static String getNetwork() {
        return network;
    }

    public static String getOs_system() {
        return os_system;
    }

    public static String getHwDevice() {
        return hwDevice;
    }

    public static String getOsDevice() {
        return osDevice;
    }

    public static String getDumpKpiInfo() {
        return dumpKpiInfo;
    }

    public static String getAccount() {
        return account;
    }

    public static String getPassword() {
        return password;
    }

    public static int getTimeout() {
        return timeout;
    }

}
