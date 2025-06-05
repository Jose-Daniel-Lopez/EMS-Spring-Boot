package com.jose.EmployeeManagementApp.controller;

import com.jose.EmployeeManagementApp.entity.Employee;
import com.jose.EmployeeManagementApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmployeeFormController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeFormController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Employee form
    @PostMapping("/employees/add")
    public String showAddForm(Model model){
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }
}
