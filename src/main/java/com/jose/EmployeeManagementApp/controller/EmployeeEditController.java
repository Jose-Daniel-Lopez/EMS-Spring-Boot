package com.jose.EmployeeManagementApp.controller;

import com.jose.EmployeeManagementApp.entity.Employee;
import com.jose.EmployeeManagementApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class EmployeeEditController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeEditController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Display the edit form with populated data
    @GetMapping("/employees/edit/{employeeId}")
    public String showEditForm(@PathVariable("employeeId") Long employeeId, Model model) {
        Optional<Employee> employeeOptional = employeeService.findEmployeeById(employeeId);
        if (employeeOptional.isEmpty()) {
            return "redirect:/employees";
        }

        // Extract the actual Employee object from the Optional
        // Learning note: Thymeleaf can't access properties on an Optional wrapper
        Employee employee = employeeOptional.get();
        model.addAttribute("employee", employee);
        return "edit-employee";
    }


    // Handle the edit form submission
    @PostMapping("/employees/edit/{employeeId}")
    public String updateEmployee(@PathVariable("employeeId") Long employeeId,
                                 @ModelAttribute("employee") Employee employee,
                                 RedirectAttributes redirectAttributes) {
        try {
            employee.setId(employeeId); // Ensure the ID is set
            employeeService.save(employee);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Employee " + employee.getFirstName() + " " + employee.getLastName() + " has been successfully updated!");
            return "redirect:/employees";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error updating employee: " + e.getMessage());
            return "redirect:/employees/edit/" + employeeId;
        }
    }

}
