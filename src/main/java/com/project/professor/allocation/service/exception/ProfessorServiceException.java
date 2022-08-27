package com.project.professor.allocation.service.exception;

public class ProfessorServiceException extends Exception {

	private String professorServiceException;

	public ProfessorServiceException(String serviceNameNotExistExpetion) {
		super("Professor doesnt exist");
		this.professorServiceException = serviceNameNotExistExpetion;

	}

	public String getServiceNameNotExistExpetion() {
		return professorServiceException;
	}

	public void setServiceNameNotExistExpetion(String serviceNameNotExistExpetion) {
		this.professorServiceException = serviceNameNotExistExpetion;
	}
}
