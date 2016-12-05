package com.appium.manager;

import static java.util.Arrays.asList;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.appium.casesutie.CommonData;

import org.testng.xml.XmlSuite.ParallelMode;

public class TestExecutor {
	private static String TESTCASEPATHE = "com.appium.casesutie.RunTestCases";
	private Properties prop = CommonData.getInstance().prop;

	public void runParallelAppium(int deviceCount) throws MalformedURLException {
		runMethodParallel(constructXmlSuiteForParallel(deviceCount));
	}

	/**
	 * 生成testng xml文件
	 * @param deviceCount 并发次数
	 * @return  返回 testng xml配置文件
	 */
	private XmlSuite constructXmlSuiteForParallel(int deviceCount) {
		ArrayList<String> listeners = new ArrayList<String>();
		try {
			prop.load(new FileInputStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// listeners.add("com.appium.testng.listener.TestngListener");
		// listeners.add("com.appium.testng.listener.RetryListener");
		if (prop.getProperty("LISTENERS") != null) {
			Collections.addAll(listeners, prop.getProperty("LISTENERS").split("\\s*,\\s*"));
		}
		XmlSuite suite = new XmlSuite();
		suite.setName("TestNG Forum");
		suite.setThreadCount(deviceCount);
		suite.setParallel(ParallelMode.TESTS);
		suite.setVerbose(2);
		suite.setListeners(listeners);
		if (prop.getProperty("LISTENERS") != null) {
			suite.setListeners(listeners);
		}

		for (int i = 0; i < deviceCount; i++) {
			XmlTest test = new XmlTest(suite);
			List<XmlClass> xmlClasses = new ArrayList<XmlClass>();
			test.setName("TestNG Test" + i);
			test.setPreserveOrder("false");
			xmlClasses.add(new XmlClass(TESTCASEPATHE));
			test.setXmlClasses(xmlClasses);

		}
		return suite;
	}

	/**
	 * 运行 testng 程序
	 * @param suite  testng xml配置文件
	 */
	private void runMethodParallel(XmlSuite suite) {
		TestNG testNG = new TestNG();
		testNG.setXmlSuites(asList(suite));
		testNG.run();
	}
}
