package com.example.aman.TestingApplicationMod_7.repositories;

import com.example.aman.TestingApplicationMod_7.Application;
import com.example.aman.TestingApplicationMod_7.TestContainerConfiguration;
import com.example.aman.TestingApplicationMod_7.entities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {
    @Autowired
    private EmployeeRepository employeeRepository;
    private Employee employee;
    @BeforeEach
    void setUp(){
        employee=Employee.builder()
                .name("Aman")
                .email("aman@gmail.com")
                .build();
    }
    @Test
    void testFindByEmail_whenEmailisPresent_thenReturnEmployee(){
    employeeRepository.save(employee);
        List<Employee>employees=employeeRepository.findByEmail(employee.getEmail());
        assertThat(employees).isNotNull();
        assertThat(employees).isNotEmpty();
        assertThat(employees.get(0).getEmail()).isEqualTo(employee.getEmail());

    }
    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList(){
     String email="notPresent.123@gmail.com";
     List<Employee>employees=employeeRepository.findByEmail(email);
     assertThat(employees).isNotNull();
     assertThat(employees).isEmpty();
    }
}
