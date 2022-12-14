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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.professor.allocation.entity.Professor;
import com.project.professor.allocation.service.ProfessorService;
import com.project.professor.allocation.service.exception.AllocatedExistsException;
import com.project.professor.allocation.service.exception.EntityNotFindException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class ProfessorController {

	private final ProfessorService professorService;

	public ProfessorController(ProfessorService professorService) {
		super();
		this.professorService = professorService;
	}

	@ApiOperation(value = "Find all professors")
	@ApiResponses({ @ApiResponse(code = 200, message = "OK") })
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<Professor>> findAll(@RequestParam(name = "name", required = false) String name)
			throws EntityNotFindException {
		List<Professor> professors;
		if (name == null) {
			professors = professorService.findAll();
		} else {
			professors = professorService.findByNameContaining(name);
		}
		return new ResponseEntity<>(professors, HttpStatus.OK);

	}

	@ApiOperation(value = "Find a professor")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "OK"), 
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping("/{professor_id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Professor> findById(@PathVariable(name = "professor_id") Long id)
			throws EntityNotFindException {
		Professor professor = professorService.findById(id);
		if (professor == null) {
			return new ResponseEntity<>(professor, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(professor, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "Find professors by department")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "OK"), 
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping("deptid/{department_id}")
	public ResponseEntity<List<Professor>> findByDepartmentId(@PathVariable(name = "department_id") Long departmentId)
			throws EntityNotFindException {
		List<Professor> professor = professorService.findByDepartmentId(departmentId);
		if (professor.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Professor>>(professor, HttpStatus.OK);
		}
	}

	@ApiOperation(value = "Find professors by CPF")
	@ApiResponses({ 
		@ApiResponse(code = 200, message = "OK"), 
		@ApiResponse(code = 400, message = "Bad Request"),
		@ApiResponse(code = 404, message = "Not Found") })
	@GetMapping(value = "/cpf")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Professor> findByCpf(@RequestParam String cpf) throws EntityNotFindException {
		
		Professor professor = professorService.findByCpf(cpf);
		if (professor != null) {
			return new ResponseEntity<>(professor, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@ApiOperation(value = "Save a professor")
	@ApiResponses({ 
			@ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 400, message = "Bad Request") })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Professor> save(@RequestBody Professor professor) {
		try {
			professor = professorService.save(professor);
			return new ResponseEntity<Professor>(professor, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
	}

	@ApiOperation(value = "Update a professor")
	@ApiResponses({ 
			@ApiResponse(code = 200, message = "OK"), 
			@ApiResponse(code = 400, message = "Bad Request"),
			@ApiResponse(code = 404, message = "Not Found") })
	@PutMapping(path = "{/professor_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Professor> update(@PathVariable(name = "professor_id") Long id,
			@RequestBody Professor professor) {
		professor.setId(id);
		try {
			professor = professorService.update(professor);
			if (professor == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(professor, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "Delete a professor")
	@ApiResponses({ @ApiResponse(code = 204, message = "No Content") })
	@DeleteMapping(path = "/{professor_id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Void> deleteById(@PathVariable(name = "professor_id") Long id) {
		try {
			professorService.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (AllocatedExistsException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (EntityNotFindException e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "Delete all professors")
	@ApiResponses({ @ApiResponse(code = 204, message = "No Content") })
	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Void> deleteAll() {
		try {
			professorService.deleteAll();
		} catch (AllocatedExistsException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
