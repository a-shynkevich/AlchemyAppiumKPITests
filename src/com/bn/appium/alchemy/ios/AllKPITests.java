package com.bn.appium.alchemy.ios;

import com.bn.appium.alchemy.utils.TestHelper;
import io.appium.java_client.AppiumDriver;

/**
 * Created by ashynkevich on 9/15/14.
 */
public class AllKPITests extends TestHelper{

    TestOpenBook testOpenBook;
    TestSearch testSearch;
    TestOobe testOobe;

    public AllKPITests(AppiumDriver driver) {
        super(driver);
        testOobe = new TestOobe(driver);
        testSearch = new TestSearch(driver);
        testOpenBook = new TestOpenBook(driver);
    }
    public void runAllKpiTests(){
        testOobe.login();
        testSearch.searchBook();
        testSearch.goToHome();
        testOpenBook.loadBook();
        testOpenBook.readBook();
        testOpenBook.goToHome();
    }
}
