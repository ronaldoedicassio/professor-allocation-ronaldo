package com.project.professor.allocation.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.professor.allocation.entity.Course;
import com.project.professor.allocation.service.CourseService;
import com.project.professor.allocation.service.exception.ServiceNotFindException;

@RestController
@RequestMapping(path = "/course")
public class CourseController {

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		super();
		this.courseService = courseService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Course>> findAll(@RequestParam(name = "name", required = false) String name) {

		List<Course> courses = courseService.findAll();
		if (name == null) {
			courses = courseService.findAll();
		} else {
			courses = courseService.findByNameContaining(name);
		}
		return new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
	}

	@GetMapping(path = "/{course_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Course> findById(@PathVariable(name = "course_id") Long id) {

		Course courses = courseService.findById(id);
		if (courses == null) {
			return new ResponseEntity<Course>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Course>(courses, HttpStatus.OK);
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Course> save(@RequestBody Course course) {
		try {
			course = courseService.save(course);
			return new ResponseEntity<Course>(course, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
	}

	@PutMapping(path = "{/course_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Course> update(@PathVariable(name = "course_id") Long id, @RequestBody Course course) {
		course.setId(id);
		try {
			course = courseService.update(course);
			if (course == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(course, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(path = "/{course_id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Void> deleteById(@PathVariable(name = "course_id") Long id) throws ServiceNotFindException {
		try {
			courseService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
