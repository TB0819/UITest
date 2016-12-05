package com.appium.devices;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.android.ddmlib.Log.LogLevel;
import com.android.ddmlib.logcat.LogCatFilter;
import com.android.ddmlib.logcat.LogCatListener;
import com.android.ddmlib.logcat.LogCatMessage;
import com.github.cosysoft.device.android.AndroidDevice;

public class BackgroundLogcatAction extends Thread {
	private AndroidDevice device;
	private String name;
	private String tag;
	private String text;
	private String pid;
	private String appName;
	private File mFile;
	private PrintWriter printWriter;

	/**
	 * @param device - value for android device
	 * @param name - name for the filter
	 * @param tag - value for the logcat message's tag field.
	 * @param text - value for the logcat message's text field.
	 * @param pid - value for the logcat message's pid field.
	 * @param appName - value for the logcat message's app name field.
	 */
	public BackgroundLogcatAction(String prefix, AndroidDevice device, String name, String tag, String text, String pid,
			String appName) {
		this.device = device;
		this.name = name;
		this.tag = tag;
		this.text = text;
		this.pid = pid;
		this.appName = appName;
		mFile = createTempFile(prefix);
		setDaemon(true);
	}

	@Override
	public void run() {
		try {
			printWriter = new PrintWriter(mFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 设置logcat过滤
		final LogCatFilter filter = new LogCatFilter(name, tag, text, pid, appName, LogLevel.DEBUG);
		// 监听logcat消息
		final LogCatListener listener = new LogCatListener() {
			@Override
			public void log(List<LogCatMessage> msgList) {
				if (mFile != null) {
					for (LogCatMessage msg : msgList) {
						if (filter.matches(msg)) {
							printWriter.println(msg.toString());
						}
					}
				}
			}
		};
		// 装载监听
		device.addLogCatListener(listener);
	}

	private File createTempFile(String prefix) {
		try {
			File returnFile = File.createTempFile(prefix,".txt",
					new File(System.getProperty("user.dir") + "/target/adblogs/"));
			System.out.println("Created tmp file:" + returnFile.getAbsolutePath()+returnFile.getName());
			return returnFile;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void close() {
		if (printWriter != null) {
			printWriter.flush();
			printWriter.close();
		}
	}
}
