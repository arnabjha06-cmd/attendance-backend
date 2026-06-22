package com.attendance.backend.repository;


import com.attendance.backend.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository
        extends JpaRepository<LeaveRequest, Integer> {

    long countByEmployeeId(
            Integer employeeId
    );

    List<LeaveRequest> findByStatus(
            String status
    );


}
