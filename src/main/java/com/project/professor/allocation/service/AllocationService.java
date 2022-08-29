package com.project.professor.allocation.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.professor.allocation.entity.Allocation;
import com.project.professor.allocation.entity.Course;
import com.project.professor.allocation.entity.Professor;
import com.project.professor.allocation.repository.AllocationRepository;
import com.project.professor.allocation.service.exception.ServiceAllocationTimeException;
import com.project.professor.allocation.service.exception.ServiceColissiontException;
import com.project.professor.allocation.service.exception.ServiceNameNotExistException;

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

	public Optional<Allocation> findById(Long Id) {
		return allocationRepository.findById(Id);
	}

	public List<Allocation> findAll() {
		return allocationRepository.findAll();
	}

	public Allocation create(Allocation allocation) {
		allocation.setId(null);
		return allocationRepository.save(allocation);

	}

	public Allocation update(Allocation allocation) throws ServiceNameNotExistException {
		if (allocation.getId() != null && allocationRepository.existsById(allocation.getId())) {
			return allocationRepository.save(allocation);
		} else {
			throw new ServiceNameNotExistException("Allocation doesn't exist");
		}
	}

	public void deleteAll() {
		allocationRepository.deleteAll();
	}

	public void deleteById(Long id) throws ServiceNameNotExistException {
		if (id != null && allocationRepository.existsById(id)) {
			allocationRepository.deleteById(id);
		} else {
			throw new ServiceNameNotExistException("Allocation ID doesn't exist");
		}

	}

	private Allocation saveInternal(Allocation allocation)
			throws ServiceAllocationTimeException, ServiceColissiontException {

		isEndHourGreaterThanStartHour(allocation);
		hasCollission(allocation);

		allocation = allocationRepository.save(allocation);

		Professor professor = professorService.findById(allocation.getProfessorId());
		allocation.setProfessor(professor);

		Course course = courseService.findByCourseId(allocation.getCourseId());
		allocation.setCourse(course);

		return allocation;
	}

	boolean isEndHourGreaterThanStartHour(Allocation allocation) throws ServiceAllocationTimeException {
		boolean isEndHourGreaterThanStartHour = true;

		if (allocation.getStart() != null && allocation.getEnd() != null
				&& allocation.getEnd().compareTo(allocation.getStart()) > 0) {
			throw new ServiceAllocationTimeException("Hour end must be less start hour");
		}
		return isEndHourGreaterThanStartHour;
	}

	boolean hasCollission(Allocation newAllocation) throws ServiceColissiontException {
		boolean hasCollision = false;

		List<Allocation> currentAllocations = allocationRepository.findByProfessorId(newAllocation.getProfessorId());

		for (Allocation currentAllocation : currentAllocations) {
			hasCollision = hasColission(currentAllocation, newAllocation);
			if (hasCollision) {
				return hasCollision;
			}
		}
		throw new ServiceColissiontException("For this allocation time Profesor already allocation");
	}

	private boolean hasColission(Allocation currentAllocation, Allocation newAllocation) {
		return !currentAllocation.getId().equals(newAllocation.getId())
				&& currentAllocation.getDay() == newAllocation.getDay()
				&& currentAllocation.getStart().compareTo(newAllocation.getEnd()) < 0
				&& currentAllocation.getStart().compareTo(newAllocation.getEnd()) < 0
				&& newAllocation.getStart().compareTo(currentAllocation.getEnd()) < 0;
	}
}
