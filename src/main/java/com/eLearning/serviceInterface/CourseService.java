package com.eLearning.serviceInterface;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eLearning.beans.Course;

@Service
public interface CourseService  {
	
	public List<Course> getAllCourses();
	
	public boolean createCourse(Course course, String username);
	
	public boolean deleteCourse(String id, String username);
	
	public boolean updateCourse(Course course, String username);
	
	public Course getCourseById(String id); 
	
	public boolean validateCourse(Course course);
	
	public int getTotalCourses();
}
