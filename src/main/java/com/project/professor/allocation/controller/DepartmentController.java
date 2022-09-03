package com.project.professor.allocation.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.professor.allocation.entity.Department;
import com.project.professor.allocation.service.DepartmentService;
import com.project.professor.allocation.service.exception.ServiceNotFindException;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentController {

	private final DepartmentService departmentService;

	public DepartmentController(DepartmentService departmentService) {
		super();
		this.departmentService = departmentService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Department>> findAll(@RequestParam(name = "name", required = false) String name) {

		List<Department> departaments = departmentService.findAll();
		if (name == null) {
			departaments = departmentService.findAll();
		} else {
			departaments = departmentService.findByNameContaining(name);
		}
		return new ResponseEntity<List<Department>>(departaments, HttpStatus.OK);
	}

	@GetMapping(path = "/{departament_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Department> findById(@PathVariable(name = "departament_id") Long id) {
		Department department = departmentService.findById(id).orElse(null);

		if (department == null) {
			return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<Department>(department, HttpStatus.OK);
		}
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Department> create(@RequestBody Department departament) {

		try {
			Department dpt = departmentService.save(departament);
			return new ResponseEntity<Department>(dpt, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Department>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(path = "/{departament_id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Department> upadate(@RequestBody Department department,
			@PathVariable(name = "departament_id", required = true) Long departmentId) {

		department.setId(departmentId);
		try {
			if (departmentService.update(department) == null) {
				return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<Department>(department, HttpStatus.OK);
			}
		} catch (ServiceNotFindException e1) {
			return new ResponseEntity<Department>(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			return new ResponseEntity<Department>(HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping(path = "/{departament_id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "departament_id") Long id) {

		try {
			departmentService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (ServiceNotFindException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping
	public ResponseEntity<Void> delete() {

		departmentService.deleteAll();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}
}
