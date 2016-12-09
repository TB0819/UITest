package com.appium.casesutie;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.appium.utils.LogUtil;

public class LoadResources {
	public static Map<String, Feature> allfeature = new HashMap<String, Feature>();
	public static List<TestCase> TestCases = new ArrayList<TestCase>();
	private List<String> fileList = new ArrayList<String>();

	public void loadAllFeatures(String filepath) {
		File file = new File(filepath);
		List<String> arrayList = getlistFile(file);

		Constructor constructor = new Constructor(Feature.class);
		TypeDescription caseDescription = new TypeDescription(Feature.class);
		caseDescription.putListPropertyType("steps", Step.class);
		constructor.addTypeDescription(caseDescription);
		Yaml yaml = new Yaml(constructor);

		for (String s : arrayList) {
			try{
				Feature feature = (Feature) yaml.load(Util.getLocalResource(s));
				allfeature.put(feature.getFeatureName(), feature);
			}catch(Exception e){
				LogUtil.messages.add(s+"格式错误\n"+e.toString());
				LogUtil.printLog("INFO");
			}
		}
	}

	public void loadTestCases(String filepath) {
		File file = new File(filepath);
		List<String> arrayList = getlistFile(file);

		Constructor constructor = new Constructor(TestCase.class);
		TypeDescription caseDescription = new TypeDescription(TestCase.class);
		caseDescription.putListPropertyType("features", Feature.class);
		constructor.addTypeDescription(caseDescription);
		Yaml yaml = new Yaml(constructor);

		for (String s : arrayList) {
			try{
				TestCase testcase = (TestCase) yaml.load(Util.getLocalResource(s));
				TestCases.add(testcase);
			}catch(Exception e){
				LogUtil.messages.add(s+"格式错误\n"+e.toString());
				LogUtil.printLog("INFO");
			}
		}
	}

	private List<String> getlistFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
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

	public void isTestCaseFile(File file) {
		String filepath = file.getAbsolutePath();
		if (filepath.endsWith(".yml") || filepath.endsWith(".yml")) {
			System.out.println("yaml format");
		} else {
			System.out.println("Only support yaml and csv format!");
		}
	}
}
