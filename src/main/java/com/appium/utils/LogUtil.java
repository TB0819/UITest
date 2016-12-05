package com.appium.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.annotations.Test;

public class LogUtil {
	public final List<String> messages = new ArrayList<>();
	
	private LogUtil() {}
	
	public void printLog(String logLevel){
		System.out.print(getLogFormatString(logLevel));
		messages.clear();
	}
	
    public String getLogFormatString(String logLevel) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String logResult = String.join(" ", (String[]) messages.toArray(new String[messages.size()]));
        return String.format("%s /%s: %s\n", formatter.format(new Date()),logLevel, logResult);
    }
    
    @Test
    public void test_log(){
    	messages.add("findelement 你好");
    	messages.add("and click");
    	printLog("DEBUG");
    	messages.add("等待");
    	messages.add("20s");
    	printLog("INFO");
    	messages.add("");
    	printLog("ERROR");
    	
    }
}
