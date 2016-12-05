package com.appium.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.appium.casesutie.Operate;
import com.appium.casesutie.Step;

public class ServiceBuilderTest{

	private String udid = "QMS4C15C28004268";
	private String deviceName = "HM_NOTE_1S_CU";
	private String deviceVersion = "6.0.1";
	private String bootstrapPort = "14779";
	private String logName = "NOTE_1s_30";
	public static AndroidDriver<MobileElement> driver;
	private AppiumDriverLocalService service;
	
	/**
	 * start driver
	 * @param udid
	 * @param deviceName
	 * @param deviceVersion
	 * @throws IOException 
	 */
//	@Parameters({"udid","deviceName","deviceVersion","bootstrapPort","logName"})
	@BeforeClass
	public void setUpMethod() throws IOException{
		//设置自动化相关参数
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceVersion);
		capabilities.setCapability(MobileCapabilityType.UDID, udid);
		capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, "True");
		capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, "True");
		//设置app的主包名和主类名
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "zmsoft.rest.supply");
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".ui.AppSplash");
		
//		service = new AppiumServiceBuilder()
//				.withAppiumJS(findCustomNode())
//				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
//				.withCapabilities(capabilities)
//				.build();
//		service.start();
		
		startService(udid, deviceName, deviceVersion,bootstrapPort,logName);
		//初始化
//		driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		driver =new AndroidDriver<MobileElement>(service.getUrl(), capabilities);//start driver
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}

	@Test
	public void test_lianxiren() throws Exception{
		Step runData = new Step();
		runData.setDriver(driver);
		runData.setElement_value("出入库");
		runData.setFindelement_type("Name");
		runData.setControl_action("click");
		Step runData1 = new Step();
		runData1.setDriver(driver);
		runData1.setElement_value("采购单");
		runData1.setFindelement_type("Name");
		runData1.setControl_action("click");
		Operate operate = new Operate();
		operate.executeOperate(runData);
		operate.executeOperate(runData1);
//		driver.findElementByName("出入库").click();
//		driver.findElementByName("采购单").click();
//		driver.findElementById("zmsoft.rest.supply:id/btn_add").click();
//		driver.findElementsById("zmsoft.rest.supply:id/icon_arrow_left").get(0).click();
//		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\""+"zmsoft.rest.supply:id/main_layout"+"\")).getChildByInstance(new UiSelector().className(\""+"android.widget.FrameLayout"+"\"), 0)").click();
//		driver.findElementByName("保存").click();
		
	}
	@AfterClass
	public void teardown(){
		driver.quit();
	}
	
	//启动appium 服务
	private void startService(String udid,String deviceName,String deviceVersion,String bootstrapPort,String logName) throws IOException{
		File logDir = new File("D:\\");
		File log = new File(logDir, logName+".txt");
//        log.createNewFile();
        
		Map<String, String> shellParam=new HashMap();
		shellParam.put("-U", udid);
		shellParam.put("--device-name", deviceName);
		shellParam.put("--platform-version", deviceVersion);
		service =null;
		service = AppiumDriverLocalService.buildService(
				new AppiumServiceBuilder()
				.withAppiumJS(findCustomNode())
				.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, bootstrapPort)
				.withEnvironment(shellParam)
//				.withLogFile(log)
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				.usingAnyFreePort());
		service.start();
	}
	
	/**
	 * find appium.js
	 * @return
	 */
	private static File findCustomNode(){
        Platform current = Platform.getCurrent();
        if (current.is(Platform.WINDOWS))
            return new File(String.valueOf("C:/Users/Administrator/AppData/Roaming/npm/node_modules/appium/build/lib/main.js"));

        if (current.is(Platform.MAC))
            return new File(String.valueOf("/Applications/Appium.app/Contents/Resources/node_modules/appium/bin/appium.js"));

        return new File(String.valueOf("specify your path on your own"));
    }
	
	public static AppiumDriver getDriver(){
		return driver;
	}	
	
}
