package com.project.professor.allocation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Allocation;
import com.project.professor.allocation.entity.Course;
import com.project.professor.allocation.entity.Professor;
import com.project.professor.allocation.repository.AllocationRepository;
import com.project.professor.allocation.service.exception.AllocationTimeException;
import com.project.professor.allocation.service.exception.ColissiontException;
import com.project.professor.allocation.service.exception.EntityNotFindException;

@Service
public class AllocationService {

	private final AllocationRepository allocationRepository;
	private final ProfessorService professorService;
	private final CourseService courseService;

	public AllocationService(AllocationRepository allocationRepository, ProfessorService professorService,
			CourseService courseService) {
		super();
		this.allocationRepository = allocationRepository;
		this.professorService = professorService;
		this.courseService = courseService;
	}

	public List<Allocation> findByProfessorId(Long professorId) {
		return allocationRepository.findByProfessorId(professorId);

	}

	public List<Allocation> findByCourseId(Long courseId) {
		return allocationRepository.findByCourseId(courseId);

	}

	public Allocation findById(Long Id) {
		return allocationRepository.findById(Id).orElse(null);

	}

	public List<Allocation> findAll() {
		return allocationRepository.findAll();
	}

	public Allocation save(Allocation allocation)
			throws AllocationTimeException, ColissiontException, EntityNotFindException {
		allocation.setId(null);
		return saveInternal(allocation);
	}

	public Allocation update(Allocation allocation)
			throws EntityNotFindException, AllocationTimeException, ColissiontException {
		if (allocation.getId() != null && allocationRepository.existsById(allocation.getId())) {
			return saveInternal(allocation);
		} else {
			throw new EntityNotFindException("Allocation doesn't exist");
		}
	}

	public void deleteAll() {
		allocationRepository.deleteAll();
	}

	public void deleteById(Long id) throws EntityNotFindException {
		if (id != null && allocationRepository.existsById(id)) {
			allocationRepository.deleteById(id);
		} else {
			throw new EntityNotFindException("Allocation ID doesn't exist");
		}
	}

	private Allocation saveInternal(Allocation allocation)
			throws AllocationTimeException, ColissiontException, EntityNotFindException {

		if (!isEndHourGreaterThanStartHour(allocation)) {
			if (hasCollission(allocation)) {
				allocation = allocationRepository.save(allocation);

				Professor professor = professorService.findById(allocation.getProfessorId());
				allocation.setProfessor(professor);

				Course course = courseService.findByCourseId(allocation.getCourseId());
				allocation.setCourse(course);

				return allocation;
			} else {
				throw new ColissiontException("For this allocation time Profesor already allocation");
			}
		} else {
			throw new AllocationTimeException("Hour end must be less start hour");
		}

	}

	boolean isEndHourGreaterThanStartHour(Allocation allocation) throws AllocationTimeException {
		return allocation.getStart() != null && allocation.getEnd() != null
				&& allocation.getEnd().compareTo(allocation.getStart()) < 0;
	}

	boolean hasCollission(Allocation newAllocation) throws ColissiontException {
		boolean hasCollision = false;

		List<Allocation> currentAllocations = allocationRepository.findByProfessorId(newAllocation.getProfessorId());

		for (Allocation currentAllocation : currentAllocations) {
			hasCollision = hasColission(currentAllocation, newAllocation);
			if (hasCollision) {
				break;
			}
		}
		return hasCollision;
	}

	private boolean hasColission(Allocation currentAllocation, Allocation newAllocation) {
		return !currentAllocation.getId().equals(newAllocation.getId())
				&& currentAllocation.getDay() == newAllocation.getDay()
				&& currentAllocation.getStart().compareTo(newAllocation.getEnd()) < 0
				&& newAllocation.getStart().compareTo(currentAllocation.getEnd()) < 0;
	}
}
