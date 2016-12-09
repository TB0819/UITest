package com.appium.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;

import com.appium.casesutie.CommonData;
import com.appium.devices.BackgroundLogcatAction;
import com.appium.report.factory.ExtentManager;
import com.appium.report.factory.ExtentTestManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.github.cosysoft.device.android.AndroidDevice;
import com.github.cosysoft.device.android.impl.AndroidDeviceStore;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class AppiumParallel {
	private static final String LOGCAT_DESC = "logcat";
	public String category = null;
	public AndroidDriver<MobileElement> driver = null;
	public AndroidDevice currentDevice;
	public String mdeviceName;
	public String mdevice_udid;
	public String mdeviceVersion;
	
	public ExtentTest parent;
	public ExtentTest child;
	public String currentCaseName;
	public Map<Long, ExtentTest> parentContext = new HashMap<Long, ExtentTest>();
	
	private AppiumServiceManager mAppiumManager = new AppiumServiceManager();
	private BackgroundLogcatAction mlogcat = null;
	public static Properties prop = CommonData.getInstance().prop;
	public static ConcurrentHashMap<AndroidDevice, Boolean> deviceMapping = new ConcurrentHashMap<AndroidDevice, Boolean>();
	public static AndroidDeviceStore deviceStore = AndroidDeviceStore.getInstance();

	// 加载可用的设备
	static {
		try {
			if (deviceStore.getDevices().size() > 0) {
				System.out.println("Adding Android devices");
				for (AndroidDevice device : deviceStore.getDevices()) {
					deviceMapping.put(device, true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to initialize framework");
		}
	}

	// 并行创建driver session
	public synchronized AndroidDriver<MobileElement> createAppiumDriverInParallel() throws Exception {
		driverInstance();
		return driver;
	}

	public void driverInstance() {
		if (prop.getProperty("APP_TYPE").equalsIgnoreCase("web")) {
			driver = new AndroidDriver<>(mAppiumManager.getAppiumUrl(), androidWeb());
		} else {
			if (Platform.getCurrent().is(Platform.MAC)) {

			} else {
				driver = new AndroidDriver<MobileElement>(mAppiumManager.getAppiumUrl(), androidNativeCapabilities());
			}
		}
	}

	public AppiumDriver<MobileElement> getDriver() {
		return driver;
	}

	/**
	 * Setting Android Desired Capabilities
	 * 
	 * @return
	 */
	public synchronized DesiredCapabilities androidNativeCapabilities() {
		DesiredCapabilities androidCapabilities = new DesiredCapabilities();
		androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		androidCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, mdeviceName);
		androidCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, mdeviceVersion);
		androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, prop.getProperty("APP_ACTIVITY"));
		androidCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, prop.getProperty("APP_PACKAGE"));
		androidCapabilities.setCapability("browserName", "");
		// androidCapabilities.setCapability(MobileCapabilityType.APP,
		// prop.getProperty("ANDROID_APP_PATH"));
		checkSelendroid(androidCapabilities);
		androidCapabilities.setCapability(MobileCapabilityType.UDID, mdevice_udid);
		return androidCapabilities;
	}

	/**
	 * Setting Android web Desired Capabilities
	 * 
	 * @return
	 */
	private synchronized DesiredCapabilities androidWeb() {
		DesiredCapabilities androidWebCapabilities = new DesiredCapabilities();
		androidWebCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, mdeviceName);
		androidWebCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		androidWebCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, mdeviceVersion);
		// If you want the tests on real device, make sure chrome browser is
		// installed
		androidWebCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, prop.getProperty("BROWSER_TYPE"));
		androidWebCapabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, true);
		return androidWebCapabilities;
	}

    public DesiredCapabilities checkSelendroid(DesiredCapabilities androidCaps) {
        int android_api = 0;
        try {
            android_api = Integer.parseInt(deviceStore.getDeviceBySerial(mdevice_udid).getDevice().getProperty("ro.build.version.sdk"));
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Android API Level::" + android_api);
        if (android_api <= 16) {
            androidCaps.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Selendroid");
        }
        return androidCaps;
    }
    
	// get device Android version
	public String getDeviceVersion() {
		return deviceStore.getDeviceBySerial(mdevice_udid).getDevice().getProperty("ro.build.version.release");
	}

	public String getDeviceName() {
		String model = deviceStore.getDeviceBySerial(mdevice_udid).getName();
		return model.substring(0, model.lastIndexOf("-"));
	}

	/**
	 * Start appium service
	 * 
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public synchronized void startAppiumService() throws Exception {
		currentDevice = getNextAvailableDevice();
		mdevice_udid = currentDevice.getSerialNumber();
		mdeviceName = getDeviceName();
		mdeviceVersion = getDeviceVersion();
		if (currentDevice == null) {
			System.out.println("No devices are free to run test or Failed to run test");
		}

		if (Platform.getCurrent().is(Platform.MAC)) {

		} else {
			category = currentDevice.getName();
		}
//        ExtentTestManager.getTest().log(LogStatus.INFO, "AppiumServerLogs",
//                "<a href=" + System.getProperty("user.dir") + "/target/appiumlogs/" + mdevice_udid
//                    .replaceAll("\\W", "_") + "__" + methodName + ".txt" + ">Logs</a>");
            
		if (Platform.getCurrent().is(Platform.MAC)) {

		} else {
			mAppiumManager.appiumServerForAndroid(mdevice_udid, currentCaseName);
		}
	}
	
	/**
	 * Close appiumservice
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public synchronized void killAppiumServer() throws InterruptedException, IOException {
		System.out.println("**************ClosingAppiumSession****************" + Thread.currentThread().getId());
        ExtentManager.getInstance().flush();
		mAppiumManager.destroyAppiumNode();
		freeDevice(currentDevice);
	}

	public static void freeDevice(AndroidDevice device) {
		deviceMapping.put(device, true);
	}

	/**
	 * return Next Available Device
	 * 
	 * @return
	 */
	public static synchronized AndroidDevice getNextAvailableDevice() {
		ConcurrentHashMap.KeySetView<AndroidDevice, Boolean> devices = deviceMapping.keySet();
		int i = 0;
		for (AndroidDevice device : devices) {
			Thread t = Thread.currentThread();
			t.setName("Thread_" + i);
			System.out.println("Parallel Thread is: " + t.getName()+"_"+t.getId());
			i++;
			if (deviceMapping.get(device) == true) {
				deviceMapping.put(device, false);
				return device;
			}
		}
		return null;
	}

	// 开启Android adb logcat日志
	public void startADBLog(String methodName) throws Exception {
		if (deviceStore.getDevices().size() > 0) {
			System.out.println("Starting ADB logs:" + mdevice_udid);
			AndroidDevice device = deviceStore.getDeviceBySerial(mdevice_udid);
			mlogcat = new BackgroundLogcatAction(LOGCAT_DESC + "_" + methodName, device, "", "", "", "", "");
			mlogcat.start();
		}
	}

	// 关闭Android adb logcat日志
	public void endADBLog(ITestResult result) throws InterruptedException, IOException {
		mlogcat.close();
		if (result.getStatus() == ITestResult.SUCCESS) {
			ExtentTestManager.getTest().log(Status.PASS, currentCaseName);
			
//			 "ADBLogs:: <a href=" + System.getProperty("user.dir") + "/target/adblogs/"
//             + mdevice_udid.replaceAll("\\W", "_") + "__" + result.getMethod()
//             .getMethodName() + ".txt" + ">AdbLogs</a>"
		}
		else if(result.getStatus() == ITestResult.FAILURE){
			ExtentTestManager.getTest().log(Status.FAIL, result.getThrowable());
		}
		else if (result.getStatus() == ITestResult.SKIP) {
            ExtentTestManager.getTest().log(Status.SKIP, "Test skipped");
        }
		
        ExtentManager.getInstance().flush();
	}
}
