package com.project.professor.allocation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Professor;
import com.project.professor.allocation.repository.ProfessorRepository;
import com.project.professor.allocation.service.exception.EntityNotFindException;

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

	public List<Professor> findByNameContaining(String name) throws EntityNotFindException {

		List<Professor> professorNameContaining = professorRepository.findByNameContaining(name);
		if (professorNameContaining != null) {
			return professorNameContaining;
		} else {
			throw new EntityNotFindException("Name not find");
		}
	}

	public Professor findById(Long id) throws EntityNotFindException {

		if (professorRepository.existsById(id)) {
			return professorRepository.findById(id).orElse(null);
		} else {
			throw new EntityNotFindException("Id doesn't exist");
		}
	}

	public Professor findByCpf(String cpf) throws EntityNotFindException {
		Optional<Professor> professorById = professorRepository.findByCpf(cpf);

		if (professorById != null) {
			return professorRepository.findByCpf(cpf).orElse(null);
		} else {
			throw new EntityNotFindException("CPF doesn't exist");
		}
	}

	public List<Professor> findByDepartmentId(Long departmentId) throws EntityNotFindException {
		List<Professor> professorByDepartamentId = professorRepository.findByDepartmentId(departmentId);

		if (professorByDepartamentId != null) {
			return professorByDepartamentId;
		} else {
			throw new EntityNotFindException("Professor not find for this departament ID");
		}
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

	public void deleteById(Long id) throws EntityNotFindException {
		if (id != null && professorRepository.existsById(id)) {
			professorRepository.deleteById(id);
		} else {
			throw new EntityNotFindException("Professor Id doesnt exists");
		}
	}

	public void deleteAll() {
		professorRepository.deleteAll();
	}

}
