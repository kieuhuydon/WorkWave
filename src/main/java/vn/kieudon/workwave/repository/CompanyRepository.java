package vn.kieudon.workwave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.kieudon.workwave.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
  
    
} 