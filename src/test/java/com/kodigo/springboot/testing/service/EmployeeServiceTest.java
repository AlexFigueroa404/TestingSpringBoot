package com.kodigo.springboot.testing.service;

import com.kodigo.springboot.testing.entity.Employee;
import com.kodigo.springboot.testing.repository.EmployeeRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;


public class EmployeeServiceTest {

// dependency injection


private EmployeeRepository employeeRepository;
private IEmployeeService employeeService;


@BeforeEach
void setup() {
  employeeRepository = Mockito.mock(EmployeeRepository.class);
  employeeService = new EmployeeServiceImpl(employeeRepository);

}

@Test

  // Junit test for
void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

  // given - precondition or setup

  Employee employee = Employee.builder()
      .id(1L)
      .firstName("Jhon")
      .lastName("Doe")
      .email("JhonDoe@gmail.com")
      .build();

  BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
      .willReturn(Optional.empty());

  BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
  // when - action or the behavior that we are going test

  Employee savedEmployee = employeeService.saveEmployee(employee);
  // then - verify the output

  Assertions.assertThat(savedEmployee).isNotNull();
}

}
















