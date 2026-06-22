package com.attendance.backend.controller;

import com.attendance.backend.repository.AttendanceRepository;
import com.attendance.backend.repository.EmployeeRepository;
import com.attendance.backend.repository.LeaveRequestRepository;
import org.springframework.web.bind.annotation.*;
import com.attendance.backend.entity.LeaveRequest;
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
                leaveRequestRepository.count();

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

}
