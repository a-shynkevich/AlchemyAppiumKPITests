package com.bn.appium.alchemy.utils;

/**
 * Created by ashynkevich on 9/18/14.
 */
public class TestParameter {
    private String testType = "";
    private String productName = "";
    private String productSize = "";
    private int readerType = 0;

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public int getReaderType() {
        return readerType;
    }

    public void setReaderType(int readerType) {
        this.readerType = readerType;
    }
}
