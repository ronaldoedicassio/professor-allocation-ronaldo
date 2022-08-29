package com.project.professor.allocation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Allocation;
import com.project.professor.allocation.entity.Course;
import com.project.professor.allocation.repository.CourseRepository;

@Service
public class CourseService {

	private final CourseService courseService;
	private final CourseRepository courseRepository;

	public CourseService(CourseService courseService, CourseRepository courseRepository) {
		super();
		this.courseService = courseService;
		this.courseRepository = courseRepository;
	}
	
	public Course findByCourseId(Long courseId) {
		return courseRepository.findById(courseId).orElse(null);
	}

}
