package com.jose.EmployeeManagementApp.repository;

import com.jose.EmployeeManagementApp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT DISTINCT e.department FROM Employee e ORDER BY e.department")
    List<String> findAllDepartments();

    @Query("SELECT DISTINCT e.position FROM Employee e ORDER BY e.position")
    List<String> findAllPositions();

    @Query("SELECT e from Employee e WHERE " +
            " e.department LIKE CONCAT('%', :query, '%') OR " +
            " e.email LIKE CONCAT('%', :query, '%') OR " +
            " e.firstName LIKE CONCAT('%', :query, '%') OR " +
            " e.lastName LIKE CONCAT('%', :query, '%') OR " +
            " e.phoneNumber LIKE CONCAT('%', :query, '%') OR " +
            " e.position LIKE CONCAT('%', :query, '%') OR " +
            " e.salary LIKE CONCAT('%', :query, '%')")
    List<Employee> searchEmployees(String query);

    @Query("SELECT e FROM Employee e WHERE e.department = :department")
    List<Employee> searchEmployeesByDepartment(String department);

    @Query("SELECT e FROM Employee e WHERE e.position = :position")
    List<Employee> searchEmployeesByPosition(String position);

}
