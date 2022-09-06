package com.project.professor.allocation.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Professor {

	@Id
	@JsonProperty(access = Access.READ_ONLY)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "CPF", nullable = false, unique = true)
	private String cpf;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "Id_Departament", nullable = false)
	private Long departmentId;

	@ManyToOne
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@JoinColumn(name = "Id_Departament", nullable = false, insertable = false, updatable = false)
	private Department departament;

//	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
//	@OneToMany(mappedBy = "professor")
//	private List<Allocation> allocations;

//	public List<Allocation> getAllocations() {
//		return allocations;
//	}
//
//	public void setAllocations(List<Allocation> allocations) {
//		this.allocations = allocations;
//	}

	public Department getDepartament() {
		return departament;
	}

	public void setDepartament(Department departament) {
		this.departament = departament;
	}

	public Professor() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

}
