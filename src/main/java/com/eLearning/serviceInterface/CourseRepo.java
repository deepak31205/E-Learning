package com.eLearning.serviceInterface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.eLearning.beans.Course;

@Service
public interface CourseRepo extends JpaRepository<Course, String>  {

}
