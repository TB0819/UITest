package com.appium.utils;

import java.util.ArrayList;
import java.util.List;

public class TestCases {

    private String testCase;
    List<Testmethods> testMethod;
    List<String> testClass = new ArrayList<>();

    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    public List<Testmethods> getTestMethod() {
        return testMethod;
    }

    public void setTestMethod(List<Testmethods> testMethod) {
        this.testMethod = testMethod;
    }


    public void add(String Class){
    	testClass.add(Class);
    }
    
    public void run(){
    	
    }

}
