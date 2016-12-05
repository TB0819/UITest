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
		FeatureResources fr = new FeatureResources();
		fr.loadAllFeature(CommonData.getInstance().prop.getProperty("FEATURE_PATH"));
		for(Feature feature : FeatureResources.allfeature.values()){
			dataToBeReturned.add(new Object[]{feature});
		}
		return dataToBeReturned.iterator();
	}
	
	@Test(dataProvider="loadCaseFile")
	public void runAllTestClass(Feature feature) throws Exception{
		Operate operate = new Operate();
		for(Step step: feature.getSteps()){
			  System.err.println("\t"+step.description+"\t"+step.control_action+"\t"+step.element_value+"\t"+step.findelement_type);
			  step.setDriver(driver);
			  operate.executeOperate(step);
		  }
	}
}
