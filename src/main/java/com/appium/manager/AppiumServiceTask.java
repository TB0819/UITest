package com.appium.manager;

import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class AppiumServiceTask implements Runnable {
	private String UDID;
	private String methodName;
	private AppiumDriverLocalService mService;
	private Properties properties = new Properties();

	public AppiumServiceTask(String UDID, String methodName) {
		this.UDID = UDID;
		this.methodName = methodName;
	}

	public void run() {
		try {
			startAppiumServerNode();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 开启appium service服务
	public void startAppiumServerNode() throws Exception {
		System.out.println("**************************************************************************\n");
		System.out.println("Starting Appium Server to handle Android Device :" + UDID + "\n");
		System.out.println("**************************************************************************\n");

		properties.load(new FileInputStream("config.properties"));
		Map<String, String> shellParam = new HashMap<String, String>();
		shellParam.put("-U", UDID);
		// 获取appium服务随机端口
		AvailabelPorts ap = new AvailabelPorts();
		int chromePort = ap.getPort();
		int bootstrapPort = ap.getPort();
		int selendroidPort = ap.getPort();
		AppiumServiceBuilder builder = new AppiumServiceBuilder()
				.withAppiumJS(new File(properties.getProperty("APPIUM_JS_PATH")))
				.withArgument(GeneralServerFlag.LOG_LEVEL, "debug")
				.withLogFile(new File(System.getProperty("user.dir") + "/target/appiumlogs/"
						+ UDID.replaceAll("\\W", "_") + "__" + methodName + ".txt"))
				.withArgument(AndroidServerFlag.CHROME_DRIVER_PORT, Integer.toString(chromePort))
				.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, Integer.toString(bootstrapPort))
				.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
				.withArgument(AndroidServerFlag.SUPPRESS_ADB_KILL_SERVER).withEnvironment(shellParam)
				.withArgument(AndroidServerFlag.SELENDROID_PORT, Integer.toString(selendroidPort))
				.usingAnyFreePort();
		
		mService = builder.build();
		mService.start();
	}

	// 得到appium service地址
	public URL getAppiumUrl() {
		return mService.getUrl();
	}

	// 关闭appium service服务
	public void destroyAppiumServerNode() {
		mService.stop();
		if (mService.isRunning()) {
			System.out.println("**************************************************************************\n");
			System.out.println("AppiumServer didn't shut... Trying to quit again....\n");
			System.out.println("**************************************************************************\n");
			mService.stop();
		}
	}

	/**
	 * Generates Random ports Used during starting appium server
	 * 
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
