package com.project.professor.allocation.service.exception;

public class ColissiontException extends Exception {
	
	private String serviceColissiontException;
	
	public ColissiontException(String serviceColissiontException) {
		super("Colission has detected");
		this.serviceColissiontException = serviceColissiontException;

	}

	public String getServiceNameNotExistExpetion() {
		return serviceColissiontException;
	}

	public void setServiceNameNotExistExpetion(String serviceColissiontException) {
		this.serviceColissiontException = serviceColissiontException;
	}
}
