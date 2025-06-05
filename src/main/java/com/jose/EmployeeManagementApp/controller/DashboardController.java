package com.jose.EmployeeManagementApp.controller;

import com.jose.EmployeeManagementApp.entity.Employee;
import com.jose.EmployeeManagementApp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    private final EmployeeService employeeService;

    @Autowired
    public DashboardController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Dashboard test
    @GetMapping("/dashboard")
    public String viewDashboard(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();

        // Department distribution
        Map<String, Long> departmentData = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        // Position distribution
        Map<String, Long> positionData = employees.stream()
                .collect(Collectors.groupingBy(Employee::getPosition, Collectors.counting()));

        // Salary range distribution (based on your salary field)
        Map<String, Long> salaryRangeData = employees.stream()
                .collect(Collectors.groupingBy(emp -> getSalaryRange(emp.getSalary()), Collectors.counting()));

        // Hiring trends by month (based on hireDate)
        Map<String, Long> hiringTrendsData = employees.stream()
                .filter(emp -> emp.getHireDate() != null)
                .collect(Collectors.groupingBy(emp -> getMonthYear(emp.getHireDate()), Collectors.counting()));

        // Average salary by department
        Map<String, Double> departmentSalaryData = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.averagingDouble(emp -> Double.parseDouble(emp.getSalary()))));

        model.addAttribute("employees", employees);
        model.addAttribute("departmentData", departmentData);
        model.addAttribute("positionData", positionData);
        model.addAttribute("salaryRangeData", salaryRangeData);
        model.addAttribute("hiringTrendsData", hiringTrendsData);
        model.addAttribute("departmentSalaryData", departmentSalaryData);

        return "dashboard";
    }

    // Helper methods
    private String getSalaryRange(String salary) {
        double sal = Double.parseDouble(salary);
        if (sal < 30000) return "Under $30K";
        if (sal < 50000) return "$30K - $50K";
        if (sal < 70000) return "$50K - $70K";
        if (sal < 100000) return "$70K - $100K";
        return "Over $100K";
    }

    private String getMonthYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        return sdf.format(date);
    }
}
