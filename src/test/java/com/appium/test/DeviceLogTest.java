package com.appium.test;


import java.util.TreeSet;
import org.testng.annotations.Test;
import com.appium.devices.BackgroundLogcatAction;
import com.github.cosysoft.device.android.AndroidDevice;
import com.github.cosysoft.device.android.impl.AndroidDeviceStore;

public class DeviceLogTest {
	
	@Test
	public void test_string(){
		System.out.println(String.format("%s_%s","logcat","a123456"));
		TreeSet<AndroidDevice> devices = AndroidDeviceStore.getInstance()
		        .getDevices();

		for (AndroidDevice d : devices) {
		  System.out.println(d.getSerialNumber());
		}
		AndroidDevice device = devices.pollFirst();
		System.out.println(device.getName());
		
		System.out.println(device.getName().substring(0,device.getName().lastIndexOf("-")));
		
		System.out.println(device.getDevice().getProperty("ro.build.version.release"));
		
//		BackgroundLogcatAction logcatAction = new BackgroundLogcatAction("logcat_"+device.getName(), device, "", "", "", "", "");
//		logcatAction.start();
//		int i=0;
//		while(true){
//			try {
//				Thread.sleep(10000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			i++;
//			System.out.println("test_devicelog running...");
//			if(i>5){
//				logcatAction.close();
//				break;
//			}
//		}
	}
}
