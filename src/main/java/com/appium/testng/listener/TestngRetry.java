package com.appium.testng.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

public class TestngRetry implements IRetryAnalyzer{
	private int retryCount = 1;
	private static int maxRetryCount=2;
//	private static ConfigReader config;
//	static {
//
//        //外围文件配置最大运行次数
//		config = new ConfigReader(TestngListener.CONFIG);
//		maxRetryCount = config.getMaxRunCount();
//		}

	public boolean retry(ITestResult result) {
		if (retryCount <= maxRetryCount) {
			String message = "running retry for  '" + result.getName() + "' on class " + 

            				this.getClass().getName() + " Retrying " + retryCount + " times";
			Reporter.setCurrentTestResult(result);
			Reporter.log("RunCount=" + (retryCount + 1));
			retryCount++;
			return true;
		}
		return false;
	}

}
