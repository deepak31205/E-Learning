package com.eLearning.serviceImplementation;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eLearning.beans.Course;
import com.eLearning.serviceInterface.CourseService;
import com.eLearning.util.JsonFileReaderUtil;


@Service
public class JsonFileToDBInsertion {
	
	@Autowired
	private JsonFileReaderUtil jsonFileReaderUtil;
	
	@Autowired
	private CourseService courseService;
	
	
	/*Using the json file reader util reads the json file
	  and store all the objects in the database*/
	public void insertDataInDB(String username){
		
		try {
			List<Course> courseList = jsonFileReaderUtil.jsonFileReader();
			
			for(Course course : courseList){
				if(course != null){
					courseService.createCourse(course,username);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
