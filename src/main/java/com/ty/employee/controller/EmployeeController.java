package com.ty.employee.controller;

import com.ty.employee.model.Employee;
import com.ty.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService service;

	// Add a new employee
	@PostMapping
	public Employee addEmployee(@RequestBody Employee employee) {
		return service.addEmployee(employee);
	}

	// Get employee by ID
	@GetMapping("/{id}")
	public Employee getEmployee(@PathVariable Long id) {
		return service.getEmployeeById(id);
	}

	// Get all employees
	@GetMapping
	public Page<Employee> getAllEmployees(@RequestParam int page, @RequestParam int size,
			@RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String order) {
		Sort sort = order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		return service.getAllEmployees(pageRequest);
	}

	// Update employee
	@PutMapping("/{id}")
	public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		return service.updateEmployee(id, employee);
	}

	// Delete employee
	@DeleteMapping("/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		service.deleteEmployee(id);
	}

	// Increment salary of all employees by 10%
	@PutMapping("/increment-salary")
	public String incrementSalary() {
		service.incrementAllSalaries();
		return "Salary incremented for all employees.";
	}
}
