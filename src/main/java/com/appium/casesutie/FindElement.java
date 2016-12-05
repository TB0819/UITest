package com.appium.casesutie;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import io.appium.java_client.MobileElement;

public class FindElement{
	private static final Map<String, String> elementTypeMap; 
	
	static {  
		elementTypeMap = new HashMap<String,String>();  
		elementTypeMap.put("Id", "byId");  
		elementTypeMap.put("Name", "byName");  
		elementTypeMap.put("ClassName", "byClassName");  
		elementTypeMap.put("Xpath", "byXpath");  
		elementTypeMap.put("Automator", "byAutomator");
    }
	public FindElement(){}
	
	public MobileElement executeFindElement(Step runData) throws Exception{
		MobileElement element = null;
		String elementMethod = elementTypeMap.get(runData.getFindelement_type());
		if(StringUtils.isNotBlank(elementMethod)){
			Method method = this.getClass().getMethod(elementMethod, Step.class);
			element = (MobileElement) method.invoke(this, runData);
		}
		return element;
	}
	
	public MobileElement byName(Step runData){
		return runData.getDriver().findElementByAndroidUIAutomator("new UiSelector().text(\""+runData.getElement_value()+"\")");
	}
	
	public MobileElement byId(Step runData){
		return runData.getDriver().findElementById(runData.getElement_value());
	}
	
	public MobileElement byClassName(Step runData){
		return runData.getDriver().findElementByClassName(runData.getElement_value());
	}
	
	public MobileElement byXpath(Step runData){
		return runData.getDriver().findElementByXPath(runData.getElement_value());
	}
	
	public MobileElement byAutomator(Step runData){
		return runData.getDriver().findElementByAndroidUIAutomator(runData.getElement_value());
	}
	
}
