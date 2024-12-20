package com.kodigo.springboot.testing.service;

import com.kodigo.springboot.testing.entity.Employee;
import java.util.List;
import java.util.Optional;

public interface IEmployeeService {

List<Employee> getAllEmployees();

Employee saveEmployee(Employee employee);

Optional<Employee> findById(Long id);

void deleteEmployee(Long id);


Employee updateEmployee(Employee employee);

}
