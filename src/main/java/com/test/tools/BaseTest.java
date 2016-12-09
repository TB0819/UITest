package com.test.tools;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import com.appium.manager.AppiumParallel;


/**
 * Test base class, the implementation of appiumservice startup, moblie
 * information settings, all test classes need to inherit the class
 * 
 * @author Administrator
 *
 */
public class BaseTest extends AppiumParallel{
	
	@BeforeClass(alwaysRun=true) 
	public void beforeClass() throws Exception {
		startAppiumService();
		driver = createAppiumDriverInParallel();
    }
	
	@BeforeMethod() 
	public void startApp(Method method) throws Exception {
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		startADBLog(method.getName());
	}
	
    @AfterMethod() 
    public void killServer(ITestResult result) throws InterruptedException, IOException {
    	endADBLog(result);
    	driver.closeApp();
    	driver.launchApp();
    }
    
    @AfterClass(alwaysRun=true)
    public void afterClass() throws InterruptedException, IOException{
    	driver.quit();
        killAppiumServer();
    }
    
    
}
