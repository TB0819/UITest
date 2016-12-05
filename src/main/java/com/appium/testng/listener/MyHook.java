package com.appium.testng.listener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;

import com.testng.demo.TestCount;

public class MyHook implements IHookable{
	public int invocationCount = 2;
	Properties prop = new Properties();

	@Override
	public void run(final IHookCallBack callBack, ITestResult testResult) {
		try {
			prop.load(new FileInputStream("config.properties"));
			invocationCount = Integer.parseInt(prop.getProperty("COUNT"));
			TestCount count = new TestCount(invocationCount);
			System.out.println(count.getCount());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		callBack.runTestMethod(testResult);
	}
}
