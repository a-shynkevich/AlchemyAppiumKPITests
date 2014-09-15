package com.bn.appium.alchemy.utils;


import com.bn.appium.alchemy.manager.TestManager;

/**
 * Created by nikolai on 27.03.14.
 */
public class Timer {

    public static long TIMEOUT = 60000;

    public static long getTimeout(){
        long diff = (System.currentTimeMillis() - MainConstants.TIME_START_TEST);
        return TestManager.mTimeout - diff;
    }

}
