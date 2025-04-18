package com.ty.employee.service;

import com.ty.employee.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
	
	Employee addEmployee(Employee employee);

	Employee getEmployeeById(Long id);

	Page<Employee> getAllEmployees(Pageable pageable);

	Employee updateEmployee(Long id, Employee employee);

	void deleteEmployee(Long id);

	void incrementAllSalaries();
}
