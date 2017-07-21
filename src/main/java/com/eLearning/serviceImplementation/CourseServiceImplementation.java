package com.eLearning.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eLearning.beans.Course;
import com.eLearning.serviceInterface.CourseDAO;
import com.eLearning.serviceInterface.CourseService;

@Service
public class CourseServiceImplementation implements CourseService{
	
	@Autowired
	private CourseDAO courseDAO;
	
	@Override
	public List<Course> getAllCourses()
    {
        return this.courseDAO.getAllCourses();
    }
    
	@Override
	public int getTotalCourses()
    {
        return this.courseDAO.getTotalCourses();
    }
	
	@Override
    public boolean createCourse(Course course, String username)
    {
        return this.courseDAO.createCourse(course, username);
    }
    
	@Override
    public boolean deleteCourse(String id, String username){
    	return courseDAO.deleteCourse(id, username);
    }
    
	@Override
    public boolean updateCourse(Course course, String username){
    	return courseDAO.updateCourse(course, username);
    }
    
	@Override
    public Course getCourseById(String id){
    	return courseDAO.getCourseById(id);
    }
    
	/*To validate all the fields against any malformed input*/
	@Override
    public boolean validateCourse(Course course){
    	int error = 0;
    	if(!course.getTitle().matches("[a-zA-Z0-9' '.-]*") 
    			|| course.getTitle().equals("") 
    			|| course.getTitle() == null)
			error++;
    	
    	if(!course.getSkill().matches("[a-zA-Z]*") 
    			|| course.getSkill().equals("") 
    			|| course.getSkill() == null)
			error++;
    	
    	if(!course.getLanguage().matches("[a-zA-Z]*") 
    			|| course.getLanguage().equals("") 
    			|| course.getLanguage() == null)
			error++;
    	
    	if(!course.getGraphic().getAlt().matches("[a-zA-Z0-9' '.-]*") 
    			|| course.getGraphic().getAlt().equals("") 
    			|| course.getGraphic().getAlt() == null)
			error++;
    	
    	if(course.getGraphic().getSrc().matches("[}{)(><]*") 
    			|| course.getGraphic().getSrc().equals("") 
    			|| course.getGraphic().getSrc() == null)
			error++;
    	
    	if(!course.getTime().matches("[0-9]*") 
    			|| course.getTime().equals("") 
    			|| course.getTime() == null)
			error++;
    	
    	if(error == 0){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
}
