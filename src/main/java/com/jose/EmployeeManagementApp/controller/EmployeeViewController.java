package com.jose.EmployeeManagementApp.controller;

import com.jose.EmployeeManagementApp.entity.Employee;
import com.jose.EmployeeManagementApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class EmployeeViewController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeViewController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees/view/{employeeId}")
    public String viewEmployee(@PathVariable("employeeId") Long employeeId, Model model){
        Optional<Employee> employee = employeeService.findEmployeeById(employeeId);

        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "view-employee";
        } else {
            // TODO: Add /error page
            return "redirect:/employees";
        }
    }

}
