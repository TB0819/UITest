package com.appium.report.factory;

import org.testng.Reporter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager { 
    public static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

    public static ExtentReports extent = ExtentManager.getInstance();
    public static InputStream input = null;

    public static synchronized  ExtentTest getTest() {
        return extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }

    public static synchronized ExtentTest createTest(String testName) {
        return createTest(testName, "", "");
    }

    public synchronized static ExtentTest createTest(String testName, String desc, String deviceId) {
        ExtentTest test = extent.createTest(testName, desc).assignCategory(deviceId);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        extent.setSystemInfo("os", "win7");
        extent.setSystemInfo("TestFrame", "APPIUM: AppiumVersion:1.6.0");
        return test;
    }

    public synchronized static void logOutPut(String imgSrc, String headerName) {
        imgSrc = "<div class='col l4 m6 s12'><div class='card-panel'><h4 class='md-display-4'>"
            + headerName + "</h4><img src=" + imgSrc
            + " style=\"width:100%;height:100%;\"></div></div>";
        extent.setTestRunnerOutput(imgSrc);
        //extent.setTestRunnerOutput(s);
    }

    public synchronized static void logger(String message) {
        Reporter.log(message + "<br>", true);
        getTest().log(Status.INFO, message + "<br>");
    }
}
