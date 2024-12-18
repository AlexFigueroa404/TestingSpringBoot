package com.kodigo.springboot.testing.service;

import com.kodigo.springboot.testing.entity.Employee;
import com.kodigo.springboot.testing.exception.ResourceNotFoundException;
import com.kodigo.springboot.testing.repository.EmployeeRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

private final EmployeeRepository employeeRepository;

public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
  this.employeeRepository = employeeRepository;
}

@Override
public Employee saveEmployee(Employee employee) {

  Optional<Employee> employeedb = employeeRepository.findByEmail(employee.getEmail());

  if (employeedb.isPresent()) {
    throw new ResourceNotFoundException("Employee already exist with given email" +
        employee.getEmail());

  }

  return employeeRepository.save(employee);

}
}
