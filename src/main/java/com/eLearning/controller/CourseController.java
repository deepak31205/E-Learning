package com.eLearning.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eLearning.beans.Course;
import com.eLearning.serviceImplementation.JsonFileToDBInsertion;
import com.eLearning.serviceInterface.CourseRepo;
import com.eLearning.serviceInterface.CourseService;
import com.google.gson.Gson;

@RestController
public class CourseController {

	@Autowired
	private Gson gson;

	@Autowired
	private JsonFileToDBInsertion jsonFileToDBInsertion;

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseRepo courseRepo;
	
	private String username;

	
	/*This end point returns the paginated list as per 
	  the page no and size sepcified in the request*/
	@RequestMapping(value = "/course/getAllCourses", method = RequestMethod.GET, produces = "application/json")
	public String getCourses(Pageable pageable) {
		
		List<Course> courseList =  courseRepo.findAll(pageable).getContent();
		String response = gson.toJson(courseList);
		return response;
	}

	/*This end point returns the object of course as per the id comes in request*/
	@RequestMapping(value = "/course/getCourseById", method = RequestMethod.GET, produces = "application/json")
	public String getCourseById(@RequestParam String id) {
		Course course = courseService.getCourseById(id);
		if(course != null){
			String courseJson = gson.toJson(course);
			return courseJson;
		}
		else{
			return "{\"message\": \"Something went wrong please try again\", \"response_code\": \"500\"}";
		}
	}

	/*This end point create a new course and save it in database as per the new course object comes in request*/
	@RequestMapping(value = "/course/add", method = RequestMethod.POST, produces = "application/json")
	public String createCourse(@RequestBody String request) {

		System.out.println(request);
		
		if (request == null) {
			return "{\"message\": \"No Input found\", \"response_code\": \"404\"}";
		}

		if(username == null){
			username = getUserName();
		}
		
		Course course = gson.fromJson(request, Course.class);
		boolean isValid = courseService.validateCourse(course);

		if (isValid) {
			boolean result = courseService.createCourse(course, username);
			if (result) {
				return "{\"message\": \"Course registered successfully\", \"response_code\": \"200\"}";
			} else {
				return "{\"message\": \"Something went wrong please try again\", \"response_code\": \"500\"}";
			}
		} else {
			return "{\"message\": \"Inputs invalid\", \"response_code\": \"404\"}";
		}

	}

	/*This end point delete the course as per the id comes in request*/
	@RequestMapping(value = "/course/delete", method = RequestMethod.GET, produces = "application/json")
	public String deleteCourse(@RequestParam String id) {
		
		if(id == null){
			return "{\"message\": \"No Input found\", \"response_code\": \"404\"}";
		}
		
		if(username == null){
			username = getUserName();
		}
		
		boolean result = courseService.deleteCourse(id, username);

		if (result) {
			return "{\"message\": \"Course deleted successfully\", \"response_code\": \"200\"}";
		} else {
			return "{\"message\": \"Something went wrong please try again\", \"response_code\": \"500\"}";
		}
	}

	/*This end point updates the course as per the id and the updates course object comes in the request*/
	@RequestMapping(value = "/course/update", method = RequestMethod.POST, produces = "application/json")
	public String updateCourse(@RequestBody String request) {

		if(request == null || request == ""){
			return "{\"message\": \"No Input found\", \"response_code\": \"404\"}";
		}
		
		if(username == null){
			username = getUserName();
		}
		
		Course course = gson.fromJson(request, Course.class);
		boolean isValid = courseService.validateCourse(course);
		
		if (isValid) {
			courseService.updateCourse(course, username);
			return "{\"message\": \"Course upudated successfully\", \"response_code\": \"200\"}";
		} else {
			return "{\"message\": \"Inputs invalid\", \"response_code\": \"404\"}";
		}
	}

	/*This end point is to initialize the database through reading the json file and inserting the data
	  into the database*/
	@RequestMapping(value = "/course/addFileData", method = RequestMethod.GET, produces = "application/json")
	public void addFileData() {
		
		if(username == null){
			username = getUserName();
		}
		jsonFileToDBInsertion.insertDataInDB(username);
	}
	
	/*This end point is to get the count of all the courses present*/
	@RequestMapping(value = "/course/getTotalCourses", method = RequestMethod.GET, produces = "application/json")
	public int getTotalCourses() {
		
		int total = courseService.getTotalCourses();
		return total;
	}
	
	/*This end point is to get which user is logged in the application*/
	@RequestMapping(value = "/course/getUserRole", method = RequestMethod.GET, produces = "application/json")
	public String getUserRole() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String result = auth.getAuthorities().iterator().next().getAuthority();
		username = auth.getName();
	    return "{\"data\": \""+result+ "\"}";
	}
	
	public String getUserName(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		username = auth.getName();
		return username;
	}
}
