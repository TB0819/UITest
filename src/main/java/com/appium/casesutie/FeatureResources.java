package com.appium.casesutie;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class FeatureResources {
	public static Map<String, Feature> allfeature = new HashMap<String, Feature>();
	private List<String> fileList = new ArrayList<String>();
	private Yaml yaml = new Yaml(new Constructor(Feature.class));

	public void loadAllFeature(String filepath) {
		File file = new File(filepath);
		List<String> arrayList = getlistFile(file);
		for(String s: arrayList){
			Feature feature = (Feature) yaml.load(Util.getLocalResource(s));
			allfeature.put(feature.getFeatureName(), feature);
		}
	}
	
	private List<String> getlistFile(File file){
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for(File f: files){
				getlistFile(f);
			}
		} else {
			String filepath = file.getAbsolutePath();
			if (filepath.endsWith(".yaml") || filepath.endsWith(".yml")) {
				fileList.add(filepath.replace("\\", "/"));
			}
		}
		return fileList;
	}
	
//	@Test
	public void testExample(){
		FeatureResources features = new FeatureResources();
		features.loadAllFeature(CommonData.getInstance().prop.getProperty("FEATURE_PATH"));
		for (Map.Entry<String, Feature> entry : FeatureResources.allfeature.entrySet()) {  
			  
		    System.out.println("Key = " + entry.getKey());  
		  for(Step step: entry.getValue().getSteps()){
			  System.out.println("\t"+step.description);
			  System.out.println("\t"+step.control_action);
			  System.out.println("\t"+step.element_value);
			  System.out.println("\t"+step.findelement_type);
		  }
		}
	}
	
	@Test
	public void testTyoeSafeList(){
		Constructor constructor = new Constructor(TestCase.class);
		TypeDescription caseDescription = new TypeDescription(TestCase.class);
		caseDescription.putListPropertyType("features", Feature.class);
		constructor.addTypeDescription(caseDescription);
		Yaml yaml = new Yaml(constructor);
		TestCase case1 = (TestCase) yaml.load(Util.getLocalResource("D:\\test_case\\case\\case1.yaml"));
		System.out.println(case1.getCaseName());
		for(Feature f: case1.getFeatures()){
			System.out.println("\t"+f.getFeatureName());
		}
	}
	
//	@Test
	public void test1(){
		Yaml yaml = new Yaml(new Constructor(TestCase.class));
		TestCase case1 = (TestCase) yaml.load(Util.getLocalResource("C:\\Users\\Administrator\\Desktop\\testcase.yaml"));
		System.out.println(case1.getCaseName());
	}
	
	public void isTestCaseFile(File file){
		if(file.getAbsolutePath().endsWith(".yml")){
			System.out.println("yaml format");
		}else{
			System.out.println("Only support yaml and csv format!");
		}
	}
}
