package com.eLearning.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.eLearning.beans.Course;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

@Component
public class JsonFileReaderUtil {
	
	
	
    List<Course> courseList = new ArrayList<>();
    
    private static final String FILENAME = new ApplicationProperties().getApplicationProperties("url");
	
	public List<Course> jsonFileReader() throws IOException{
		
		File courseFile = new File(FILENAME);
		InputStream stream = new FileInputStream(courseFile);
		
		JsonReader reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
        Gson gson = new GsonBuilder().create();
		
		try{

	        // Read file in stream mode
	        reader.beginArray();
	        while (reader.hasNext()) {
	        	Course course = gson.fromJson(reader, Course.class);
	        	System.out.println(course.getGraphic().getSrc());
	        	courseList.add(course);
	        }
			return courseList;
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			reader.close();
			stream.close();
		}
		return null;
	}
}
