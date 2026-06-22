package com.attendance.backend.repository;

import com.attendance.backend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Integer> {

    Attendance findTopByEmployeeIdOrderByIdDesc(
            Integer employeeId
    );

    Attendance findTopByEmployeeIdAndAttendanceDate(
            Integer employeeId,
            String attendanceDate
    );

    long countByEmployeeId(
            Integer employeeId
    );

    List<Attendance> findByEmployeeIdOrderByIdDesc(
            Integer employeeId
    );
}