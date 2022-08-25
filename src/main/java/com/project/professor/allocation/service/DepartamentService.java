package com.project.professor.allocation.service;

import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Departament;
import com.project.professor.allocation.repository.DepartamentRepository;

@Service
public class DepartamentService {

	private final DepartamentRepository departamentRepository;

	public DepartamentService(DepartamentRepository departamentRepository) {
		super();
		this.departamentRepository = departamentRepository;
	}

	public Departament findById(Long id) {
		return departamentRepository.findById(id).orElseGet(null);
	}
	
	public Departament create(Departament departament) {
		return departamentRepository.save(departament);
	}
	
	public void deleteById(Long id) {
		departamentRepository.deleteById(id);
	}

}
