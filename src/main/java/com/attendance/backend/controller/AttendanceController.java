package com.attendance.backend.controller;

import com.attendance.backend.dto.PunchInRequest;
import com.attendance.backend.dto.PunchOutRequest;
import com.attendance.backend.entity.Attendance;
import com.attendance.backend.repository.AttendanceRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.attendance.backend.repository.LeaveRequestRepository;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceRepository attendanceRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    public AttendanceController(
            AttendanceRepository attendanceRepository,
            LeaveRequestRepository leaveRequestRepository) {

        this.attendanceRepository =
                attendanceRepository;

        this.leaveRequestRepository =
                leaveRequestRepository;
    }

    @PostMapping("/punch-in")
    public Map<String, Object> punchIn(
            @RequestBody PunchInRequest request) {

        Attendance attendance = new Attendance();

        attendance.setCompanyId(request.getCompanyId());
        attendance.setEmployeeId(request.getEmployeeId());
        attendance.setOfficeId(request.getOfficeId());

        attendance.setAttendanceDate(
                LocalDate.now().toString());

        attendance.setPunchIn(
                LocalDateTime.now().toString());

        attendanceRepository.save(attendance);

        Map<String, Object> response =
                new HashMap<>();

        response.put("success", true);
        response.put("message",
                "Punch In Successful");


        return response;
    }
        @PostMapping("/punch-out")
        public Map<String, Object> punchOut(
                @RequestBody PunchOutRequest request) {

            Attendance attendance =
                    attendanceRepository
                            .findTopByEmployeeIdOrderByIdDesc(
                                    request.getEmployeeId());

            Map<String, Object> response =
                    new HashMap<>();

            if (attendance == null) {

                response.put("success", false);
                response.put("message",
                        "No Attendance Found");

                return response;
            }

            attendance.setPunchOut(
                    LocalDateTime.now().toString());

            attendanceRepository.save(attendance);

            response.put("success", true);
            response.put("message",
                    "Punch Out Successful");

            return response;
        }
    @GetMapping("/history/{employeeId}")
    public List<Attendance> getHistory(
            @PathVariable Integer employeeId) {

        return attendanceRepository
                .findByEmployeeIdOrderByIdDesc(
                        employeeId
                );
    }

    @GetMapping("/today/{employeeId}")
    public Map<String, Object> getTodayAttendance(
            @PathVariable Integer employeeId) {

        Attendance attendance =
                attendanceRepository
                        .findTopByEmployeeIdAndAttendanceDate(
                                employeeId,
                                LocalDate.now().toString()
                        );

        Map<String, Object> response =
                new HashMap<>();

        if (attendance == null) {

            response.put("success", false);
            response.put("message",
                    "No Attendance Found");

            return response;
        }

        response.put("success", true);
        response.put("attendanceDate",
                attendance.getAttendanceDate());

        response.put("punchIn",
                attendance.getPunchIn());

        response.put("punchOut",
                attendance.getPunchOut());

        return response;
    }
    @GetMapping("/report/{employeeId}")
    public Map<String, Object> getReport(
            @PathVariable Integer employeeId) {

        long totalDays =
                attendanceRepository
                        .countByEmployeeId(employeeId);

        Map<String, Object> response =
                new HashMap<>();

        response.put("success", true);
        response.put("employeeId", employeeId);
        response.put("totalAttendanceDays",
                totalDays);

        return response;
    }
    @GetMapping("/stats/{employeeId}")
    public Map<String, Object> getStats(
            @PathVariable Integer employeeId) {

        long presentDays =
                attendanceRepository
                        .countByEmployeeId(
                                employeeId);

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "presentDays",
                presentDays);

        long leaveDays =
                leaveRequestRepository
                        .countByEmployeeId(
                                employeeId);

        long totalDays =
                presentDays + leaveDays;

        int attendancePercentage = 0;

        if(totalDays > 0) {

            attendancePercentage =
                    (int)((presentDays * 100)
                            / totalDays);
        }

        response.put(
                "leaveDays",
                leaveDays);

        response.put(
                "attendancePercentage",
                attendancePercentage);

        return response;
    }

    }

