package com.attendance.backend.controller;

import com.attendance.backend.entity.Company;
import com.attendance.backend.repository.CompanyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CompanyController {

    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping("/api/companies")
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }
}
