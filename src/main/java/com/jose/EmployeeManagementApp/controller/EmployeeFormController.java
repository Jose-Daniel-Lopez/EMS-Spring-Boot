package com.jose.EmployeeManagementApp.controller;

import com.jose.EmployeeManagementApp.entity.Employee;
import com.jose.EmployeeManagementApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeFormController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeFormController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Display the add employee form
    @GetMapping("/employees/add")
    public String showAddForm(Model model){
        model.addAttribute("employee", new Employee());
        return "add-employee";
    }

    // Handle form submission
    @PostMapping("/employees/add")
    public String saveEmployee(@ModelAttribute("employee") Employee employee,
                               RedirectAttributes redirectAttributes) {
        try {
            employeeService.save(employee);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Employee " + employee.getFirstName() + " " + employee.getLastName() + " has been successfully added!");
            return "redirect:/employees";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Error adding employee: " + e.getMessage());
            return "redirect:/employees/add";
        }
    }
}
