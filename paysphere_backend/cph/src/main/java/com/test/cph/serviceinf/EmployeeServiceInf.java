package com.test.cph.serviceinf;

import com.test.cph.entity.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface EmployeeServiceInf {

    public List<Employee> getAllEmployees();

    Employee addEmployee(Employee employee);

    Optional<Employee> getEmployeeById(long id);

    Employee updateEmployee(Employee employeeData);

    void deleteEmploee(long id);
}
