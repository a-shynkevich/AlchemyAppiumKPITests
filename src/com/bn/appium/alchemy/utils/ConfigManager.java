package com.bn.appium.alchemy.utils;

import static com.bn.appium.alchemy.utils.LoggerUtils.e;

/**
 * Created by nikolai on 23.06.2014.
 */
public class ConfigManager {
    private PropertiesManager propertiesManager;

    public ConfigManager() {
        propertiesManager = new PropertiesManager();
    }

    public PropertiesManager getPropertiesManager() {
        return propertiesManager;
    }

    public String getProperty(String configName){
        String property =  propertiesManager.getProperty(configName);
        System.out.println("Property:" + configName + " : " + property);
        return property;
    }

    public long getTimeout(){
        String line = getProperty(ConfigurationParametersEnum.TIMEOUT.name());
        long timeout;
        try{
            timeout = Long.parseLong(line);
        }catch (Throwable ex){
            e("Used default timeout = " + MainConstants.DEFAULT_TIMEOUT + ex.getMessage());
            timeout = MainConstants.DEFAULT_TIMEOUT;
        }
        return timeout;
    }

}
