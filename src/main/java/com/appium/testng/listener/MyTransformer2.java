package com.appium.testng.listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.testng.IAnnotationTransformer2;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.ITestAnnotation;

import com.testng.demo.TestCount;

public class MyTransformer2 implements IAnnotationTransformer2 {
	public int invocationCount;
	
	/**
	 * annotation 注解
	 * testClass 类
	 * testConstructor 构造函数
	 * testMethod 测试方法
	 */
	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		// TODO Auto-generated method stub
		invocationCount = TestCount.count;
		System.out.println(invocationCount);
		if ("test_invcount".equals(testMethod.getName())) {
			annotation.setInvocationCount(invocationCount);
		}
	}

	@Override
	public void transform(IConfigurationAnnotation annotation, Class testClass, Constructor testConstructor,
			Method testMethod) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transform(IDataProviderAnnotation annotation, Method method) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transform(IFactoryAnnotation annotation, Method method) {
		// TODO Auto-generated method stub
		
	}

}
