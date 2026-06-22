package com.attendance.backend.controller;

import com.attendance.backend.dto.LeaveRequestDto;
import com.attendance.backend.entity.LeaveRequest;
import com.attendance.backend.repository.LeaveRequestRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/leave")
public class LeaveController {

    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveController(
            LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    @PostMapping("/apply")
    public Map<String, Object> applyLeave(
            @RequestBody LeaveRequestDto request) {

        LeaveRequest leave = new LeaveRequest();

        leave.setCompanyId(request.getCompanyId());
        leave.setEmployeeId(request.getEmployeeId());
        leave.setFromDate(request.getFromDate());
        leave.setToDate(request.getToDate());
        leave.setReason(request.getReason());

        leave.setStatus("PENDING");

        leaveRequestRepository.save(leave);

        Map<String, Object> response =
                new HashMap<>();

        response.put("success", true);
        response.put("message",
                "Leave Applied Successfully");

        return response;
    }
}
