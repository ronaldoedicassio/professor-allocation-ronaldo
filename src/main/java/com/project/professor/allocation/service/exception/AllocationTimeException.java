package com.project.professor.allocation.service.exception;

public class AllocationTimeException extends Exception {
	
	private String serviceAllocationTimeException;
	
	public AllocationTimeException(String serviceAllocationTimeException) {
		super("End time is must be less start time");
		this.serviceAllocationTimeException = serviceAllocationTimeException;

	}

	public String getServiceNameNotExistExpetion() {
		return serviceAllocationTimeException;
	}

	public void setServiceNameNotExistExpetion(String serviceAllocationTimeException) {
		this.serviceAllocationTimeException = serviceAllocationTimeException;
	}
}
