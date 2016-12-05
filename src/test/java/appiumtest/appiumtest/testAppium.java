package appiumtest.appiumtest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.test.tools.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class testAppium extends BaseTest{

	@Test
	public void test_RKD(){
		System.out.println(getClass().getName());
		System.out.println(getClass().getName());
		AndroidDriver<MobileElement> driver1 =(AndroidDriver<MobileElement>) driver;
		driver1.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
//		driver1.findElementByName("出入库").click();
		driver1.findElementByAndroidUIAutomator("new UiSelector().className(\""+"android.widget.TextView"+"\").text(\""+"出入库"+"\")").click();
//		driver1.findElementByName("采购单").click();
		driver1.findElementByAndroidUIAutomator("new UiSelector().text(\""+"入库单"+"\")").click();
//		UITestTools tools= new UITestTools();
//		tools.swpiToUp(driver1);
//		tools.swpiToUp(driver1);
		driver1.findElementById("zmsoft.rest.supply:id/btn_add").click();
		driver1.findElementsById("zmsoft.rest.supply:id/icon_arrow_left").get(0).click();
		driver1.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\""+"zmsoft.rest.supply:id/main_layout"+"\")).getChildByInstance(new UiSelector().className(\""+"android.widget.FrameLayout"+"\"), 0)").click();
		driver1.findElementByName("保存").click();
		Assert.assertEquals(3, 3);
	}
}
