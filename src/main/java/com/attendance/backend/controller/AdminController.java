package com.attendance.backend.controller;

import com.attendance.backend.repository.AttendanceRepository;
import com.attendance.backend.repository.EmployeeRepository;
import com.attendance.backend.repository.LeaveRequestRepository;
import org.springframework.web.bind.annotation.*;
import com.attendance.backend.entity.LeaveRequest;
import org.springframework.web.bind.annotation.PutMapping;
import com.attendance.backend.entity.Employee;
import com.attendance.backend.dto.AddEmployeeRequest;
import java.util.List;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final EmployeeRepository employeeRepository;
    private final AttendanceRepository attendanceRepository;
    private final LeaveRequestRepository leaveRequestRepository;


    public AdminController(
            EmployeeRepository employeeRepository,
            AttendanceRepository attendanceRepository,
            LeaveRequestRepository leaveRequestRepository) {

        this.employeeRepository = employeeRepository;
        this.attendanceRepository = attendanceRepository;
        this.leaveRequestRepository = leaveRequestRepository;

    }

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {

        Map<String, Object> response =
                new HashMap<>();

        long totalEmployees =
                employeeRepository.count();

        long totalAttendance =
                attendanceRepository.count();

        long totalLeaves =
                leaveRequestRepository
                        .countByStatus("PENDING");


        response.put(
                "totalEmployees",
                totalEmployees);

        response.put(
                "totalAttendance",
                totalAttendance);

        response.put(
                "totalLeaves",
                totalLeaves);

        return response;
    }
    @GetMapping("/pending-leaves")
    public List<LeaveRequest> getPendingLeaves() {

        return leaveRequestRepository
                .findByStatus("PENDING");

    }
    @PostMapping("/approve/{id}")
    public Map<String, Object> approveLeave(
            @PathVariable Integer id) {

        LeaveRequest leave =
                leaveRequestRepository
                        .findById(id)
                        .orElse(null);

        Map<String, Object> response =
                new HashMap<>();

        if(leave == null) {

            response.put("success", false);

            return response;
        }

        leave.setStatus("APPROVED");

        leaveRequestRepository.save(leave);

        response.put("success", true);

        return response;
    }
    @PostMapping("/reject/{id}")
    public Map<String, Object> rejectLeave(
            @PathVariable Integer id) {

        LeaveRequest leave =
                leaveRequestRepository
                        .findById(id)
                        .orElse(null);

        Map<String, Object> response =
                new HashMap<>();

        if(leave == null) {

            response.put("success", false);

            return response;
        }

        leave.setStatus("REJECTED");

        leaveRequestRepository.save(leave);

        response.put("success", true);

        return response;
    }
    @GetMapping("/employees")
    public List<Employee> getEmployees() {

        return employeeRepository.findAll();
    }
    @PostMapping("/add-employee")
    public Map<String, Object> addEmployee(
            @RequestBody AddEmployeeRequest request) {

        Employee employee = new Employee();

        employee.setCompanyId(
                request.getCompanyId());

        employee.setEmployeeId(
                request.getEmployeeId());

        employee.setName(
                request.getName());

        employee.setEmail(
                request.getEmail());

        employee.setPasswordHash(
                request.getPassword());

        employee.setRole(
                request.getRole());

        employeeRepository.save(employee);

        Map<String, Object> response =
                new HashMap<>();

        response.put("success", true);
        response.put("message",
                "Employee Added Successfully");

        return response;
    }
    @DeleteMapping("/delete-employee/{id}")
    public Map<String, Object> deleteEmployee(
            @PathVariable Integer id) {

        Map<String, Object> response =
                new HashMap<>();

        if (!employeeRepository.existsById(id)) {

            response.put("success", false);
            response.put("message",
                    "Employee Not Found");

            return response;
        }

        employeeRepository.deleteById(id);

        response.put("success", true);
        response.put("message",
                "Employee Deleted");

        return response;
    }
    @PutMapping("/update-employee/{id}")
    public Map<String, Object> updateEmployee(
            @PathVariable Integer id,
            @RequestBody Employee request) {

        Map<String, Object> response =
                new HashMap<>();

        Employee employee =
                employeeRepository
                        .findById(id)
                        .orElse(null);

        if(employee == null) {

            response.put("success", false);
            response.put("message",
                    "Employee Not Found");

            return response;
        }

        employee.setName(
                request.getName());

        employee.setEmail(
                request.getEmail());

        employee.setRole(
                request.getRole());

        employeeRepository.save(employee);

        response.put("success", true);
        response.put("message",
                "Employee Updated");

        return response;
    }

}
