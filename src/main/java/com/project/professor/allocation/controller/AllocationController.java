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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.professor.allocation.entity.Allocation;
import com.project.professor.allocation.entity.Department;
import com.project.professor.allocation.service.AllocationService;
import com.project.professor.allocation.service.exception.ServiceAllocationTimeException;
import com.project.professor.allocation.service.exception.ServiceColissiontException;
import com.project.professor.allocation.service.exception.ServiceNotFindException;

@RestController
@RequestMapping(path = "/allocation")
public class AllocationController {

	private final AllocationService allocationService;

	public AllocationController(AllocationService allocationService) {
		super();
		this.allocationService = allocationService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Allocation>> findAll() {

		List<Allocation> allocation = allocationService.findAll();
		if (allocation == null) {
			return new ResponseEntity<List<Allocation>>(allocation, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Allocation>>(allocation, HttpStatus.OK);
		}

	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Allocation> findById(Long id) {

		try {
			Allocation allocation = allocationService.findById(id);
			return new ResponseEntity<Allocation>(allocation, HttpStatus.OK);
		} catch (ServiceNotFindException e) {
			return new ResponseEntity<Allocation>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/course/{course_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Allocation>> findByCourseId(@PathVariable(name = "course_id") Long courseId) {

		try {
			List<Allocation> coursesAllocated = allocationService.findByCourseId(courseId);
			return new ResponseEntity<>(coursesAllocated, HttpStatus.OK);
		} catch (ServiceNotFindException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(path = "/professor/{professor_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Allocation>> findByProfessorId(@PathVariable(name = "professor_id") Long professorId) {

		try {
			List<Allocation> coursesAllocated = allocationService.findByProfessorId(professorId);
			return new ResponseEntity<>(coursesAllocated, HttpStatus.OK);
		} catch (ServiceNotFindException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Allocation> create(@RequestBody Allocation allocation) {

		try {
			Allocation allocated = allocationService.save(allocation);
			return new ResponseEntity<Allocation>(allocated, HttpStatus.CREATED);
		} catch (ServiceAllocationTimeException | ServiceColissiontException | ServiceNotFindException e) {
			return new ResponseEntity<Allocation>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/{allocation_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Allocation> upadate(@PathVariable(name = "allocation_id") Long id,
			@RequestBody Allocation allocation) {
		allocation.setId(id);

		try {
			allocationService.update(allocation);
			return new ResponseEntity<Allocation>(allocation, HttpStatus.OK);

		} catch (ServiceNotFindException | ServiceAllocationTimeException | ServiceColissiontException e) {
			return new ResponseEntity<Allocation>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(path = "/{allocation_id}")
	public ResponseEntity<Void> deleteById(@PathVariable(name = "allocation_id") Long id) {
		try {
			allocationService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (ServiceNotFindException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping
	public ResponseEntity<Void> deleteAll() {
		allocationService.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
