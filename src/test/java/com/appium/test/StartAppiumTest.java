package com.appium.test;

import org.testng.annotations.Test;
import com.appium.manager.ParallelThread;

public class StartAppiumTest {
	
	@Test
	public void testRunner() throws Exception{
		ParallelThread parallelThread = new ParallelThread();
		parallelThread.runner();
	}
}
