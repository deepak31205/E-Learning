package com.eLearning.serviceImplementation;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eLearning.beans.Course;
import com.eLearning.beans.UserLog;
import com.eLearning.serviceInterface.CourseDAO;

@Service
public class CourseDAOImplementation implements CourseDAO {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	
	/*To get the list of all the courses*/
	@Override
	public List<Course> getAllCourses() {
		return this.hibernateTemplate.loadAll(Course.class);
	}

	
	/*To create a new course using hibernate template*/
	@Override
	@Transactional(readOnly=false)
	public boolean createCourse(Course course, String username){
		try{
			course.setDate_created(new Date().toString());
			course.setLast_update(new Date().toString());
			this.hibernateTemplate.merge(course);
			logUser(username, "create");
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	/*To delete course using hibernate template*/
	@Override
	@Transactional(readOnly=false)
	public boolean deleteCourse(String id, String username){
		try{
			Course course = hibernateTemplate.get(Course.class, id);
			this.hibernateTemplate.delete(course.getGraphic());
			this.hibernateTemplate.delete(course);
			logUser(username, "delete");
			return true;
		}
		catch (HibernateException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*To update a course using hibernate template*/
	@Override
	@Transactional(readOnly=false)
	public boolean updateCourse(Course course, String username){
		try{
			course.setLast_update(new Date().toString());
			this.hibernateTemplate.update(course);
			logUser(username, "update");
			return true;
		}
		catch(HibernateException e){
			e.printStackTrace();
			return false;
		}
	}

	/*To get a course for a given courseid using hibernate template*/
	@Override
	public Course getCourseById(String id){
		try{
			Course course = this.hibernateTemplate.get(Course.class, id);
			return course;
		}
		catch(HibernateException e){
			e.printStackTrace();
			return null;
		}
	}

	/*To log the user in the database and the activity performed by that user*/
	public void logUser(String username, String activity){
		UserLog user = new UserLog();
		user.setUsername(username);
		user.setTimeStamp(new Date().toString());
		user.setActivity(activity);
		hibernateTemplate.save(user);
	}

	@Override
	public int getTotalCourses(){
		return this.hibernateTemplate.loadAll(Course.class).size();
	}
}
