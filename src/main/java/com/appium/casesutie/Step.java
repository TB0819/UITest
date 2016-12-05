package com.appium.casesutie;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class Step {
	public String description;
	public String findelement_type;
	public String element_value;
	public String control_action;
	public String Data;
	public String expectation;
	public AndroidDriver<MobileElement> driver;
	
	public AndroidDriver<MobileElement> getDriver() {
		return driver;
	}
	public void setDriver(AndroidDriver<MobileElement> driver) {
		this.driver = driver;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFindelement_type() {
		return findelement_type;
	}
	public void setFindelement_type(String findelement_type) {
		this.findelement_type = findelement_type;
	}
	public String getElement_value() {
		return element_value;
	}
	public void setElement_value(String element_value) {
		this.element_value = element_value;
	}
	public String getControl_action() {
		return control_action;
	}
	public void setControl_action(String control_action) {
		this.control_action = control_action;
	}
	public String getData() {
		return Data;
	}
	public void setData(String data) {
		Data = data;
	}
	public String getExpectation() {
		return expectation;
	}
	public void setExpectation(String expectation) {
		this.expectation = expectation;
	}
}
