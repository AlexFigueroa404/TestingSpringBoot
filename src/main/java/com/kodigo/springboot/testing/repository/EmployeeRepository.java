package com.kodigo.springboot.testing.repository;

import com.kodigo.springboot.testing.entity.Employee;
import java.util.Optional;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

@Query("select e from Employee e where e.firstName=:nombre and e.lastName=:apellido")
Employee buscarPorNombreYApellido(@Param("nombre") String nombre,
    @Param("apellido") String apellido);

Optional<Employee> findByEmail(String email);
}
