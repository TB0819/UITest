package com.appium.testng.listener;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.TestListenerAdapter;

import com.annotation.values.SkipIf;
import com.appium.manager.AppiumParallel;

public class TestngListener extends TestListenerAdapter implements ITestListener {
	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
//		takeScreenShot(tr);
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		super.onTestSkipped(tr);
//		takeScreenShot(tr);
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		super.onTestSuccess(tr);
	}

	@Override
	public void onTestStart(ITestResult tr) {
		Object currentClass = tr.getInstance();
		AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) ((AppiumParallel) currentClass).getDriver();
		SkipIf skip = tr.getMethod().getConstructorOrMethod().getMethod().getAnnotation(SkipIf.class);
		if (skip != null) {
			String info = skip.platform();
			if (driver.toString().split("\\(")[0].trim().toString().contains(info)) {
				System.out.println("skipping test");
				throw new SkipException("Skipped because property was set to :::" + info);
			}
		}
	}

	@Override
	public void onFinish(ITestContext testContext) {
		super.onFinish(testContext);
		// List of test results which we will delete later
		ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
		// collect all id's from passed test
		Set<Integer> passedTestIds = new HashSet<Integer>();
		for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
			passedTestIds.add(getId(passedTest));
		}

		Set<Integer> failedTestIds = new HashSet<Integer>();
		for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
			int failedTestId = getId(failedTest);

			// if we saw this test as a failed test before we mark as to be
			// deleted
			// or delete this failed test if there is at least one passed
			// version
			if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)) {
				testsToBeRemoved.add(failedTest);
			} else {
				failedTestIds.add(failedTestId);
			}
		}

		// finally delete all tests that are marked
		for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator
				.hasNext();) {
			ITestResult testResult = iterator.next();
			if (testsToBeRemoved.contains(testResult)) {
				iterator.remove();
			}
		}
	}

	private int getId(ITestResult result) {
		int id = result.getTestClass().getName().hashCode();
		id = id + result.getMethod().getMethodName().hashCode();
		id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
		return id;
	}

//	// 自动截图，保存图片到本地以及html结果文件中
//	private void takeScreenShot(ITestResult tr) {
//		String currentPath = System.getProperty("user.dir");
//		AppiumDriver driver = null;
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
//		String mDateTime = formatter.format(new Date());
//		String screenShotFile = currentPath + "\\" + mDateTime + "_" + tr.getName();
//		File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//		try {
//			FileUtils.copyFile(screenShot, new File(screenShotFile));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Reporter.setCurrentTestResult(tr);
//		Reporter.log(screenShotFile);
//
//		// 这里实现把图片链接直接输出到结果文件中，通过邮件发送结果则可以直接显示图片
//		Reporter.log("<img src=\"../" + screenShotFile + "\"/>");
//
//	}
}
