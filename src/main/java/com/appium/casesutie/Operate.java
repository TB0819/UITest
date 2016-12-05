package com.appium.casesutie;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import io.appium.java_client.MobileElement;

public class Operate{
	private FindElement findElement = new FindElement();
	private static final Map<String, String> operateTypeMap; 
//	public static int WIDTH = driver.manage().window().getSize().width;
//	public static int HEIGHT = driver.manage().window().getSize().height;

	static {  
		operateTypeMap = new HashMap<String,String>();  
		operateTypeMap.put("click", "click");  
		operateTypeMap.put("input", "input");  
		operateTypeMap.put("verifyText", "verifyText");  
		operateTypeMap.put("nativeKey", "nativeKey");  
		operateTypeMap.put("upSwipe", "upSwipe");
		operateTypeMap.put("downSwipe", "downSwipe");
		operateTypeMap.put("longPress", "longPress");
    }
	
	public Operate(){}
	
	public void executeOperate(Step runData) throws Exception{
		String operateMethod = operateTypeMap.get(runData.getControl_action());
		if(StringUtils.isNotBlank(operateMethod)){
			Method method = this.getClass().getMethod(operateMethod, Step.class);
			method.invoke(this, runData);
		}
	}
	//点击
	public void click(Step runData) throws Exception{
		findElement.executeFindElement(runData).click();
	}
	
	//输入
	public void input(Step runData) throws Exception{
		MobileElement element = findElement.executeFindElement(runData);
		element.clear();
		element.sendKeys(runData.getData());
	}
	
	//文本检查
	public void verifyText(Step runData) throws Exception{
		findElement.executeFindElement(runData);
	}
	
	//系统按键
	public void nativeKey(int key){
//		runData.getDriver().pressKeyCode(key);
	}
	
	//上滑
	public void upSwipe(){
//		runData.getDriver().swipe(WIDTH / 2, HEIGHT * 3/ 4, WIDTH /2 , HEIGHT /4, 1000);
	}
	
	//下滑
	public void downSwipe(){
//		runData.getDriver().swipe(WIDTH / 2, HEIGHT / 4, WIDTH /2 , HEIGHT * 3 /4, 1000);
	}
	
	//长按
	public void longPress(){
		
	}
}
