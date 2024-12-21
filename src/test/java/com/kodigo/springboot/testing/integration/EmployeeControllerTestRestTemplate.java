package com.kodigo.springboot.testing.integration;


import com.kodigo.springboot.testing.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTestRestTemplate {


  @Autowired
  private TestRestTemplate restTemplate;

  @LocalServerPort
  private int port;



  @Test

  void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() {
    // given - precondition or setup



    Employee employee = Employee.builder()
        .firstName("Jhon")
        .lastName("Doe")
        .email("Jhon@gmail.com").build();

    ResponseEntity<Employee> responseEntity =
        restTemplate.postForEntity("http://localhost:" + port + "/api/employees", employee,
            Employee.class);

    // when - action or the behavior that we are going test

    // then - the assertion or the output that we are expecting

    Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
  }


}
