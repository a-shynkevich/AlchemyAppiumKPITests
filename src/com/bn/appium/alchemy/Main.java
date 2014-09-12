package com.bn.appium.alchemy;

import com.bn.appium.alchemy.ios.IOsKpiTests;
import com.bn.appium.alchemy.utils.ConfigManager;

/**
 * Created by nikolai on 25.07.2014.
 */
public class Main {

    public static void main(String[] args){
        ConfigManager configManager = new ConfigManager();
                IOsKpiTests iOsKpiTests = new IOsKpiTests();
                try {
                    iOsKpiTests.setUp();
                    iOsKpiTests.login();
                    iOsKpiTests.logOut();
                    iOsKpiTests.tearDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
    }
}
