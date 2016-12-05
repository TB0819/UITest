package appiumtest.appiumtest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.testng.annotations.Test;

public class FellTest {
	
//	@Test(description="no zuo no dai")
	public void test_print(){
		System.out.println("haha");
	}
	
	@Test
	public void test_stringprint() throws MalformedURLException{
		ArrayList<String> items = new ArrayList<String>();
		String pack="appiumtest.appiumtest,com.appium.test";
		Collections.addAll(items, pack.split("\\s*,\\s*"));
		URL newUrl = null;
		List<URL> newUrls = new ArrayList<URL>();
		int a = 0;
		Collection<URL> urls = ClasspathHelper.forPackage(items.get(a));

		Iterator<URL> iter = urls.iterator();
		URL url = iter.next();
		urls.clear();
		for (int i = 0; i < items.size(); i++) {
			newUrl = new URL(url.toString() + items.get(i).replaceAll("\\.", "/"));
			newUrls.add(newUrl);
			a++;
		}
		
		Reflections reflections = new Reflections(
				new ConfigurationBuilder().setUrls(newUrls).setScanners(new MethodAnnotationsScanner()));
		Set<Method> resources = reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class);
		createTestsMap(resources);
//		for(URL u: newUrls){
//			System.out.println(u.toString());
//		}
	}

	private Map<String, List<Method>> createTestsMap(Set<Method> methods) {
		Map<String, List<Method>> testsMap = new HashMap<String, List<Method>>();
		methods.stream().forEach(method -> {
			List<Method> methodsList = testsMap.get(method.getDeclaringClass().getPackage().getName() + "."
					+ method.getDeclaringClass().getSimpleName());
			if (methodsList == null) {
				methodsList = new ArrayList<>();
				testsMap.put(method.getDeclaringClass().getPackage().getName() + "."
						+ method.getDeclaringClass().getSimpleName(), methodsList);
			}
			methodsList.add(method);
		});
		
		for(String s:testsMap.keySet()){
			System.out.println(s);
		}
		return testsMap;
	}

}
