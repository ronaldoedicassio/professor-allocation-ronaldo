package com.project.professor.allocation.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Course;
import com.project.professor.allocation.entity.Department;
import com.project.professor.allocation.entity.Professor;
import com.project.professor.allocation.repository.CourseRepository;
import com.project.professor.allocation.service.exception.EntityNotFindException;

import net.bytebuddy.implementation.bytecode.Throw;

@Service
public class CourseService {

	private final CourseRepository courseRepository;

	public CourseService(CourseRepository courseRepository) {
		super();
		this.courseRepository = courseRepository;
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

	public void deleteById(Long id) throws EntityNotFindException {
		if (id != null && courseRepository.existsById(id)) {
			courseRepository.deleteById(id);
		} else {
			throw new EntityNotFindException("Course doesn't find");
		}
	}

	public void deleteAll() {
		courseRepository.deleteAllInBatch();
	}
}