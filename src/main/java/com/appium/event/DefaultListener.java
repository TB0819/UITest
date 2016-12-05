package com.appium.event;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import io.appium.java_client.events.api.Listener;

public class DefaultListener implements ActionListener,ControlListener{
	private final List<Listener> listeners = new ArrayList<>();
	 private Object dispatcher  = Proxy.newProxyInstance(Listener.class.getClassLoader(),
		        new Class[] {ActionListener.class,ControlListener.class},
		        new ListenerHandler(listeners));

	@Override
	public void beforeFindBy() {
		((ControlListener) dispatcher).beforeFindBy();
	}

	@Override
	public void afterFindBy() {
		((ControlListener) dispatcher).afterFindBy();
	}

	@Override
	public void beforeClickOn() {
		((ActionListener)dispatcher).beforeClickOn();
	}

	@Override
	public void afterClickOn() {
		((ActionListener)dispatcher).afterClickOn();
	}

}
