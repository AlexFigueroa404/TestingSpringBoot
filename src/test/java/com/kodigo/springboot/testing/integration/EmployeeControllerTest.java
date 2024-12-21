package com.kodigo.springboot.testing.integration;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodigo.springboot.testing.entity.Employee;
import com.kodigo.springboot.testing.repository.EmployeeRepository;
import java.util.Arrays;
import java.util.List;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

// deshabilitar h2


@AutoConfigureMockMvc
public class EmployeeControllerTest {


@Autowired
private MockMvc mockMvc;


@Autowired
private EmployeeRepository employeeRepository;

@Autowired
private ObjectMapper objectMapper;


@BeforeEach
void setup() {
  employeeRepository.deleteAll();
}


@Test
  // Junit test for
void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

  // given - precondition or setup

  Employee employee = Employee.builder().firstName("Jhon").lastName("Doe")
      .email("JhonDoe@gmail.com").build();

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

  employeeRepository.saveAll(employees);

  // when - action or the behavior that we are going test

  mockMvc.perform(get("/api/employees"))
      // then - verify the output
      .andExpect(status().isOk())
      .andDo(print())
      .andExpect(jsonPath("$.size()", CoreMatchers.is(employees.size())));
}

}























