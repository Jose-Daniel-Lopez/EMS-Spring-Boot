package com.jose.EmployeeManagementApp.service;

import com.jose.EmployeeManagementApp.entity.Employee;
import com.jose.EmployeeManagementApp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // ==================== CREATE OPERATIONS ====================

    @Transactional
    public void createEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    // ==================== READ OPERATIONS ====================

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<String> getAllDepartments() {
        return employeeRepository.findAllDepartments();
    }

    public List<String> getAllPositions() {
        return employeeRepository.findAllPositions();
    }

    public Optional<Employee> findEmployeeById(Long theId){
        return employeeRepository.findById(theId);
    }

    public List<Employee> searchEmployees(String query){
        return employeeRepository.searchEmployees(query);
    }

    public List<Employee> searchEmployeesByDepartment(String department) {
        return employeeRepository.searchEmployeesByDepartment(department);
    }

    public List<Employee> searchEmployeesByPosition(String position) {
        return employeeRepository.searchEmployeesByPosition(position);
    }

    // ==================== UPDATE OPERATIONS ====================

    @Transactional
    public void updateEmployeeById(Long theId, Employee employeeDetails){

        // Find Entity
        Employee existingEmployee = employeeRepository.findById(theId).orElseThrow();

        // Update fields
        existingEmployee.setFirstName(employeeDetails.getFirstName());
        existingEmployee.setLastName(employeeDetails.getLastName());
        existingEmployee.setEmail(employeeDetails.getEmail());
        existingEmployee.setPhoneNumber(employeeDetails.getPhoneNumber());
        existingEmployee.setDepartment(employeeDetails.getDepartment());
        existingEmployee.setPosition(employeeDetails.getPosition());
        existingEmployee.setSalary(employeeDetails.getSalary());
        existingEmployee.setHireDate(employeeDetails.getHireDate());

        employeeRepository.save(existingEmployee);
    }

    // ==================== DELETE OPERATIONS ====================

    @Transactional
    public void deleteEmployeeById(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }
}
