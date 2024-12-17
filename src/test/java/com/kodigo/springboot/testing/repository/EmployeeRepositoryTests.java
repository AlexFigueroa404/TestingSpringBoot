package com.kodigo.springboot.testing.repository;


import com.kodigo.springboot.testing.entity.Employee;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest

public class EmployeeRepositoryTests {


  /**
   * @DataJpaTest provides some standard setup needed for testing the persistence layer:
   * <p>
   * configuring H2, an in-memory database setting Hibernate, Spring Data, and the DataSource
   * performing an @EntityScan turning on SQL logging
   * <p>
   * To test the EmployeeRepository, we need to inject it into the test class. We can do this by
   * using the @Autowired annotation:
   * @Autowired private EmployeeRepository employeeRepository;
   */


  @Autowired
  private EmployeeRepository employeeRepository;

  // Junit test for save employee operation using bdd style

  @DisplayName("Junit test for save employee operation using bdd style")
  @Test
  void givenEmployeeObject_whenSave_thenReturnSaveEmployee() {

    // given precondition or setup

    Employee employee = Employee.builder().firstName("John").lastName("Doe")
        .email("JhonDoe@gmail.com").build();

    // when action or the behavior that we are going test

    Employee saveEmployee = employeeRepository.save(employee);

    // then verify the output

    // assertions with assertj

    Assertions.assertThat(saveEmployee).isNotNull();
    Assertions.assertThat(saveEmployee.getId()).isPositive();


  }

  @Test
    // Junit test for
  void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {

    // given - precondition or setup

    Employee employee01 = Employee.builder()
        .firstName("John")
        .lastName("Doe")
        .email("JhonDoe@gmail.com")
        .build();

    Employee employee02 = Employee
        .builder()
        .firstName("Jane")
        .lastName("Doe")
        .email("JaneDoe@gmail.com")
        .build();

    employeeRepository.save(employee01);
    employeeRepository.save(employee02);

    // when - action or the behavior that we are going test

    List<Employee> employees = employeeRepository.findAll();

    // then - verify the output

    Assertions.assertThat(employees).isNotNull();
    Assertions.assertThat(employees.size()).isEqualTo(2);


  }


  @Test
    // Junit test for get employees by id
  void given_whenFindById_thenReturnEmployee() {

    // given - precondition or setup

    Employee employee = Employee
        .builder()
        .firstName("Jane")
        .lastName("Doe")
        .email("JaneDoe@gmail.com")
        .build();

    // when - action or the behavior that we are going test

    Employee employeeOptional = employeeRepository.findById(employee.getId()).get();

    // then - verify the output

    Assertions.assertThat(employeeOptional).isNotNull();




  }
}



























