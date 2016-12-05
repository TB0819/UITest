package com.appium.casesutie;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
	 public static String getLocalResource(String theName) {
        try {
        	FileInputStream file;
        	BufferedReader reader;
        	InputStreamReader inputFileReader;
        	file = new FileInputStream(theName);
        	inputFileReader = new InputStreamReader(file, "utf-8");
        	reader = new BufferedReader(inputFileReader);
        	String tempString = null;
        	StringBuilder buf = new StringBuilder();
        	try{
        		while ((tempString = reader.readLine()) != null) {
	        		buf.append(tempString);
	        		buf.append("\n");
	            }
        	}finally{
        		reader.close();
        	}
            String resource = buf.toString();
            // convert EOLs
            String[] lines = resource.split("\\r?\\n");
            StringBuilder buffer = new StringBuilder();
            for (int j = 0; j < lines.length; j++) {
                buffer.append(lines[j]);
                buffer.append("\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
