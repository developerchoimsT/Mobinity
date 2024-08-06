package com.ms.hoopi.repository;

import com.ms.hoopi.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {

    Company findByCompanyCd(String s);
}
