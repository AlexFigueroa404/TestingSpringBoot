package com.kodigo.springboot.testing.integration;


import com.kodigo.springboot.testing.entity.Employee;
import com.kodigo.springboot.testing.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest

// will disable H2 database
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IntegrationWithoutH2 {

@Autowired
private EmployeeRepository employeeRepository;

// Junit test for save employee operation using bdd style
private Employee employee;

@BeforeEach
void setup() {

  employee = Employee.builder()
      .firstName("Jhon")
      .lastName("Doe")
      .email("JhonDoe@gmail.com")
      .build();
}


@DisplayName("Junit test for save employee operation using bdd style")
@Test
void givenEmployeeObject_whenSave_thenReturnSaveEmployee() {

  // given precondition or setup

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
  employeeRepository.deleteAll();
  Employee employee01 = Employee.builder().firstName("John").lastName("Doe")
      .email("JhonDoe@gmail.com").build();

  Employee employee02 = Employee.builder().firstName("Jane").lastName("Doe")
      .email("JaneDoe@gmail.com").build();

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
void givenEmployeeObject_whenFindByIdEmployee_thenReturnEmployee() {

  // given - precondition or setup

  Employee employee = Employee.builder().firstName("Jane").lastName("Doe")
      .email("JaneDoe@gmail.com").build();

  employeeRepository.save(employee);

  // when - action or the behavior that we are going test

  Optional<Employee> employeedb = employeeRepository.findById(employee.getId());

  // then - verify the output

  Assertions.assertThat(employeedb).isPresent();
  Assertions.assertThat(employeedb.get().getFirstName()).isEqualTo("Jane");

}

@Test
  // Junit test for
void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

  // given - precondition or setup

  Employee employee = Employee.builder()
      .firstName("Jhon")
      .lastName("Doe")
      .email("JhonDoe@gmail.com")
      .build();

  employeeRepository.save(employee);

  // when - action or the behavior that we are going test

  Optional<Employee> employeedb = employeeRepository.findByEmail(employee.getEmail());
  // then - verify the output

  Assertions.assertThat(employeedb).isPresent();
  Assertions.assertThat(employeedb.get().getEmail()).isEqualTo(employee.getEmail());

}

@Test
// Junit test for
void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

  // given - precondition or setup

  Employee employee = Employee.builder()
      .firstName("Jhon")
      .lastName("Doe")
      .email("JhonDoe@gmail.com")
      .build();

  employeeRepository.save(employee);
  // when - action or the behavior that we are going test

  Employee employeedb = employeeRepository.findById(employee.getId()).get();

  employeedb.setEmail("JhonDoe@hotmail.com");
  Employee updatedEmployee = employeeRepository.save(employeedb);

  Assertions.assertThat(updatedEmployee.getEmail()).isEqualTo("JhonDoe@hotmail.com");

}


@Test
  // Junit test for
void givenEmployeeObject_whenDeleteEmployee_thenRemoveEmployee() {

  // given - precondition or setup
  Employee employee = Employee.builder()
      .firstName("Jhon")
      .lastName("Doe")
      .email("JhonDoe@gmail.com")
      .build();

  employeeRepository.save(employee);
  // when - action or the behavior that we are going test

  employeeRepository.deleteById(employee.getId());

  Optional<Employee> employeeDeleted = employeeRepository.findById(employee.getId());

  // then - verify the output

  Assertions.assertThat(employeeDeleted).isEmpty();

}

@Test

  // Junit test for
void givenEmployeeObject_whenFindByNameAndLastName_thenReturnEmployeeObject() {

  // given - precondition or setup
  Employee employee = Employee.builder()
      .firstName("Jhon")
      .lastName("Doe")
      .email("JhonDoe@gmail.com")
      .build();

  Employee employeedb = employeeRepository.save(employee);

  // when - action or the behavior that we are going test

  Employee employeeTest = employeeRepository.buscarPorNombreYApellido(employee.getFirstName(),
      employee.getLastName());

  // then - verify the output

  Assertions.assertThat(employeeTest).isNotNull();
}


}
