package com.appium.casesutie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.appium.report.factory.ExtentTestManager;
import com.appium.utils.LogUtil;
import com.aventstack.extentreports.Status;
import com.test.tools.BaseTest;

public class RunTestCases extends BaseTest{
	
	@DataProvider
	public  Iterator<Object[]> loadCaseFile(){
		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
		LoadResources fr = new LoadResources();
		fr.loadTestCases(CommonData.getInstance().prop.getProperty("CASE_PATH"));
		for(TestCase testcase : LoadResources.TestCases){
			dataToBeReturned.add(new Object[]{testcase});
		}
		return dataToBeReturned.iterator();
	}
	
	@Test(dataProvider="loadCaseFile")
	public void runAllTestClass(TestCase testcase) throws Exception{
		//报告格式
		currentCaseName = testcase.getCaseName();
		parent = ExtentTestManager.createTest(currentCaseName, "Mobile Appium Test",category );
        parentContext.put(Thread.currentThread().getId(), parent);
		child = ExtentTestManager.getTest().createNode(currentCaseName);
		
		Operate operate = new Operate();
		LogUtil.messages.add("测试用例："+testcase.getCaseName());
		LogUtil.printLog("DEBUG");
		for(Feature f: testcase.getFeatures()){
			LogUtil.messages.add("模块："+f.getFeatureName());
			LogUtil.printLog("DEBUG");
			for(Step step: LoadResources.allfeature.get(f.getFeatureName()).getSteps()){
			  step.setDriver(driver);
			  try{
				  operate.executeOperate(step);
				  child.log(Status.PASS, "\t"+step.description+"\t"+step.control_action+"\t"+step.element_value+"\t"+step.findelement_type);
			  }catch(Exception e){
				  child.log(Status.FAIL, "\t"+step.description+"\t"+step.control_action+"\t"+step.element_value+"\t"+step.findelement_type);
				  throw new RuntimeException(e);
			  }
			}
		}
	}
}
