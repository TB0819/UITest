package com.appium.casesutie;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonData {

	public Properties prop;
	
	private CommonData(){
		loadConfig();
		loadAllFeatures();
	}
	
	private void loadConfig(){
		prop = new Properties();
		try {
			prop.load(new FileInputStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadAllFeatures() {
		LoadResources fr = new LoadResources();
		fr.loadAllFeatures(prop.getProperty("FEATURE_PATH"));
	}
	
	public static CommonData getInstance(){
		return CommonData.CommonDataHolder.instance;
	}
	
	private static class CommonDataHolder{
		private static CommonData instance = new CommonData();
	}
	
}
