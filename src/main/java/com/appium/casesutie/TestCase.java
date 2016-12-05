package com.appium.casesutie;

import java.util.List;

public class TestCase {
	private String caseName;
	private List<Feature> features;
	
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public List<Feature> getFeatures() {
		return features;
	}
	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
}
