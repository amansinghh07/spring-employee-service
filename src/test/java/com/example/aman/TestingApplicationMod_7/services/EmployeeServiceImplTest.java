package com.example.aman.TestingApplicationMod_7.services;

import com.example.aman.TestingApplicationMod_7.TestContainerConfiguration;
import com.example.aman.TestingApplicationMod_7.dto.EmployeeDto;
import com.example.aman.TestingApplicationMod_7.entities.Employee;
import com.example.aman.TestingApplicationMod_7.exceptions.ResourceNotFoundException;
import com.example.aman.TestingApplicationMod_7.repositories.EmployeeRepository;
import com.example.aman.TestingApplicationMod_7.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ResourceElementResolver;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestContainerConfiguration.class)
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Spy
    private ModelMapper modelMapper;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee mockEmployee;
    private EmployeeDto mockEmployeeDto;
    @BeforeEach
    void setUp() {
        mockEmployee = Employee.builder()
                .email("amanKsr@gmail.com")
                .name("Aman").salary(2000L)
                .build();
        mockEmployeeDto=modelMapper.map(mockEmployee, EmployeeDto.class);
    }
    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployeeDto() {
        // assign
        when(employeeRepository.findById(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));
        //act
        EmployeeDto employeeDto = employeeService.getEmployeeById(mockEmployee.getId());
        //assert
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository,atLeast(1)).findById(mockEmployee.getId());
//        verify(employeeRepository).save(null);
    }
    @Test
    void testGetEmployeeById_WhenEmployeeIsNotPresent_ThenThrowExceptio() {
        //arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        // act and assert
        assertThatThrownBy(()->employeeService.getEmployeeById(1L)).
                isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");
        verify(employeeRepository).findById(1L);
    }
    @Test
    void createEmployee_WhenValidEmployee_ThenCreateNewEmployee() {
        // assign
        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);
        //act
        EmployeeDto employeeDto=employeeService.createNewEmployee(mockEmployeeDto);
        //assert
        assertThat(employeeDto).isNotNull();
        assertThat(employeeDto.getEmail()).isEqualTo(mockEmployeeDto.getEmail());
        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        Employee capturedEmployee = captor.getValue();
        assertThat(capturedEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
    }
    @Test
    void testCreateNewEmployee_whenAttemptingToCreateEmployeeWithExistingEmail_ThenThrowExceptio() {
        when(employeeRepository.findByEmail(mockEmployeeDto.getEmail())).thenReturn(List.of(mockEmployee));
        assertThatThrownBy(()->employeeService.createNewEmployee(mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: "+mockEmployeeDto.getEmail());
        verify(employeeRepository).findByEmail(mockEmployeeDto.getEmail());
        verify(employeeRepository,never()).save(any());
    }
    @Test
    void testUpdateEmployee_whenEmployeeDoesNotExist_ThenThrowExceptio() {
        //arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());
        //act and assert
        assertThatThrownBy(()->employeeService.updateEmployee(1L, mockEmployeeDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");
        verify(employeeRepository).findById(1L);
        verify(employeeRepository,never()).save(any());
    }
    @Test
    void testUpdateEmployee_whenAttemptingToUpdateEmail_thenThrowExceptio() {
        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDto.setName("Random");
        mockEmployeeDto.setEmail("random@gmail.com");
        //act and assert
        assertThatThrownBy(()->employeeService.updateEmployee(mockEmployeeDto.getId(), mockEmployeeDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The email of the employee cannot be updated");
        verify(employeeRepository).findById(mockEmployeeDto.getId());
        verify(employeeRepository,never()).save(any());
    }
    @Test
    void testUpdateEmployee_whenValidEmployee_ThenUpdateEmployee() {
        when(employeeRepository.findById(mockEmployeeDto.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDto.setName("Random");
        mockEmployeeDto.setSalary(199L);
        Employee newEmployee=modelMapper.map(mockEmployeeDto, Employee.class);
        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);
        EmployeeDto updateEmployeeDto=employeeService.updateEmployee(mockEmployeeDto.getId(), mockEmployeeDto);
        assertThat(updateEmployeeDto).isEqualTo(mockEmployeeDto);
        verify(employeeRepository).findById(mockEmployeeDto.getId());
        verify(employeeRepository).save(any());
    }
    @Test
    void testDeleteEmployee_WhenEmployeeDoesNotExist_ThenThrowExceptio() {
        when(employeeRepository.existsById(1L)).thenReturn(false);
        assertThatThrownBy(()->employeeService.deleteEmployee(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: "+1L);
        verify(employeeRepository,never()).deleteById(anyLong());
    }
    @Test
    void testDeleteEmployee_WhenEmployeeExists_ThenDeleteEmployee() {
        when(employeeRepository.existsById(1L)).thenReturn(true);
        assertThatCode(()->employeeService.deleteEmployee(1L))
                .doesNotThrowAnyException();
        verify(employeeRepository).existsById(1L);
    }

}
