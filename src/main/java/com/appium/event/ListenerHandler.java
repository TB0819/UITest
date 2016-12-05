package com.appium.event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.support.events.WebDriverEventListener;

import io.appium.java_client.events.api.Listener;

public class ListenerHandler implements InvocationHandler {
	private final List<Listener> listeners;

	ListenerHandler(List<Listener> listeners) {
        this.listeners = listeners;
    }
	
	private Method findElementInWebDriverEventListener(Method m) {
        try {
            return WebDriverEventListener.class.getMethod(m.getName(), m.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		for (Listener l: listeners) {
            boolean isInvoked = false;
            if (method.getDeclaringClass().isAssignableFrom(l.getClass())) {
                method.invoke(l, args);
                isInvoked = true;
            }

            if (isInvoked) {
                continue;
            }

            Method webDriverEventListenerMethod = findElementInWebDriverEventListener(method);
            if (webDriverEventListenerMethod != null
                && WebDriverEventListener.class.isAssignableFrom(l.getClass())) {
                webDriverEventListenerMethod.invoke(l, args);
            }
        }
        return null;
	}

}
