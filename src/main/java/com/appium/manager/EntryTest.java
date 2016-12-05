package com.appium.manager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.testng.annotations.Test;


public class EntryTest {

//	@Test
//	public void test_entry(){
//		AppiumDriverLocalService service = null;
//		service = AppiumDriverLocalService.buildDefaultService();
//		service.start();
//		System.out.println(service.getUrl().toString());
//	}
	
	 public static void main(String[] args) {
		 ExecutorService threadPool = Executors.newFixedThreadPool(2);
		 threadPool.submit(new AppiumServiceTask("128704", "logingtest"));
		 threadPool.submit(new AppiumServiceTask("648526", "logGYLtest"));
		 threadPool.shutdown();
	 }
}
