package com.attendance.backend.controller;

import com.attendance.backend.dto.LoginRequest;
import com.attendance.backend.entity.Employee;
import com.attendance.backend.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final EmployeeRepository employeeRepository;

    public AuthController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestBody LoginRequest request) {

        Employee employee =
                employeeRepository
                        .findByEmployeeIdAndPasswordHash(
                                request.getEmployeeId(),
                                request.getPassword()
                        );

        Map<String, Object> response =
                new HashMap<>();

        if (employee != null) {

            response.put("success", true);
            response.put("message", "Login Successful");

            response.put("employeeId",
                    employee.getId());

            response.put("employeeName",
                    employee.getName());

            response.put("companyId",
                    employee.getCompanyId());

        } else {

            response.put("success", false);
            response.put("message",
                    "Invalid Credentials");
        }

        return response;
    }
}