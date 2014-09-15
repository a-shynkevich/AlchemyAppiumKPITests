package com.bn.appium.alchemy.utils;

/**
 * Created by nikolai on 23.06.2014.
 */
public class MainConstants {
    public static final String CONFIGURATION_FILE_PATH = "config.properties";
    public static int DEFAULT_TIMEOUT = 5000;
    public static String FILE_NAME_LOG_TESTS = "iOS.csv";
    public static final String TIME_FORMAT = "hh_mm_ss_SSS__dd_MM_yyyy_";
    public static final String PATH_TO_SCREENSHOTS = "Screenshots";
    public static final String PATH_TO_ITEMS_NAME = "";
    public static String TEST_TYPE = "kpi";
    public static String TEST_NAME = "";
    public static String RANDOM_TEST_LOG = "#%s; time:%s; data:%s; testname:%s; action:%s;";
    public static final long SCREENSHOT_TIMEOUT = 0;
    public static String FILE_NAME_RANDOM_TESTS = "random.steps";
    public static long TIME_START_TEST = 0;

    public static class TestType {
        public static final String testOobe = "testOobe";
        public static final String testSearch = "testSearch";

        public static class Kpi {
            public static final String[] allTests = new String[]{
                    "testOobe",
                    "testSearch"
            };
            public static final int TEST_OOBE = 0;
            public static final int TEST_SEARCH = 1;


            public class TestAction {
                public static final String FIRST_SYNC = "FirstSync";
                public static final String FULL_SYNC = "FullSync";
                public static final String SEARCH = "SearchSync";
            }
        }
    }
}
