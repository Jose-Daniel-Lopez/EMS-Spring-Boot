package com.jose.EmployeeManagementApp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder


@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String department;
    private String position;
    private String salary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    // Used to calculate the difference between today's date and hire date to be displayed in employees table
    public long getDaysFromHire() {
        if (hireDate == null) return 0;
        long diffInMillies = new Date().getTime() - hireDate.getTime();
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

}
