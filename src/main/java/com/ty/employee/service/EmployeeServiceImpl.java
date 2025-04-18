package com.ty.employee.service;

import com.ty.employee.model.Employee;
import com.ty.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Override
	public Employee addEmployee(Employee employee) {
		return repository.save(employee);
	}

	@Override
	@Cacheable(value = "employees", key = "#id")
	public Employee getEmployeeById(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	@Cacheable(value = "allEmployees", key = "{#pageable.pageNumber, #pageable.pageSize, #pageable.sort}")
	public Page<Employee> getAllEmployees(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Override
	@CacheEvict(value = "employees", key = "#id")
	public Employee updateEmployee(Long id, Employee newData) {
		Employee emp = repository.findById(id).orElseThrow();
		emp.setName(newData.getName());
		emp.setDepartment(newData.getDepartment());
		emp.setSalary(newData.getSalary());
		return repository.save(emp);
	}

	@Override
	@CacheEvict(value = { "employees", "allEmployees" }, allEntries = true)
	public void deleteEmployee(Long id) {
		repository.deleteById(id);
	}

	@Override
	@Async
	@CacheEvict(value = { "employees", "allEmployees" }, allEntries = true)
	public void incrementAllSalaries() {
		List<Employee> employees = repository.findAll();
		employees.forEach(emp -> {
			emp.setSalary(emp.getSalary() * 1.10);
			repository.save(emp);
		});
	}
}
