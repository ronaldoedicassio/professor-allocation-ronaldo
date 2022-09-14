package com.project.professor.allocation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Department;
import com.project.professor.allocation.entity.Department;
import com.project.professor.allocation.repository.DepartmentRepository;
import com.project.professor.allocation.repository.ProfessorRepository;
import com.project.professor.allocation.service.exception.AllocatedExistsException;
import com.project.professor.allocation.service.exception.EntityNotFindException;

@Service
public class DepartmentService {

	private final DepartmentRepository departmentRepository;
	private final ProfessorRepository professorRepository;

	public DepartmentService(DepartmentRepository departmentRepository, ProfessorRepository professorRepository) {
		super();
		this.departmentRepository = departmentRepository;
		this.professorRepository = professorRepository;
	}

	public List<Department> findByNameContaining(String name) {
		return departmentRepository.findByNameContaining(name);

	}

	public Optional<Department> findById(Long id) {
		return departmentRepository.findById(id);
	}

	public List<Department> findAll() {
		return departmentRepository.findAll();
	}

	public Department save(Department department) {
		department.setId(null);
		return departmentRepository.save(department);
	}

	public Department update(Department department) throws EntityNotFindException {
		if (department.getId() != null && departmentRepository.existsById(department.getId())) {
			return departmentRepository.save(department);
		} else {
			throw new EntityNotFindException("Department doenst exists");
		}
	}

	public void deleteById(Long id) throws EntityNotFindException, AllocatedExistsException {
		if (id != null && departmentRepository.existsById(id)) {
			if(professorRepository.findByDepartmentId(id) == null) {
				departmentRepository.deleteById(id);	
			}else {
				throw new AllocatedExistsException("This department have professor");
			}
		} else {
			throw new EntityNotFindException("Department ID doesnt exists");
		}
	}

	public void deleteAll() throws AllocatedExistsException {
		
		List<Department> departments = findAll();
		
		for (Department department : departments) {
			if(professorRepository.findByDepartmentId(department.getId()) != null) {
				throw new AllocatedExistsException("Department have professor(s)");	
			}
		}
		departmentRepository.deleteAll();
	}

}
