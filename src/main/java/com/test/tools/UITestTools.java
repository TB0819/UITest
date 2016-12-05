package com.test.tools;

import io.appium.java_client.android.AndroidDriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UITestTools {

	//等待元素加载
	public boolean waitForVisible(WebDriver driver, final String element, int timeout){
		boolean isPresent = new WebDriverWait(driver, timeout).until(new ExpectedCondition<WebElement>(){
			public WebElement apply(WebDriver webDriver) {  
                return webDriver.findElement(By.id(element));  
            }
		}).isDisplayed(); 
		return isPresent;
	}
	
	//判断当前activity
	public boolean isActivity(AndroidDriver<WebElement> driver,String activityName){
		boolean flag = false;
		for(int i = 0;i<5;i++){
			if(activityName.equalsIgnoreCase(driver.currentActivity())){
				return flag = true;
			}
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		}
		return flag;
	}
	
	//右滑
	public void swpiToRight(AndroidDriver<WebElement> driver){
	  int width = driver.manage().window().getSize().width;
	  int height = driver.manage().window().getSize().height;
	  driver.swipe(width / 4, height / 2, width * 3 / 4, height / 2, 1000);
	}
	//左滑
	public void swpiToLeft(AndroidDriver<WebElement> driver){
	  int width = driver.manage().window().getSize().width;
	  int height = driver.manage().window().getSize().height;
	  driver.swipe(width * 3 / 4 , height / 2, width / 4, height / 2, 1000);
	}
	//上滑
	public void swpiToUp(AndroidDriver<WebElement> driver){
		 int width = driver.manage().window().getSize().width;
		 int height = driver.manage().window().getSize().height;
		 driver.swipe(width / 2, height * 3/ 4, width /2 , height /4, 1000);
	}
	//下滑
	public void swpiToDown(AndroidDriver<WebElement> driver){
		 int width = driver.manage().window().getSize().width;
		 int height = driver.manage().window().getSize().height;
		 driver.swipe(width / 2, height / 4, width /2 , height * 3 /4, 1000);
	}
}
