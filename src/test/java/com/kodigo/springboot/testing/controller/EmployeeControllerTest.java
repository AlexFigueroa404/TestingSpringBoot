package com.kodigo.springboot.testing.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodigo.springboot.testing.entity.Employee;
import com.kodigo.springboot.testing.service.IEmployeeService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class EmployeeControllerTest {

@Autowired
private MockMvc mockMvc;

@MockBean
private IEmployeeService employeeService;

@Autowired
private ObjectMapper objectMapper;


@Test
  // Junit test for
void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee()
    throws Exception {

  // given - precondition or setup

  Employee employee = Employee.builder().firstName("Jhon").lastName("Doe")
      .email("JhonDoe@gmail.com").build();

  given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
      .willAnswer((invocation) -> invocation.getArgument(0));

  // when - action or the behavior that we are going test

  mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON)
          .content(new ObjectMapper().writeValueAsString(employee)))

      // then - verify the output
      .andDo(print()).andExpect(status().isCreated())
      .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
      .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
      .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));


}

@Test
  // Junit test for
void givenListEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {

  // given - precondition or setup

  Employee employee1 = Employee.builder().firstName("Jhon").lastName("Doe")
      .email("JhonDoe@gmail.com").build();

  Employee employee2 = Employee.builder().firstName("Jane").lastName("Doe")
      .email("JaneDoe@gmail.com").build();

  List<Employee> employees = Arrays.asList(employee1, employee2);

  given(employeeService.getAllEmployees()).willReturn(employees);

  // when - action or the behavior that we are going test

  mockMvc.perform(get("/api/employees"))
      // then - verify the output
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$.size()", CoreMatchers.is(employees.size())));
}

@Test

  // Junit test for
void givenEmployeeId_whenFindById_thenReturnEmployee() throws Exception {

  // given - precondition or setup
  Employee employee = Employee.builder()
      .id(1L)
      .firstName("Jhon").lastName("Doe")
      .email("JhonDoe@gmail.com").build();

  given(employeeService.findById(employee.getId())).willReturn(Optional.of(employee));

  // when - action or the behavior that we are going test

  mockMvc.perform(get("/api/employees/{id}", employee.getId()))

      // then - verify the output

      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
      .andExpect(jsonPath("$.lastName").value(employee.getLastName()))
      .andExpect(jsonPath("$.email").value(employee.getEmail()));


}

@Test
  // Junit test for
void givenInvalidEmployeeId_whenFindById_thenReturnEmpty() throws Exception {

  // given - precondition or setup

  Employee employee = Employee.builder()
      .id(1L)
      .firstName("Jhon").lastName("Doe")
      .email("JhonDoe@gmail.com").build();

  // when - action or the behavior that we are going test

  given(employeeService.findById(employee.getId())).willReturn(Optional.empty());

  mockMvc.perform(get("/api/employees/{id}", employee.getId()))

      // then - verify the output

      .andExpect(status().isNotFound())
      .andDo(print());


}


@Test
  // Junit test for
void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployee() throws Exception {

  // given - precondition or setup
  Employee employee = Employee.builder()
      .id(3L)
      .firstName("Jhon").lastName("Doe")
      .email("JhonDoe@gmail.com").build();

  Employee updatedemployee = Employee.builder()
      .id(3L)
      .firstName("Jane ").lastName("Smith")
      .email("JaneSmith@gmail.com").build();

  given(employeeService.findById(employee.getId())).willReturn(Optional.of(employee));

  given(employeeService.updateEmployee(any(Employee.class)))
      .willAnswer((invocation) -> invocation.getArgument(0));

  // when - action or the behavior that we are going test

  mockMvc.perform(put("/api/employees/{id}", employee.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(updatedemployee)))

      // then - verify the output

      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$.firstName").value(updatedemployee.getFirstName()))
      .andExpect(jsonPath("$.lastName").value(updatedemployee.getLastName()))
      .andExpect(jsonPath("$.email").value(updatedemployee.getEmail()));


}


@Test
void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {

  // given - precondition or setup
  Employee employee = Employee.builder()
      .id(3L)
      .firstName("Jhon").lastName("Doe")
      .email("JhonDoe@gmail.com").build();

  Employee updatedemployee = Employee.builder()
      .id(3L)
      .firstName("Jane ").lastName("Smith")
      .email("JaneSmith@gmail.com").build();

  given(employeeService.findById(employee.getId())).willReturn(Optional.empty());

  given(employeeService.updateEmployee(any(Employee.class)))
      .willAnswer((invocation) -> invocation.getArgument(0));

  // when - action or the behavior that we are going test

  mockMvc.perform(put("/api/employees/{id}", employee.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(updatedemployee)))

      // then - verify the output

      .andExpect(status().isNotFound())
      .andDo(print());

}

@Test

  // Junit test for
void givenEmployeeId_whenDeleteEmployeeById_thenReturn200() throws Exception {

  // given - precondition or setup

  Employee employee = Employee.builder()
      .id(3L)
      .firstName("Jane ").lastName("Smith")
      .email("JaneSmith@gmail.com").build();

  willDoNothing().given(employeeService).deleteEmployee(employee.getId());

  // when - action or the behavior that we are going test
  mockMvc.perform(delete("/api/employees/{id}", employee.getId()))

      // then - verify the output

      .andExpect(status().isOk())
      .andDo(print());


}

}



















