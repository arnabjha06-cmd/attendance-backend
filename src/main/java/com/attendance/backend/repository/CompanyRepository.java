package com.attendance.backend.repository;

import com.attendance.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository
        extends JpaRepository<Company, Integer> {

}