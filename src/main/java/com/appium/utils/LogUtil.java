package com.appium.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.annotations.Test;

public class LogUtil {
	public static final List<String> messages = new ArrayList<>();
	
	private LogUtil() {}
	
	/**
	 * 输入log信息
	 * @param logLevel
	 */
	public static void printLog(String logLevel){
		System.out.print(getLogFormatString(logLevel));
		messages.clear();
	}
	
	/**
	 * log格式化
	 * @param logLevel
	 * @return
	 */
    private static String getLogFormatString(String logLevel) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String logResult = String.join(" ", (String[]) messages.toArray(new String[messages.size()]));
        return String.format("%s [%s] %s\n", formatter.format(new Date()),logLevel, logResult);
    }
    
    @Test
    public void test_log(){
    	messages.add("findelement 你好");
    	messages.add("and click");
    	printLog("DEBUG");
    	messages.add("等待");
    	messages.add("20s");
    	printLog("INFO");
    	messages.add(System.getProperty("user.dir") + "/target/ExtentReport.html");
    	printLog("ERROR");
    	
    }
}
