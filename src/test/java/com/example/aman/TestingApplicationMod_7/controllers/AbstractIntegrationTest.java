package com.example.aman.TestingApplicationMod_7.controllers;

import com.example.aman.TestingApplicationMod_7.TestContainerConfiguration;
import com.example.aman.TestingApplicationMod_7.dto.EmployeeDto;
import com.example.aman.TestingApplicationMod_7.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainerConfiguration.class)
public class AbstractIntegrationTest {
    @Autowired
    public WebTestClient webTestClient;
   Employee testEmployee = Employee.builder()
            .email("amanksr@gmail.com")
                .name("Aman singh")
                .salary(200L)
                .build();
   EmployeeDto testEmployeeDto=EmployeeDto.builder()
            .email("amanksr@gmail.com")
                .name("Aman singh")
                .salary(200L)
                .build();
}
