package com.project.professor.allocation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Professor;
import com.project.professor.allocation.repository.AllocationRepository;
import com.project.professor.allocation.repository.ProfessorRepository;
import com.project.professor.allocation.service.exception.AllocationExistsException;
import com.project.professor.allocation.service.exception.EntityNotFindException;

@Service
public class ProfessorService {

	private final ProfessorRepository professorRepository;
	private final AllocationRepository allocationRepository;

	public ProfessorService(ProfessorRepository professorRepository, AllocationRepository allocationRepository) {
		super();
		this.professorRepository = professorRepository;
		this.allocationRepository = allocationRepository;
	}

	public List<Professor> findAll() {
		return professorRepository.findAll();
	}

	public List<Professor> findByNameContaining(String name) throws EntityNotFindException {
		return professorRepository.findByNameContaining(name);

	}

	public Professor findById(Long id) throws EntityNotFindException {
		return professorRepository.findById(id).orElse(null);

	}

	public Professor findByCpf(String cpf) throws EntityNotFindException {
		return professorRepository.findByCpf(cpf).orElse(null);
	}

	public List<Professor> findByDepartmentId(Long departmentId) throws EntityNotFindException {
		return professorRepository.findByDepartmentId(departmentId);

	}

	public Professor save(Professor professor) {
		professor.setId(null);
		return professorRepository.save(professor);
	}

	public Professor update(Professor professor) throws EntityNotFindException {
		if (professor.getId() != null && professorRepository.existsById(professor.getId())) {
			return professorRepository.save(professor);
		} else
			throw new EntityNotFindException("Professor  doesnt exists");
	}

	public void deleteById(Long id) throws EntityNotFindException, AllocationExistsException {
		if (id != null && professorRepository.existsById(id)) {
			if (allocationRepository.findByProfessorId(id) == null) {
				professorRepository.deleteById(id);
			} else {
				throw new AllocationExistsException("Professor have allocation");
			}
		} else {
			throw new EntityNotFindException("Professor Id doesnt exists");
		}
	}

	public void deleteAll() throws AllocationExistsException {

		List<Professor> professors = findAll();

		for (Professor professor : professors) {
			if (allocationRepository.findByProfessorId(professor.getId()) != null) {
				throw new AllocationExistsException("Professor have allocation");
			}
		}
		professorRepository.deleteAll();
	}

}
