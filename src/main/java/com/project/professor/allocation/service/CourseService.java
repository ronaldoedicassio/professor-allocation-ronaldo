package com.project.professor.allocation.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Course;
import com.project.professor.allocation.entity.Department;
import com.project.professor.allocation.entity.Professor;
import com.project.professor.allocation.repository.AllocationRepository;
import com.project.professor.allocation.repository.CourseRepository;
import com.project.professor.allocation.service.exception.AllocatedExistsException;
import com.project.professor.allocation.service.exception.EntityNotFindException;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class CourseService {

	private final CourseRepository courseRepository;
	private final AllocationRepository allocationRepository;

	public CourseService(CourseRepository courseRepository, AllocationRepository allocationRepository) {
		super();
		this.courseRepository = courseRepository;
		this.allocationRepository = allocationRepository;
	}

	public Course findByCourseId(Long courseId) throws EntityNotFindException {
		Course course = courseRepository.findById(courseId).orElse(null);
		if (course != null) {
			return course;
		} else {
			throw new EntityNotFindException("Course not find");
		}
	}

	public List<Course> findByNameContaining(String name) {
		return courseRepository.findByNameContaining(name);

	}

	public List<Course> findAll() {
		return courseRepository.findAll();

	}

	public Course findById(Long id) {
		return courseRepository.findById(id).orElse(null);

	}

	public Course save(Course course) {
		course.setId(null);
		return courseRepository.save(course);
	}

	public Course update(Course course) throws EntityNotFindException {
		if (course.getId() != null && courseRepository.existsById(course.getId())) {
			return courseRepository.save(course);
		} else {
			throw new EntityNotFindException("Course doesn't find");
		}
	}

	public void deleteById(Long id) throws EntityNotFindException, AllocatedExistsException {
		if (id != null && courseRepository.existsById(id)) {
			if(allocationRepository.findByCourseId(id) == null) {
				courseRepository.deleteById(id);	
			}else {
				throw new AllocatedExistsException("This course have allocation");
			}
			
		} else {
			throw new EntityNotFindException("Course doesn't find");
		}
	}

	public void deleteAll() throws AllocatedExistsException {
		
		List<Course> courses = findAll();
		
		for (Course course : courses) {
			if(allocationRepository.findByCourseId(course.getId()) != null) {
				throw new AllocatedExistsException("Course have allocation");	
			}
		}
		courseRepository.deleteAllInBatch();
	}
}