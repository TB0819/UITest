package appiumtest.appiumtest;

import org.testng.annotations.Test;

public class testdemo {

	@Test
	public void test_string(){
		System.out.println("new UiObject(new UiSelector().text(\""+"采购单"+"\"))");
		System.out.println("new UiSelector().resourceId(\""+"zmsoft.rest.supply:id/name"+"\").text(\""+"采购单"+"\")");
		String operSys = System.getProperty("os.name").toLowerCase();
		System.out.println(operSys);
		System.out.println("new UiScrollable(new UiSelector().resourceId(\""+"zmsoft.rest.supply:id/main_layout"+"\")).getChildByInstance(new UiSelector().className(\""+"android.widget.FrameLayout"+"\"), 0)");
	}
	
}
