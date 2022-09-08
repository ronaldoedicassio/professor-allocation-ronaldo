package com.project.professor.allocation.service.exception;

public class EntityNotFindException extends Exception {
	
	private String serviceNameNotExistExpetion;
	
	public EntityNotFindException(String serviceNameNotExistExpetion) {
		super("Doesn't exist");
		this.serviceNameNotExistExpetion = serviceNameNotExistExpetion;

	}

	public String getServiceNameNotExistExpetion() {
		return serviceNameNotExistExpetion;
	}

	public void setServiceNameNotExistExpetion(String serviceNameNotExistExpetion) {
		this.serviceNameNotExistExpetion = serviceNameNotExistExpetion;
	}
}
