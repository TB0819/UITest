package com.appium.manager;

import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.appium.casesutie.CommonData;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServiceManager {
	AvailabelPorts ap = new AvailabelPorts();
	private Properties prop = CommonData.getInstance().prop;
	private AppiumDriverLocalService appiumDriverLocalService;

	// 开启appium service服务
	public void appiumServerForAndroid(String UDID, String methodName) throws Exception {
		System.out.println("**************************************************************************\n");
		System.out.println("Starting Appium Server to handle Android Device :" + UDID + "\n");
		System.out.println("**************************************************************************\n");

		prop.load(new FileInputStream("config.properties"));
		Map<String, String> shellParam = new HashMap<String, String>();
		shellParam.put("-U", UDID);
		// 获取appium服务随机端口
		int port = ap.getPort();
		int chromePort = ap.getPort();
		int bootstrapPort = ap.getPort();
		int selendroidPort = ap.getPort();
		AppiumServiceBuilder builder = new AppiumServiceBuilder()
				.withAppiumJS(new File(prop.getProperty("APPIUM_JS_PATH")))
				.withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
				.withLogFile(new File(System.getProperty("user.dir") + "/target/appiumlogs/"
						+ UDID.replaceAll("\\W", "_") + "__"+"3"  + ".txt"))
				.withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, Integer.toString(chromePort))
				.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, Integer.toString(bootstrapPort))
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				.withArgument(AndroidServerFlag.SUPPRESS_ADB_KILL_SERVER)
				.withEnvironment(shellParam)
				.withArgument(AndroidServerFlag.SELENDROID_PORT, Integer.toString(selendroidPort))
				.usingPort(port);
		appiumDriverLocalService = builder.build();
		appiumDriverLocalService.start();
	}

	//得到appium service地址
	public URL getAppiumUrl() {
		return appiumDriverLocalService.getUrl();
	}

	//关闭appium service服务
	public void destroyAppiumNode() {
		appiumDriverLocalService.stop();
		if (appiumDriverLocalService.isRunning()) {
			System.out.println("**************************************************************************\n");
			System.out.println("AppiumServer didn't shut... Trying to quit again....\n");
			System.out.println("**************************************************************************\n");
			appiumDriverLocalService.stop();
		}
	}

	/**
	 * Generates Random ports Used during starting appium server
	 * @author Administrator
	 *
	 */
	class AvailabelPorts {
		public int getPort() throws Exception {
			ServerSocket socket = new ServerSocket(0);
			socket.setReuseAddress(true);
			int port = socket.getLocalPort();
			socket.close();
			return port;
		}
	}
}
