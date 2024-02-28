package com.iamgkt.redisspringpoc;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Api(tags = "Employee Management")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @ApiOperation("Get all employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    @ApiOperation("Get employee by ID")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable @ApiParam("Employee ID") Long id) {
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ApiOperation("Create a new employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody @ApiParam("Employee details") Employee employee) {
        Employee newEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    @PutMapping("/{id}")
    @ApiOperation("Update an existing employee")
    public ResponseEntity<Employee> updateEmployee(@PathVariable @ApiParam("Employee ID") Long id,
                                                   @RequestBody @ApiParam("Updated employee details") Employee newEmployee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, newEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete an employee by ID")
    public ResponseEntity<Void> deleteEmployee(@PathVariable @ApiParam("Employee ID") Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
