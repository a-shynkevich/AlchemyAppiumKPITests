package com.bn.appium.alchemy.utils;

import com.bn.appium.alchemy.ios.TestOpenProduct;
import com.bn.appium.alchemy.manager.TestManager;

/**
 * Created by ashynkevich on 9/18/14.
 */
public class InstallerTestParams {
    private TestParameter testParameter = null;
    public InstallerTestParams(){
        testParameter = new TestParameter();
    }

    public void setParametersOpenBook(){
            getTestParameter().setProductName(TestManager.configManager.getProperty(ConfigurationParametersEnum.BOOK.name()));
            getTestParameter().setProductSize(TestManager.configManager.getProperty(ConfigurationParametersEnum.BOOK_SIZE.name()));
            getTestParameter().setReaderType(TestOpenProduct.Constants.Reader.EUPB);
    }

    public void setParametersOpenComics(){
        getTestParameter().setProductName(TestManager.configManager.getProperty(ConfigurationParametersEnum.DRP_COMICS.name()));
        getTestParameter().setProductSize(TestManager.configManager.getProperty(ConfigurationParametersEnum.DRP_COMICS_SIZE.name()));
        getTestParameter().setReaderType(TestOpenProduct.Constants.Reader.DRP);
    }

    public void setParametersOpenMagazines(){
        getTestParameter().setProductName(TestManager.configManager.getProperty(ConfigurationParametersEnum.DRP_MAGAZINE.name()));
        getTestParameter().setProductSize(TestManager.configManager.getProperty(ConfigurationParametersEnum.MAGAZINE_SIZE.name()));
        getTestParameter().setReaderType(TestOpenProduct.Constants.Reader.DRP);
    }

    public void setParametersOpenWoodwin(){
        getTestParameter().setProductName(TestManager.configManager.getProperty(ConfigurationParametersEnum.WOODWIN_MAGAZINE.name()));
        getTestParameter().setProductSize(TestManager.configManager.getProperty(ConfigurationParametersEnum.WOODWIN_SIZE.name()));
        getTestParameter().setReaderType(TestOpenProduct.Constants.Reader.EUPB);
    }

    public void setParametersOpenFava(){
        getTestParameter().setProductName(TestManager.configManager.getProperty(ConfigurationParametersEnum.EPIB.name()));
        getTestParameter().setProductSize(TestManager.configManager.getProperty(ConfigurationParametersEnum.EPIB_SIZE.name()));
        getTestParameter().setReaderType(TestOpenProduct.Constants.Reader.EUPB);
    }

    public void setParametersOpenPdf(){
        getTestParameter().setProductName(TestManager.configManager.getProperty(ConfigurationParametersEnum.PDF.name()));
        getTestParameter().setProductSize(TestManager.configManager.getProperty(ConfigurationParametersEnum.PDF_SIZE.name()));
        getTestParameter().setReaderType(TestOpenProduct.Constants.Reader.EUPB);
    }

    public void setParametersOpenNewspaper(){
        getTestParameter().setProductName(TestManager.configManager.getProperty(ConfigurationParametersEnum.NEWSPAPER.name()));
        getTestParameter().setProductSize(TestManager.configManager.getProperty(ConfigurationParametersEnum.NEWSPAPER_SIZE.name()));
        getTestParameter().setReaderType(TestOpenProduct.Constants.Reader.EUPB);
    }

    public void setParametersOpenCatalog(){
        getTestParameter().setProductName(TestManager.configManager.getProperty(ConfigurationParametersEnum.DRP_CATALOG.name()));
        getTestParameter().setProductSize(TestManager.configManager.getProperty(ConfigurationParametersEnum.DRP_CATALOG_SIZE.name()));
        getTestParameter().setReaderType(TestOpenProduct.Constants.Reader.EUPB);
    }

    public void setParametersVideoBook(){
        getTestParameter().setProductName(TestManager.configManager.getProperty(ConfigurationParametersEnum.VIDEO_BOOK.name()));
        getTestParameter().setProductSize(TestManager.configManager.getProperty(ConfigurationParametersEnum.VIDEO_BOOK_SIZE.name()));
        getTestParameter().setReaderType(TestOpenProduct.Constants.Reader.EUPB);
    }

    public TestParameter getTestParameter() {
        return testParameter;
    }
}
