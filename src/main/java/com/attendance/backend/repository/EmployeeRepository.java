package com.attendance.backend.repository;

import com.attendance.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
        extends JpaRepository<Employee, Integer> {

    Employee findByEmployeeIdAndPasswordHash(
            String employeeId,
            String passwordHash
    );
}
