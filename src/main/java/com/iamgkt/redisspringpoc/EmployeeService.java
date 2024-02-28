package com.iamgkt.redisspringpoc;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    /**
     * Saves an employee.
     *
     * @param employee The employee to be saved.
     * @return The saved employee.
     */
    @CachePut(cacheNames = "employees", key = "#employee.id")
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }


    /**
     * Retrieves an employee by ID.
     *
     * @param id The ID of the employee.
     * @return An Optional containing the employee, or empty if not found.
     */
    @Cacheable(cacheNames = "employees", key = "#id")
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * Retrieves all employees.
     *
     * @return A list of all employees.
     */
    @Cacheable(cacheNames = "employees")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Updates an existing employee.
     *
     * @param id          The ID of the employee to be updated.
     * @param newEmployee The updated employee data.
     * @return The updated employee.
     * @throws RuntimeException if the employee with the given ID is not found.
     */
    @CachePut(cacheNames = "employees", key = "#id")
    public Employee updateEmployee(Long id, Employee newEmployee) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setName(newEmployee.getName());
            return employeeRepository.save(employee);
        }).orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    /**
     * Deletes an employee by ID.
     *
     * @param id The ID of the employee to be deleted.
     */
    @CacheEvict(cacheNames = "employees", key = "#id")
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
