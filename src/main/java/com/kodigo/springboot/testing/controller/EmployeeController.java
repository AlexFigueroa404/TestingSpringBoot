package com.kodigo.springboot.testing.controller;

import com.kodigo.springboot.testing.entity.Employee;
import com.kodigo.springboot.testing.service.IEmployeeService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

private final IEmployeeService employeeService;

public EmployeeController(IEmployeeService employeeService) {
  this.employeeService = employeeService;
}

@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Employee createEmployee(@RequestBody Employee employee) {
  return employeeService.saveEmployee(employee);
}

@GetMapping
public List<Employee> getAllEmployees() {
  return employeeService.getAllEmployees();
}

@GetMapping("{id}")
public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") Long id) {

  return employeeService.findById(id)
      .map(ResponseEntity::ok)
      .orElseGet(() -> ResponseEntity.notFound().build());

}

@PutMapping("{id}")
public ResponseEntity<Employee> updateEmployee(@PathVariable("id") Long id,
    @RequestBody Employee employee) {

  return employeeService.findById(id).map(savedEmployee -> {
    savedEmployee.setFirstName(employee.getFirstName());
    savedEmployee.setLastName(employee.getLastName());
    savedEmployee.setEmail(employee.getEmail());
    Employee updateEmployee = employeeService.updateEmployee(savedEmployee);

    return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
  }).orElseGet(() -> ResponseEntity.notFound().build());


}


@DeleteMapping("{id}")
public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id) {

  employeeService.deleteEmployee(id);

  return new ResponseEntity<String>("Employee deleted succesfully", HttpStatus.OK);
}


}

























