package com.appium.casesutie;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.test.tools.BaseTest;

public class RunTestCases extends BaseTest{
	
	@DataProvider
	public  Iterator<Object[]> loadCaseFile(){
		List<Object[]> dataToBeReturned = new ArrayList<Object[]>();
		LoadResources fr = new LoadResources();
		fr.loadTestCases(CommonData.getInstance().prop.getProperty("CASE_PATH"));
		for(TestCase testcase : LoadResources.TestCases.values()){
			dataToBeReturned.add(new Object[]{testcase});
		}
		return dataToBeReturned.iterator();
	}
	
	@Test(dataProvider="loadCaseFile")
	public void runAllTestClass(TestCase testcase) throws Exception{
		System.out.println("测试用例："+testcase.getCaseName());
		for(Feature f: testcase.getFeatures()){
			System.out.println("模块："+f.getFeatureName());
			for(Step step: LoadResources.allfeature.get(f.getFeatureName()).getSteps()){
			  System.out.println("\t"+step.description+"\t"+step.control_action+"\t"+step.element_value+"\t"+step.findelement_type);
			}
		}
//		Operate operate = new Operate();
//		for(Step step: feature.getSteps()){
//			  System.err.println("\t"+step.description+"\t"+step.control_action+"\t"+step.element_value+"\t"+step.findelement_type);
//			  step.setDriver(driver);
//			  operate.executeOperate(step);
//		  }
	}
}
