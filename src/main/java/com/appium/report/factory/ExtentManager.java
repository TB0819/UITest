package com.appium.report.factory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	public static ExtentReports instance;
	final static String filePath = System.getProperty("user.dir") + "/target/ExtentReport.html";

	public synchronized static ExtentReports getInstance() {
		if (instance == null) {
			instance = createInstance(filePath);
		}
		return instance;
	}
	
    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);
        htmlReporter.setAppendExisting(true);
        
        instance = new ExtentReports();
        instance.attachReporter(htmlReporter);
        
        return instance;
    }
}
