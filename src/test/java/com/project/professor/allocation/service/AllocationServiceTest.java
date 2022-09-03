package com.project.professor.allocation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.project.professor.allocation.entity.Allocation;
import com.project.professor.allocation.service.exception.ServiceAllocationTimeException;
import com.project.professor.allocation.service.exception.ServiceColissiontException;
import com.project.professor.allocation.service.exception.ServiceNotFindException;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class AllocationServiceTest {

	SimpleDateFormat sdf = new SimpleDateFormat("HH:mmZ");

	@Autowired
	AllocationService allocationService;

	@Test
	public void findAll() {
		List<Allocation> allocationFind = allocationService.findAll();
		allocationFind.stream().forEach(System.out::println);
	}

	@Test
	public void create() throws ParseException {

		try {

			Allocation allocation = new Allocation();
			allocation.setId(null);
			allocation.setDay(DayOfWeek.SUNDAY);
			allocation.setStart(sdf.parse("15:00-0300"));
			allocation.setEnd(sdf.parse("17:00-0300"));
			allocation.setProfessorId(1L);
			allocation.setCourseId(1L);
			allocation = allocationService.save(allocation);

			System.out.println(allocation);

		} catch (ServiceAllocationTimeException e) {
			System.out.println(e.getServiceNameNotExistExpetion());
		} catch (ServiceColissiontException e) {
			System.out.println(e.getServiceNameNotExistExpetion());
		} catch (ServiceNotFindException e) {
			System.out.println(e.getServiceNameNotExistExpetion());
		}

	}

}
