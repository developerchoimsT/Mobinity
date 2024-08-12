package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, String> {

    Company findByCompanyCd(String companyCd);
}
