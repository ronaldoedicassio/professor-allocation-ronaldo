package com.project.professor.allocation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Professor;
import com.project.professor.allocation.repository.ProfessorRepository;
import com.project.professor.allocation.service.exception.ServiceNotFindException;

@Service
public class ProfessorService {

	private final ProfessorRepository professorRepository;

	public ProfessorService(ProfessorRepository professorRepository) {
		super();
		this.professorRepository = professorRepository;
	}

	public List<Professor> findAll() {
		return professorRepository.findAll();
	}

	public List<Professor> findByNameContaining(String name) {
		return professorRepository.findByNameContaining(name);
	}

	public Professor findById(Long id) {
		return professorRepository.findById(id).orElse(null);
	}

	public Professor findByCpf(String cpf) {
		return professorRepository.findByCpf(cpf).orElse(null);
	}

	public List<Professor> findByDepartmentId(Long departmentId) {
		return professorRepository.findByDepartmentId(departmentId);
	}

	public Professor save(Professor professor) {
		professor.setId(null);
		return professorRepository.save(professor);
	}

	public Professor update(Professor professor) throws ServiceNotFindException {
		if (professor.getId() != null && professorRepository.existsById(professor.getId())) {
			return professorRepository.save(professor);
		} else
			throw new ServiceNotFindException("Professor  doesnt exists");
	}

	public void deleteById(Long id) throws ServiceNotFindException {
		if (id != null && professorRepository.existsById(id)) {
			professorRepository.deleteById(id);
		} else {
			throw new ServiceNotFindException("Professor Id doesnt exists");
		}
	}

	public void deleteAll() {
		professorRepository.deleteAll();
	}

}
