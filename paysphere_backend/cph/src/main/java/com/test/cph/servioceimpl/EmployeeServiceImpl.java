package com.test.cph.servioceimpl;

import com.test.cph.entity.Employee;
import com.test.cph.repository.EmployeeRepository;
import com.test.cph.serviceinf.EmployeeServiceInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class EmployeeServiceImpl implements EmployeeServiceInf {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee employeeData) {
        return employeeRepository.save(employeeData);
    }

    @Override
    public void deleteEmploee(long id) {
        employeeRepository.deleteById(id);
    }


}
