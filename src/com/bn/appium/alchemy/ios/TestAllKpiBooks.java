package com.bn.appium.alchemy.ios;

import com.bn.appium.alchemy.utils.InstallerTestParams;
import com.bn.appium.alchemy.utils.TestHelper;
import com.bn.appium.alchemy.utils.TestParameter;
import io.appium.java_client.AppiumDriver;

/**
 * Created by ashynkevich on 9/15/14.
 */
public class TestAllKpiBooks extends TestHelper{
    InstallerTestParams bookInstaller;
    InstallerTestParams comicsInstaller;
    InstallerTestParams magazineInstaller;
    InstallerTestParams woodwinInstaller;
    InstallerTestParams pdfInstaller;
    TestOpenProduct testOpenProduct;

    public TestAllKpiBooks(AppiumDriver driver) {
        super(driver);
        bookInstaller = new InstallerTestParams();
        comicsInstaller = new InstallerTestParams();
        magazineInstaller = new InstallerTestParams();
        woodwinInstaller = new InstallerTestParams();
        pdfInstaller = new InstallerTestParams();
    }

    public void runAllKpiTests(){

        bookInstaller.setParametersOpenBook();
        testOpenProduct = new TestOpenProduct(driver, bookInstaller.getTestParameter());
        testOpenProduct.start();

        comicsInstaller.setParametersOpenComics();
        testOpenProduct = new TestOpenProduct(driver, comicsInstaller.getTestParameter());
        testOpenProduct.start();

        magazineInstaller.setParametersOpenMagazines();
        testOpenProduct = new TestOpenProduct(driver, magazineInstaller.getTestParameter());
        testOpenProduct.start();

        woodwinInstaller.setParametersOpenWoodwin();
        testOpenProduct = new TestOpenProduct(driver, woodwinInstaller.getTestParameter());
        testOpenProduct.start();

        pdfInstaller.setParametersOpenPdf();
        testOpenProduct = new TestOpenProduct(driver, pdfInstaller.getTestParameter());
        testOpenProduct.start();

    }
}
