package com.project.professor.allocation.service;

import org.springframework.stereotype.Service;

@Service
public class CourseService {

	private final CourseService courseService;

	public CourseService(CourseService courseService) {
		super();
		this.courseService = courseService;
	}

}
