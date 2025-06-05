package com.jose.EmployeeManagementApp.controller;

import com.jose.EmployeeManagementApp.entity.Employee;
import com.jose.EmployeeManagementApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/employees")
public class EmployeeListController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeListController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // ==================== PRE-POPULATION ====================

    // Ghetto way to fix allDepartments and allPositions attributes not being both added.
    // Causing to only populate one of the dropdowns, the attribute that gets loaded first.
    @ModelAttribute
    public void addCommonAttributes(Model model) {
        List<String> allDepartments = employeeService.getAllDepartments();
        List<String> allPositions = employeeService.getAllPositions();
        model.addAttribute("allDepartments", allDepartments);
        model.addAttribute("allPositions", allPositions);
    }

    // ==================== VIEW/READ OPERATIONS ====================

    // Main dashboard page
    @GetMapping({"/", ""})
    public String showAll(Model model){
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employees";
    }

    // Search employees
    @GetMapping("/search")
    public String searchEmployees(@RequestParam(value = "query", required = false) String query, Model model){

        if (query != null && !query.trim().isEmpty()) {
            List<Employee> employees = employeeService.searchEmployees(query.trim());
            model.addAttribute("employees", employees);
            model.addAttribute("searchQuery", query);
        } else {
            // Handle empty search
            model.addAttribute("employees", employeeService.getAllEmployees());
        }

        return "employees";
    }

    // Search employees by department
    @GetMapping("/searchByDepartment")
    public String searchEmployeesByDepartment(@RequestParam(value = "department", required = false) String department, Model model){

        // Always get all departments for the dropdown
        List<String> allDepartments = employeeService.getAllDepartments();
        model.addAttribute("allDepartments", allDepartments);

        if (department != null && !department.trim().isEmpty() && !department.equals("All departments")) {
            List<Employee> employees = employeeService.searchEmployeesByDepartment(department.trim());
            model.addAttribute("employees", employees);
            model.addAttribute("selectedDepartment", department);
        } else {
            // Handle empty search or "All departments"
            model.addAttribute("employees", employeeService.getAllEmployees());
            model.addAttribute("selectedDepartment", "All departments");
        }

        return "employees";
    }

    // Search employees by position
    @GetMapping("/searchEmployeesByPosition")
    public String searchEmployeesByPosition(@RequestParam(value = "position", required = false) String position, Model model){

        // Always get all positions for the dropdown
        List<String> allPositions = employeeService.getAllPositions();
        model.addAttribute("allPositions", allPositions);

        if (position != null && !position.trim().isEmpty() && !position.equals("All positions")) {
            List<Employee> employees = employeeService.searchEmployeesByPosition(position.trim());
            model.addAttribute("employees", employees);
            model.addAttribute("selectedPosition", position);
        } else {
            model.addAttribute("employees", employeeService.getAllEmployees());
            model.addAttribute("selectedPosition", "All positions");
        }

        return "employees";
    }

    // ==================== CREATE OPERATIONS ====================

    // Handle form submission for new employee
    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute Employee employee, RedirectAttributes redirectAttributes){
        try {
            employeeService.save(employee);
            redirectAttributes.addFlashAttribute("successMessage", "Employee added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding employee: " + e.getMessage());
        }
        return "redirect:/employees/";
    }

    // ==================== DELETE OPERATIONS ====================

    // Handle delete requests
    @GetMapping("/delete/{employeeId}")
    public String deleteEmployee(@PathVariable Long employeeId, RedirectAttributes redirectAttributes){
        try {
            employeeService.deleteEmployeeById(employeeId);
            redirectAttributes.addFlashAttribute("successMessage", "Employee deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting employee: " + e.getMessage());
        }
        return "redirect:/employees/";
    }
}
