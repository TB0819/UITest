package com.appium.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.TreeSet;
import com.appium.casesutie.CommonData;
import com.github.cosysoft.device.android.AndroidDevice;
import com.github.cosysoft.device.android.impl.AndroidDeviceStore;

public class ParallelThread {
	protected int deviceCount = 0;
	public Properties prop = CommonData.getInstance().prop;
	public TreeSet<AndroidDevice> devices;
	AndroidDeviceStore deviceStore = AndroidDeviceStore.getInstance();
	TestExecutor testExecutor = new TestExecutor();

	public ParallelThread() throws IOException {
		prop.load(new FileInputStream("config.properties"));
	}
	
	public void runner() throws Exception {
		// create appiumlogs Directory
		mkDirectory("appiumlogs");
	
		if (deviceStore.getDevices().size() > 0) {
			deviceCount = deviceStore.getDevices().size();
			// create adblogs Directory
			mkDirectory("adblogs");
			// create Snapshot Directory
			mkSnapshotFolderAndroid("android");
		} else {
			System.out.println("No Android Devices Online");
			System.exit(0);
		}
	
		System.out.println("***************************************************\n");
		System.out.println("Total Number of devices detected :" + deviceCount + "\n");
		System.out.println("***************************************************\n");
		System.out.println("starting running tests in threads");
		
		testExecutor.runParallelAppium(deviceCount);
		
	}
	
	private void mkSnapshotFolderAndroid( String platform) {
		for (AndroidDevice device : deviceStore.getDevices()) {
			if (device != null) {
				mkDirectory("screenshot");
				mkDirectory("screenshot/" + platform);
				mkDirectory("screenshot/" + platform + "/" + device.getSerialNumber().replaceAll("\\W", "_"));
			}
		}
	}

	private void mkDirectory(String DirectoryName) {
		File f = new File(System.getProperty("user.dir") + "/target/" + DirectoryName + "/");
		if (!f.exists()) {
			System.out.println("creating directory: " + DirectoryName);
			try {
				f.mkdir();
			} catch (SecurityException se) {
				se.printStackTrace();
			}
		}
	}
}
