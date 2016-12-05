package com.testng.demo;

public class TestCount {
	public static int count;
	
	public TestCount(int count){
		TestCount.count = count;
	}
	
	public int getCount(){
		return count;
	}

}
