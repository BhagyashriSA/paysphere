package com.test.cph.controller;

import com.test.cph.entity.Employee;
import com.test.cph.exception.ResourceNotFoundException;
import com.test.cph.serviceinf.EmployeeServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeServiceInf employeeServiceInf;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeServiceInf.getAllEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeServiceInf.addEmployee(employee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Optional<Employee>> getEmployeeById(@PathVariable long id) {
        Optional<Employee> employeeData = employeeServiceInf.getEmployeeById(id);
        return  new ResponseEntity<>(employeeData, HttpStatus.OK);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable long id, @RequestBody Employee employee) {
        Employee employeeData = employeeServiceInf.getEmployeeById(id).orElseThrow(() ->
                 new ResourceNotFoundException(("Employee does not exist with id " + id)));
        employeeData.setName(employee.getName());
        employeeData.setEmail(employee.getEmail());
        employeeData.setDepartment(employee.getDepartment());
        employeeData.setSalary(employee.getSalary());
        Employee updatwdData = employeeServiceInf.updateEmployee(employeeData);
        return  new ResponseEntity<>(updatwdData, HttpStatus.OK);
    }

    @DeleteMapping("/employees/{id}")
    public  ResponseEntity<Void> deleteEmployee(@PathVariable long id){
        employeeServiceInf.deleteEmploee(id);
//        return ResponseEntity.ok("Employee Deleted Successfully");
        return  ResponseEntity.noContent().build();
    }


}
