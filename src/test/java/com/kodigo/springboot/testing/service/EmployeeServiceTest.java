package com.kodigo.springboot.testing.service;

import com.kodigo.springboot.testing.entity.Employee;
import com.kodigo.springboot.testing.exception.ResourceNotFoundException;
import com.kodigo.springboot.testing.repository.EmployeeRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

@Mock
private EmployeeRepository employeeRepository;

@InjectMocks
private EmployeeServiceImpl employeeService;

private Employee employee;

@BeforeEach
void setup() {
//  employeeRepository = Mockito.mock(EmployeeRepository.class);
//  employeeService = new EmployeeServiceImpl(employeeRepository);
  employee = Employee.builder()
      .id(1L)
      .firstName("Jhon")
      .lastName("Doe")
      .email("JhonDoe@gmail.com")
      .build();
}

@Test

  // Junit test for
void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

  // given - precondition or setup

  given(employeeRepository.findByEmail(employee.getEmail()))
      .willReturn(Optional.empty());

  given(employeeRepository.save(employee)).willReturn(employee);
  // when - action or the behavior that we are going test

  Employee savedEmployee = employeeService.saveEmployee(employee);
  // then - verify the output

  Assertions.assertThat(savedEmployee).isNotNull();
}

@Test
  // Junit test for
void givenExistingEmail_whenSaveEmployee_thenThrowsException() {

  // given - precondition or setup

  given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
//  Employee savedEmployee = employeeService.saveEmployee(employee);

//  given(employeeRepository.save(employee)).willReturn(employee);

  // when - action or the behavior that we are going test

  org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class,
      () -> employeeService.saveEmployee(employee));

  // then - verify the output

  verify(employeeRepository, never()).save(any(Employee.class));


}

@Test
  // Junit test for
void givenEmployeeList_whenGetAllEmployees_thenReturnEmployeeList() {

  // given - precondition or setup

  Employee employee2 = Employee.builder()
      .firstName("Jane")
      .lastName("Doe")
      .email("JaneDoe@gmail.com")
      .build();

  List<Employee> employees = Arrays.asList(employee, employee2);

  given(employeeRepository.findAll()).willReturn(employees);

  // when - action or the behavior that we are going test

  List<Employee> employeeList = employeeService.getAllEmployees();

  // then - verify the output

  Assertions.assertThat(employeeList).isNotNull();
  Assertions.assertThat(employeeList.size()).isEqualTo(2);
  Assertions.assertThat(employeeList).contains(employee, employee2);
  Assertions.assertThat(employeeList).isInstanceOf(List.class);
}

@Test
  // Junit test for
void givenEmptyEmployeeList_whenGetAllEmployees_thenReturnEmptyEmployeeLists() {

  // given - precondition or setup

  given(employeeRepository.findAll()).willReturn(Collections.emptyList());

  // when - action or the behavior that we are going test

  List<Employee> employeeList = employeeService.getAllEmployees();

  // then - verify the output

  Assertions.assertThat(employeeList).isEmpty();
  Assertions.assertThat(employeeList).size().isEqualTo(0);
}


@Test

  // Junit test for
void givenEmployeeId_whenFindById_thenReturnEmployee() {

  // given - precondition or setup

  given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

  // when - action or the behavior that we are going test

  Employee employeedb = employeeService.findById(employee.getId()).get();

  // then - verify the output

  Assertions.assertThat(employeedb).isNotNull();
}


@Test
  // Junit test for
void givenEmployeeId_whenDeleteEmployee_thenDoNothing() {

  // given - precondition or setup

  doNothing().when(employeeRepository).deleteById(employee.getId());

  // when - action or the behavior that we are going test

  employeeService.deleteEmployee(employee.getId());

  // then - verify the output

  verify(employeeRepository, times(1)).deleteById(employee.getId());
}


}
















