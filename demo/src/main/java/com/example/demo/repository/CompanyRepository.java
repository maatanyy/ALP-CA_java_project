package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
	// 회사 이름이 존재하는지 확인하는 메서드
    boolean existsByName(String name);
}