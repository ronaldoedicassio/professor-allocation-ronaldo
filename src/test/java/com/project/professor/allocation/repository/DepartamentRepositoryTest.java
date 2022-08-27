package com.project.professor.allocation.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import com.project.professor.allocation.entity.Department;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
@TestPropertySource(locations = "classpath:application.properties")

public class DepartamentRepositoryTest {

	@Autowired
	DepartmentRepository departmentRepository;

	@Test
	public void findAll() {
		List<Department> listDept = departmentRepository.findAll();
		listDept.forEach(System.out::println);
	}

	@Test
	public void findByID() {
		Optional<Department> departament = departmentRepository.findById(1L);

		System.out.println(departament.orElse(null));
	}

	@Test
	public void create() {
		Department dept = new Department();
		dept.setName("Ecologia3");
		System.out.println(dept);

		Department deptSave = departmentRepository.save(dept);
		System.out.println(deptSave);
	
		Department dept1 = new Department();
		dept1.setName("Humanas");
		departmentRepository.save(dept1);
		
		Department dept2 = new Department();
		dept2.setName("Biologica");
		departmentRepository.save(dept2);
		
		Department dept3 = new Department();
		dept3.setName("Medicina");		
		departmentRepository.save(dept3);
	}
	

	@Test
	public void upadate() {
		Department dept = new Department();
		dept.setName("Matemantica");
		dept.setId(400l);
		System.out.println(dept);

		Department deptSave = departmentRepository.save(dept);
		System.out.println(deptSave);

	}

	@Test
	public void delete() {
		departmentRepository.deleteById(4l);
	}

	@Test
	public void deleteAll() {
		departmentRepository.deleteAll();
	}
}
