package com.appium.test;

import java.util.ArrayList;
import java.util.List;

import org.testng.TestNG;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.appium.testng.listener.MyTransformer2;

public class TestngDemo {
	
	public static void main(String[] args){
		ArrayList<String> listeners = new ArrayList<String>();
		listeners.add("com.appium.testng.listener.MyHook");
		XmlSuite suite = new XmlSuite();
		suite.setName("TmpSuite");
		 
		XmlTest test = new XmlTest(suite);
		test.setName("TmpTest");
		suite.setListeners(listeners);

		List<XmlClass> classes = new ArrayList<XmlClass>();
		classes.add(new XmlClass("com.appium.test.TestngDemo"));
		test.setXmlClasses(classes) ;
		
		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);

		TestNG tng = new TestNG();
		tng.setXmlSuites(suites);
		tng.setAnnotationTransformer(new MyTransformer2());
		tng.run();
	}
	
	@Test
	public void test_invcount(){
		System.err.println("haha");
	}
	
//	@Test(invocationCount =3)
	public void test_invcount1(){
		System.err.println("haha");
	}

}
