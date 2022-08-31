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
import com.project.professor.allocation.service.exception.ServiceNotFindException;
import com.project.professor.allocation.service.exception.ServiceNotFindException;

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

	public List<Allocation> findByProfessorId(Long professorId) throws ServiceNotFindException {
		List<Allocation> allocated = allocationRepository.findByProfessorId(professorId);
		if (allocated.isEmpty()) {
			throw new ServiceNotFindException("Porfessor allocation not find");
		} else {
			return allocated;
		}
	}

	public List<Allocation> findByCourseId(Long courseId) throws ServiceNotFindException {
		List<Allocation> allocated = allocationRepository.findByCourseId(courseId);
		if (allocated.isEmpty()) {
			throw new ServiceNotFindException("Course allocation not find");
		} else {
			return allocated;
		}
	}

	public Allocation findById(Long Id) throws ServiceNotFindException {
		Allocation allocation = allocationRepository.findById(Id).orElse(null);
		if (allocation == null) {
			throw new ServiceNotFindException("Allocation doesn't exists");
		} else {
			return allocation;
		}
	}

	public List<Allocation> findAll() {
		return allocationRepository.findAll();
	}

	public Allocation save(Allocation allocation) throws ServiceAllocationTimeException, ServiceColissiontException {
		allocation.setId(null);
		return saveInternal(allocation);

	}

	public Allocation update(Allocation allocation)
			throws ServiceNotFindException, ServiceAllocationTimeException, ServiceColissiontException {
		if (allocation.getId() != null && allocationRepository.existsById(allocation.getId())) {
			return saveInternal(allocation);
		} else {
			throw new ServiceNotFindException("Allocation doesn't exist");
		}
	}

	public void deleteAll() {
		allocationRepository.deleteAll();
	}

	public void deleteById(Long id) throws ServiceNotFindException {
		if (id != null && allocationRepository.existsById(id)) {
			allocationRepository.deleteById(id);
		} else {
			throw new ServiceNotFindException("Allocation ID doesn't exist");
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
				&& allocation.getEnd().compareTo(allocation.getStart()) < 0) {
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
				&& newAllocation.getStart().compareTo(currentAllocation.getEnd()) < 0;
	}
}
